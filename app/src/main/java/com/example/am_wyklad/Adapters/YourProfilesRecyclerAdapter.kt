package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables

class YourProfilesRecyclerAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<YourProfilesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.your_profiles_layout, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount() = dataSet.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var profile: TextView
        var removeButton: Button

        init {
            profile = itemView.findViewById(R.id.profile)
            removeButton = itemView.findViewById(R.id.removeButton)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.profile.text = dataSet[position]

        holder.profile.setOnClickListener{

        }

        holder.removeButton.setOnClickListener(){
            removeItem(position)
        }
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyDataSetChanged()
    }

}