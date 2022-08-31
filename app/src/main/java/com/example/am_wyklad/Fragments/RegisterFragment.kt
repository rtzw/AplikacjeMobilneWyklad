package com.example.am_wyklad.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.Database.User
import com.example.am_wyklad.R


class RegisterFragment : Fragment() {
    lateinit var registerButton: View
    lateinit var backButton: View
    lateinit var loginInput: EditText
    lateinit var passwordInput: EditText
    lateinit var nameInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        registerButton = view.findViewById(R.id.registerButton)
        loginInput = view.findViewById(R.id.loginInput)
        nameInput = view.findViewById(R.id.nameInput)
        backButton = view.findViewById(R.id.backButton)
        passwordInput = view.findViewById(R.id.passwordInput)
        val userDatabase: UserDatabase = UserDatabase(requireActivity())

        registerButton.setOnClickListener(){
            if(userDatabase.isRegistered(loginInput.text.toString())) {
                Toast.makeText(requireContext(), "Account exists!", Toast.LENGTH_SHORT).show()
            }else{
                if(loginInput.text.toString().isNotEmpty() && loginInput.text.toString().isNotBlank()
                    && passwordInput.text.toString().isNotEmpty() && passwordInput.text.toString().isNotBlank()
                    && nameInput.text.toString().isNotEmpty() && nameInput.text.toString().isNotBlank()) {
                    addNewUser()
                    val loginFragment = LoginFragment();
                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.mainActivity, loginFragment)
                    fragmentTransaction.commit()
                }
                else{
                    Toast.makeText(requireContext(), "Incorrect data!", Toast.LENGTH_SHORT).show()
                }
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
    private fun addNewUser(): Boolean {
        val userDatabase: UserDatabase = UserDatabase(requireActivity())
        userDatabase.addUser(User(0, loginInput.text.toString(), passwordInput.text.toString(),nameInput.text.toString()))
        Toast.makeText(requireContext(), "Konto zostało utworzone!", Toast.LENGTH_SHORT).show()
        return true
    }

}