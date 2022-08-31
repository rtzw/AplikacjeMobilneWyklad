package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.R

class ProfileResultRecyclerAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<ProfileResultRecyclerAdapter.ViewHolder>() {

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

    }

}
