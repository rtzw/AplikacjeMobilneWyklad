package com.example.am_wyklad.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.Adapters.ChallengesRecyclerAdapter
import com.example.am_wyklad.Adapters.ParticipantsRecyclerAdapter
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables


class AddChallenges : Fragment() {
    lateinit var backButton: View
    lateinit var addCustom: Button
    private lateinit var challengesRecyclerAdapter: ChallengesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_challenges, container, false)
        backButton = view.findViewById(R.id.backButton)
        addCustom = view.findViewById(R.id.addCustomChallenge)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycleViewChallenges)
        challengesRecyclerAdapter = ChallengesRecyclerAdapter(StaticVariables.addedChallenges)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = challengesRecyclerAdapter

        addCustom.setOnClickListener(){
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle("Challenge")
            val input = EditText(activity)
            input.setHint("Input challenge")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                var m_Text = input.text.toString()
                StaticVariables.addedParticipants.add(m_Text)
                challengesRecyclerAdapter = ChallengesRecyclerAdapter(StaticVariables.addedChallenges)
                recyclerView.adapter = challengesRecyclerAdapter
                challengesRecyclerAdapter.notifyDataSetChanged()

            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()

        }

        backButton.setOnClickListener(){
            val newProfile = NewProfile();
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.mainActivity, newProfile)
            fragmentTransaction.commit()
        }
        return view
    }


}