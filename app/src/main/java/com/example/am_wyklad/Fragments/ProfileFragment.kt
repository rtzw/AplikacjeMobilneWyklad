package com.example.am_wyklad.Fragments

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.Adapters.*
import com.example.am_wyklad.Database.Profile
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables
import kotlin.random.Random

class ProfileFragment : Fragment() {

    lateinit var backButton: View
    lateinit var drawButton: View
    lateinit var sms: View
    lateinit var code: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        lateinit var profileParticipantsRecyclerAdapter: ProfileParticipantsRecyclerAdapter
        lateinit var profileChallengesRecycleAdapter: ProfileChallengesRecycleAdapter
        lateinit var profileResultRecyclerAdapter: ProfileResultRecyclerAdapter

        backButton = view.findViewById(R.id.backButton)
        drawButton = view.findViewById(R.id.drawButton)
        sms = view.findViewById(R.id.sms)
        code = view.findViewById(R.id.code)
        val profile: Profile
        val userDatabase: UserDatabase = UserDatabase(requireActivity())
        if(StaticVariables.loggedUser.id == -1){
            profile = userDatabase.getProfileByCode(StaticVariables.code).get(0);
        }
        else {
            profile =
                userDatabase.getProfile(StaticVariables.loggedUser.id, StaticVariables.profile)
                    .get(0)
        }
        val participants = profile.players.split(";")
        var challenges = profile.challenges.split(";")
        val result = profile.draw.split(";")
        var participantsMutableList = participants.toMutableList()
        participantsMutableList = participantsMutableList.subList(0, participantsMutableList.size - 1).toMutableList()
        var challengesMutableList = challenges.toMutableList()
        challengesMutableList = challengesMutableList.subList(0, challengesMutableList.size - 1).toMutableList()
        var resultMutableList = result.toMutableList()
        if(resultMutableList.size > 1)
            resultMutableList = resultMutableList.subList(0, resultMutableList.size - 1).toMutableList()
        var participantsTempList: MutableList<String> = mutableListOf()
        for(item in participantsMutableList){
            participantsTempList.add(item)
            participantsTempList.add("")
        }
        var challengesTempList: MutableList<String> = mutableListOf()
        for(item in challengesMutableList){
            challengesTempList.add(item)
            challengesTempList.add("")
        }
        code.text = "Code: " + profile.code
        // participants
        val recyclerViewParticipants: RecyclerView = view.findViewById(R.id.participants)
        profileParticipantsRecyclerAdapter = ProfileParticipantsRecyclerAdapter(
            participantsTempList
            )
        val layoutManagerParticipants = LinearLayoutManager(activity)
        recyclerViewParticipants.layoutManager = layoutManagerParticipants
        recyclerViewParticipants.adapter = profileParticipantsRecyclerAdapter
        profileParticipantsRecyclerAdapter.notifyDataSetChanged()

        // challengess
        val recyclerViewChallenges: RecyclerView = view.findViewById(R.id.challenges)
        profileChallengesRecycleAdapter = ProfileChallengesRecycleAdapter(
            challengesTempList
        )
        val layoutManagerChallenges = LinearLayoutManager(activity)
        recyclerViewChallenges.layoutManager = layoutManagerChallenges
        recyclerViewChallenges.adapter = profileChallengesRecycleAdapter
        profileChallengesRecycleAdapter.notifyDataSetChanged()

        // result
        val recyclerViewResult: RecyclerView = view.findViewById(R.id.result)
        profileResultRecyclerAdapter = ProfileResultRecyclerAdapter(
            resultMutableList
        )
        val layoutManagerResult = LinearLayoutManager(activity)
        recyclerViewResult.layoutManager = layoutManagerResult
        recyclerViewResult.adapter = profileResultRecyclerAdapter

        drawButton.setOnClickListener(){
            if(StaticVariables.loggedUser.id == -1)
                Toast.makeText(requireContext(), "You cannot draw, ask the organizer!", Toast.LENGTH_SHORT).show()
            else {
                resultMutableList = mutableListOf()
                if (participantsMutableList.size <= challengesMutableList.size) {
                    challengesMutableList.shuffle(Random(System.currentTimeMillis() / 1000L))
                    for (i in 0..(participantsMutableList.size - 1)) {
                        resultMutableList.add(
                            participantsMutableList.get(i) + ", this is a challenge for you --> " + challengesMutableList.get(i))
                        resultMutableList.add("")
                    }
                } else {
                    challengesMutableList.shuffle(Random(System.currentTimeMillis() / 1000L))
                    var j = 0
                    for (i in 0..(participantsMutableList.size - 1)) {
                        if (i % challengesMutableList.size  == 0) j = 0
                        resultMutableList.add(
                            participantsMutableList.get(i) + ", this is a challenge for you --> " + challengesMutableList.get(j))
                        resultMutableList.add("")

                        j++
                    }
                }
                println(resultMutableList)
                var temp: String = ""
                for (item in resultMutableList) {
                    temp += "$item;"
                }
                if (temp.last().equals(";")) {
                    temp.dropLast(1)
                }
                userDatabase.updateProfile(
                    Profile(
                        -1, profile.adminId, profile.name,
                        profile.code, profile.challenges, profile.players, temp
                    )
                )
                profileResultRecyclerAdapter = ProfileResultRecyclerAdapter(resultMutableList)
                recyclerViewResult.adapter = profileResultRecyclerAdapter
                profileResultRecyclerAdapter.notifyDataSetChanged()
            }
        }

        sms.setOnClickListener(){
            if(StaticVariables.loggedUser.id == -1)
                Toast.makeText(requireContext(), "You cannot send SMS, only the organizer can!", Toast.LENGTH_SHORT).show()
            else {
                try {
                    val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                    builder.setTitle("The application will send an SMS with the code to the profile.")
                    val input = EditText(activity)
                    input.setHint("Input phone number")
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    builder.setView(input)

                    builder.setPositiveButton(
                        "SEND",
                        DialogInterface.OnClickListener { dialog, which ->
                            var m_Text = input.text.toString()

                            sendSMS(
                                m_Text, "You have been invited" +
                                        " to ${StaticVariables.loggedUser.name}'s profile in the" +
                                        " \"Bachelor party\" application. Here is your code: ${profile.code}"
                            )
                            Toast.makeText(requireContext(), "Message Sent", Toast.LENGTH_SHORT)
                                .show()

                        })
                    builder.setNegativeButton(
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                    builder.show()

                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter correct data!" + e.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        backButton.setOnClickListener(){
            if(StaticVariables.loggedUser.id == -1){
                val chooseFragment = ChooseFragment()
                val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.mainActivity, chooseFragment)
                fragmentTransaction.commit()
            }else{
                val yourProfiles = YourProfiles()
                val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.mainActivity, yourProfiles)
                fragmentTransaction.commit()
            }

        }
        return view
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(requireContext(), 0, Intent("SMS_SENT"),
            PendingIntent.FLAG_IMMUTABLE
        )
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

}