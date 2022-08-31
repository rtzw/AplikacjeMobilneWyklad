package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.Adapters.*
import com.example.am_wyklad.Database.Profile
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables

class ProfileFragment : Fragment() {

    lateinit var backButton: View
    lateinit var drawButton: View
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
        val challenges = profile.challenges.split(";")
        val result = profile.challenges.split(";")
        val participantsMutableList = participants.toMutableList()
        val challengesMutableList = challenges.toMutableList()
        val resultMutableList = result.toMutableList()
        code.text = "Code: " + profile.code
        // participants
        val recyclerViewParticipants: RecyclerView = view.findViewById(R.id.participants)
        profileParticipantsRecyclerAdapter = ProfileParticipantsRecyclerAdapter(participantsMutableList
            )
        val layoutManagerParticipants = LinearLayoutManager(activity)
        recyclerViewParticipants.layoutManager = layoutManagerParticipants
        recyclerViewParticipants.adapter = profileParticipantsRecyclerAdapter
        profileParticipantsRecyclerAdapter.notifyDataSetChanged()

        // challengess
        val recyclerViewChallenges: RecyclerView = view.findViewById(R.id.challenges)
        profileChallengesRecycleAdapter = ProfileChallengesRecycleAdapter(challengesMutableList
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
            println("Witam")
            if(participantsMutableList.size <= challengesMutableList.size){
                challenges.shuffled()
                for(i in 0..(participants.size - 1)){
                    resultMutableList.add(challenges.get(i) + " for " + participantsMutableList.get(i))
                }
            }
            else{
                challenges.shuffled()
                var j = 0
                for(i in 0..(participants.size - 1)){
                    if((challenges.size + 1) % (i + 1) == 0) j = 0
                    resultMutableList.add(challenges.get(j) + " --> " + participantsMutableList.get(i))
                    j++
                }
            }
            profileResultRecyclerAdapter = ProfileResultRecyclerAdapter(resultMutableList)
            profileResultRecyclerAdapter.notifyDataSetChanged()
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


}