package com.example.am_wyklad.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.am_wyklad.Fragments.ChooseFragment
import com.example.am_wyklad.Fragments.LoginFragment
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setChooseFragment()
        StaticVariables()

    }

    private fun setChooseFragment(){
        val chooseFragment = ChooseFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainActivity, chooseFragment)
        fragmentTransaction.commit()
    }
}