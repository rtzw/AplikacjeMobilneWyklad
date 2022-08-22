package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables


class NewProfile : Fragment() {


    lateinit var backButton: View
    lateinit var addChallengesButton: View
    lateinit var addParticipantsButton: View
    lateinit var addParticipantsText: TextView

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

        addParticipantsText.text = StaticVariables.addedParticipants.size.toString() + " added"
        println(StaticVariables.addedParticipants)
        addParticipantsButton.setOnClickListener(){
            val addParticipants = AddParticipants();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, addParticipants)
            fragmentTransaction.commit()
        }

        addChallengesButton.setOnClickListener(){
            val addChallanges = AddChallanges();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, addChallanges)
            fragmentTransaction.commit()
        }

        backButton.setOnClickListener(){
            val profilesMenu = ProfilesMenu();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, profilesMenu)
            fragmentTransaction.commit()
        }
        return view
    }
}