package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.R



class ProfilesMenu : Fragment() {

    lateinit var yourProfiles: View
    lateinit var createNewProfile: View
    lateinit var logoutButton: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profiles_menu, container, false)
        yourProfiles = view.findViewById(R.id.yourProfiles)
        createNewProfile = view.findViewById(R.id.createNewProfile)
        logoutButton = view.findViewById(R.id.logoutButton)

        yourProfiles.setOnClickListener(){
//            val loginFragment = LoginFragment();
//            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
//            fragmentTransaction.replace(R.id.mainActivity, loginFragment)
//            fragmentTransaction.commit()
        }

        createNewProfile.setOnClickListener(){
            val newProfile = NewProfile();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, newProfile)
            fragmentTransaction.commit()
        }

        logoutButton.setOnClickListener(){
            val chooseFragment = ChooseFragment();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, chooseFragment)
            fragmentTransaction.commit()

        }


        return view
    }


}