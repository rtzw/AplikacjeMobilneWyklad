package com.example.am_wyklad.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R

class LoginFragment : Fragment() {
    lateinit var loginButton: View
    lateinit var loginInput: EditText
    lateinit var passwordInput: EditText
    lateinit var backButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = view.findViewById(R.id.loginButton)
        loginInput = view.findViewById(R.id.loginInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        backButton = view.findViewById(R.id.backButton)
        val userDatabase: UserDatabase = UserDatabase(requireActivity())

        loginButton.setOnClickListener(){
            if(userDatabase.isRegistered(loginInput.text.toString(), passwordInput.text.toString())) {

                val profilesMenu = ProfilesMenu();
                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.mainActivity, profilesMenu)
                    fragmentTransaction.commit()
                }
            else{
                Toast.makeText(requireContext(), "Konto nie istnieje!", Toast.LENGTH_LONG).show()
            }
        }

        backButton.setOnClickListener(){
            val chooseFragment = ChooseFragment();
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, chooseFragment)
            fragmentTransaction.commit()
        }
        return view
    }
}