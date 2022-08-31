package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R

class ChooseFragment : Fragment() {

    lateinit var loginButton: View
    lateinit var registerButton: View
    lateinit var joinByCodeButton: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose, container, false)
        loginButton = view.findViewById(R.id.loginButton)
        registerButton = view.findViewById(R.id.registerButton)
        joinByCodeButton = view.findViewById(R.id.joinByCodeButton)
        val userDatabase: UserDatabase = UserDatabase(requireActivity())

        if(userDatabase.getChallenges(-1).size == 0){
            userDatabase.addChallenge(-1,"Grab a nearby dancer and challenge them to a dance-off.")
            userDatabase.addChallenge(-1,"Get a selfie with a blonde, brunette and a red head.")
            userDatabase.addChallenge(-1,"Challenge a stranger to a press up competition and win.")
            userDatabase.addChallenge(-1,"Peel a potato with your bare teeth.")
            userDatabase.addChallenge(-1,"Approach a random stranger and explain that you are going to perform a magic trick. The challenge is to keep their attention for as long as possible.        ")
            userDatabase.addChallenge(-1,"Choose a random stranger and copy his movements for 10 minutes.")
            userDatabase.addChallenge(-1,"Kiss a random girl.")
        }

        loginButton.setOnClickListener(){
            val loginFragment = LoginFragment()
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, loginFragment)
            fragmentTransaction.commit()
        }

        registerButton.setOnClickListener(){
            val registerFragment = RegisterFragment()
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, registerFragment)
            fragmentTransaction.commit()
        }

        joinByCodeButton.setOnClickListener(){
            val joinByCodeFragment = JoinByCodeFragment()
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, joinByCodeFragment)
            fragmentTransaction.commit()
        }

        return view
    }

}