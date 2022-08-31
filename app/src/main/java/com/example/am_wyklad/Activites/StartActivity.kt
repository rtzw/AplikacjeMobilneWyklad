package com.example.am_wyklad.Activites

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.example.am_wyklad.Fragments.StartFragment
import com.example.am_wyklad.R


class StartActivity : AppCompatActivity() {
    private var isFullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)
        isFullscreen = true
        setStartFragment()

        val thread = Thread(){
            run{
                Thread.sleep(5500)
            }
            runOnUiThread(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }

    private fun setStartFragment(){
        val startFragment = StartFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.startActivity, startFragment)
        fragmentTransaction.commit()
    }

}