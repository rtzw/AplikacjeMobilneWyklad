package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
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

        loginButton.setOnClickListener(){
            val loginFragment = LoginFragment();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, loginFragment)
            fragmentTransaction.commit()
        }

        registerButton.setOnClickListener(){
            val registerFragment = RegisterFragment();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, registerFragment)
            fragmentTransaction.commit()
        }

        joinByCodeButton.setOnClickListener(){
            val joinByCodeFragment = JoinByCodeFragment();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, joinByCodeFragment)
            fragmentTransaction.commit()
        }

        return view
    }

}