package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables

class ChallengesRecyclerAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<ChallengesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.challenges_layout, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount() = dataSet.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var challenge: TextView
        var select: CheckBox

        init {
            challenge = itemView.findViewById(R.id.challenge)
            select = itemView.findViewById(R.id.checkbox)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.challenge.text = dataSet[position]
        if(StaticVariables.addedChallenges.contains(dataSet[position])){
            holder.select!!.isChecked=true
        }


        holder.select.setOnClickListener(){
            if(holder.select.isChecked){
                StaticVariables.addedChallenges.add(holder.challenge.text.toString())
            }
            else{
                StaticVariables.addedChallenges.remove(holder.challenge.text.toString())
            }
        }
    }

}