package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.Adapters.ParticipantsRecyclerAdapter
import com.example.am_wyklad.Adapters.YourProfilesRecyclerAdapter
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables


class YourProfiles : Fragment(), YourProfilesRecyclerAdapter.ClickListner {

    lateinit var backButton: View
    lateinit var addNewProfile: View
    private lateinit var yourProfilesRecyclerAdapter: YourProfilesRecyclerAdapter
    lateinit var userDatabase: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_your_profiles, container, false)

        backButton = view.findViewById(R.id.backButton)
        addNewProfile = view.findViewById(R.id.addNewProfile)
        userDatabase = UserDatabase(requireActivity())

        val recyclerView: RecyclerView = view.findViewById(R.id.profiles)
        yourProfilesRecyclerAdapter = YourProfilesRecyclerAdapter(userDatabase.getProfiles(StaticVariables.loggedUser.id),this)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = yourProfilesRecyclerAdapter

        addNewProfile.setOnClickListener(){
            val newProfile = NewProfile()
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, newProfile)
            fragmentTransaction.commit()
        }

        backButton.setOnClickListener(){
            val profilesMenu = ProfilesMenu();
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, profilesMenu)
            fragmentTransaction.commit()
        }

        return view
    }

    override fun onItemClick(string: String) {
        if(StaticVariables.deleteProfile.equals("null")) {
            val profileFragment = ProfileFragment()
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, profileFragment)
            fragmentTransaction.commit()
        }
        else{
            userDatabase.deleteProfile(StaticVariables.deleteProfile, StaticVariables.loggedUser.id)
            StaticVariables.deleteProfile = "null"
        }
    }


}