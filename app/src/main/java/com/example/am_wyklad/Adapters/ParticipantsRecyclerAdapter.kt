package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.am_wyklad.R

class ParticipantsRecyclerAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<ParticipantsRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.participants_layout, parent, false)
        return ViewHolder(v)
    }




    override fun getItemCount() = dataSet.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var participants: TextView
        var removeButton: Button

        init {
            participants = itemView.findViewById(R.id.participants)
            removeButton = itemView.findViewById(R.id.removeButton)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.participants.text = dataSet[position]

        holder.removeButton.setOnClickListener(){
            removeItem(position)
        }
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyDataSetChanged()
    }


}