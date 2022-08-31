package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.R

class ProfileParticipantsRecyclerAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<ProfileParticipantsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profile_layout, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount() = dataSet.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var profileText: TextView

        init {
            profileText = itemView.findViewById(R.id.profileText)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.profileText.text = dataSet[position]


    }

}