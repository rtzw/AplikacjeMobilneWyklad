package com.example.am_wyklad.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.am_wyklad.Fragments.ChooseFragment
import com.example.am_wyklad.Fragments.LoginFragment
import com.example.am_wyklad.R

class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setChooseFragment()



//        val loginFragment = LoginFragment()
//        val fragmentManager = supportFragmentManager
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val blankFragment = BlankFragment()
//        fragmentTransaction.add(R.id.mainActivity, loginFragment)
//        fragmentTransaction.commit()

//        binding.fragmentContainer.

//        binding.loginButton.setOnClickListener(){
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            val blankFragment = BlankFragment()
//            fragmentTransaction.replace(R.id.fragmentContainer, blankFragment)
//            fragmentTransaction.commit()
//        }

    }
    private fun setLoginFragment(){
        val loginFragment = LoginFragment();
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainActivity, loginFragment)
        fragmentTransaction.commit()
    }

    private fun setChooseFragment(){
        val chooseFragment = ChooseFragment();
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainActivity, chooseFragment)
        fragmentTransaction.commit()
    }
}