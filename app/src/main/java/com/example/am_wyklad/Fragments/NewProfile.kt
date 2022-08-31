package com.example.am_wyklad.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.Adapters.ChallengesRecyclerAdapter
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


class NewProfile : Fragment() {


    lateinit var backButton: View
    lateinit var addChallengesButton: View
    lateinit var addParticipantsButton: View
    lateinit var addParticipantsText: TextView
    lateinit var addChallengesText: TextView
    lateinit var create: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_profile, container, false)
        backButton = view.findViewById(R.id.backButton)
        addChallengesButton = view.findViewById(R.id.addChallengesButton)
        addParticipantsButton = view.findViewById(R.id.addParticipantsButton)
        addParticipantsText = view.findViewById(R.id.addParticipantsText)
        addChallengesText = view.findViewById(R.id.addChallengesText)
        create = view.findViewById(R.id.create)
        val userDatabase: UserDatabase = UserDatabase(requireActivity())

        addParticipantsText.text = StaticVariables.addedParticipants.size.toString() + " added"
        addChallengesText.text = StaticVariables.addedChallenges.size.toString() + " added"

        addParticipantsButton.setOnClickListener(){
            val addParticipants = AddParticipants();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, addParticipants)
            fragmentTransaction.commit()
        }

        addChallengesButton.setOnClickListener(){
            val addChallenges = AddChallenges()
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, addChallenges)
            fragmentTransaction.commit()
        }

        backButton.setOnClickListener(){
            val profilesMenu = ProfilesMenu()
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, profilesMenu)
            fragmentTransaction.commit()
        }

        create.setOnClickListener(){
            if(StaticVariables.addedChallenges.size == 0 && StaticVariables.addedParticipants.size == 0){
                Toast.makeText(requireContext(), "Add challenges and participants!", Toast.LENGTH_SHORT).show()
            }
            else if(StaticVariables.addedChallenges.size == 0){
                Toast.makeText(requireContext(), "Add challenges!", Toast.LENGTH_SHORT).show()
            }
            else if(StaticVariables.addedParticipants.size == 0){
                Toast.makeText(requireContext(), "Add participants!", Toast.LENGTH_SHORT).show()
            }
            else{
                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                builder.setTitle("Profile name")
                val input = EditText(activity)
                input.setHint("Input profile name")
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    var m_Text = input.text.toString()
                    if(userDatabase.getProfilesByName(StaticVariables.loggedUser.id, m_Text).size == 0) {

                        if (m_Text.isNotEmpty() || m_Text.isNotBlank()) { 28832474
                            var pin = (10000000..99999999).random(Random(System.currentTimeMillis() / 1000L))
                            while(true){
                                pin = (10000000..99999999).random(Random(System.currentTimeMillis() / 1000L))
                                if(!userDatabase.getBooleanProfileByCode(pin.toString())){
                                    break
                                }
                            }

                            var challenges: String = ""
                            var players: String = ""
                            for (item in StaticVariables.addedChallenges) {
                                challenges += "$item;"
                            }
                            for (item in StaticVariables.addedParticipants) {
                                players += "$item;"
                            }
                            if(challenges.last().equals(";")){
                                challenges.dropLast(1)
                            }
                            if(players.last().equals(";")){
                                players.dropLast(1)
                            }
                            userDatabase.addProfile(
                                StaticVariables.loggedUser.id,
                                m_Text,
                                pin.toString(),
                                challenges,
                                players,
                                "Waiting for draw!"
                            )
                            StaticVariables.addedChallenges = mutableListOf()
                            StaticVariables.addedParticipants = mutableListOf()
                            Toast.makeText(
                                requireContext(),
                                "A new profile has been created!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val yourProfiles = YourProfiles()
                            val fragmentTransaction: FragmentTransaction =
                                fragmentManager!!.beginTransaction()
                            fragmentTransaction.replace(R.id.mainActivity, yourProfiles)
                            fragmentTransaction.commit()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Empty name, try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else{
                        Toast.makeText(
                            requireContext(),
                            "Profile exists, input different name!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
                builder.show()


            }


        }

        return view
    }
}