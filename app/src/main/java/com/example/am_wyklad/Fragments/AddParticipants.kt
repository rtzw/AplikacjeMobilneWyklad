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
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.R
import com.example.am_wyklad.Adapters.ParticipantsRecyclerAdapter
import com.example.am_wyklad.StaticVariables

class AddParticipants : Fragment() {
    lateinit var backButton: View
    lateinit var addParticipant: View


    private lateinit var participantsRecyclerAdapter: ParticipantsRecyclerAdapter


    val dataSet: Array<String> = arrayOf()
//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_participants, container, false)

        backButton = view.findViewById(R.id.backButton)
        addParticipant = view.findViewById(R.id.addParticipant)


        val recyclerView: RecyclerView = view.findViewById(R.id.recycleViewParticipants)
        participantsRecyclerAdapter = ParticipantsRecyclerAdapter(StaticVariables.addedParticipants)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = participantsRecyclerAdapter


        addParticipant.setOnClickListener(){
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle("Name")
            val input = EditText(activity)
            input.setHint("Input name")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                var m_Text = input.text.toString()
                if(m_Text.isNotEmpty() || m_Text.isNotBlank()) {
                    StaticVariables.addedParticipants.add(m_Text)
                    participantsRecyclerAdapter =
                        ParticipantsRecyclerAdapter(StaticVariables.addedParticipants)
                    recyclerView.adapter = participantsRecyclerAdapter
                    participantsRecyclerAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(requireContext(), "Empty name, try again!", Toast.LENGTH_SHORT).show()
                }
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