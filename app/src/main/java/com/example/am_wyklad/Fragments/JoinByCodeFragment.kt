package com.example.am_wyklad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables


class JoinByCodeFragment : Fragment()  {

    lateinit var backButton: View
    lateinit var joinButton: View
    lateinit var codeInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_join_by_code, container, false)
        backButton = view.findViewById(R.id.backButton)
        joinButton = view.findViewById(R.id.joinButton)
        codeInput = view.findViewById(R.id.codeInput)
        val userDatabase: UserDatabase = UserDatabase(requireActivity())


        joinButton.setOnClickListener(){
            if(userDatabase.getProfileByCode(codeInput.text.toString()).size >0){
                StaticVariables.code = codeInput.text.toString()
                StaticVariables.profile = userDatabase.getProfileByCode(codeInput.text.toString()).get(0).name
                val profileFragment = ProfileFragment();
                val fragmentTransaction: FragmentTransaction =
                    fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.mainActivity, profileFragment)
                fragmentTransaction.commit()
            }
            else{
                Toast.makeText(requireContext(), "Incorrect code!", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener(){
            val chooseFragment = ChooseFragment();
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, chooseFragment)
            fragmentTransaction.commit()
        }

        return view
    }

}