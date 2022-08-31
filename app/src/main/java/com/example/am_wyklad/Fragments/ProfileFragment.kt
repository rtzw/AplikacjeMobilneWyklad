package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var draw: View

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
        draw = view.findViewById(R.id.draw)

        val userDatabase: UserDatabase = UserDatabase(requireActivity())
        val profile: Profile =
            userDatabase.getProfile(StaticVariables.loggedUser.id,StaticVariables.profile).get(0)

        val participants = profile.players.split(";")
        val challenges = profile.challenges.split(";")
        val participantsMutableList = participants.toMutableList()
        val challengesMutableList = challenges.toMutableList()

        // participants
        val recyclerViewParticipants: RecyclerView = view.findViewById(R.id.participants)
        profileParticipantsRecyclerAdapter = ProfileParticipantsRecyclerAdapter(participantsMutableList
            )
        val layoutManagerParticipants = LinearLayoutManager(activity)
        recyclerViewParticipants.layoutManager = layoutManagerParticipants
        recyclerViewParticipants.adapter = profileParticipantsRecyclerAdapter

        // challengess
        val recyclerViewChallenges: RecyclerView = view.findViewById(R.id.challenges)
        profileChallengesRecycleAdapter = ProfileChallengesRecycleAdapter(challengesMutableList
        )
        val layoutManagerChallenges = LinearLayoutManager(activity)
        recyclerViewChallenges.layoutManager = layoutManagerChallenges
        recyclerViewChallenges.adapter = profileChallengesRecycleAdapter

        // result
        val recyclerViewResult: RecyclerView = view.findViewById(R.id.result)
        profileResultRecyclerAdapter = ProfileResultRecyclerAdapter(
            mutableListOf() //todo
        )
        val layoutManagerResult = LinearLayoutManager(activity)
        recyclerViewResult.layoutManager = layoutManagerResult
        recyclerViewResult.adapter = profileResultRecyclerAdapter

        draw.setOnClickListener(){

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