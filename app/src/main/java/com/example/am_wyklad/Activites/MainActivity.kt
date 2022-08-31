package com.example.am_wyklad.Activites

import android.Manifest
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.am_wyklad.Fragments.ChooseFragment
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
