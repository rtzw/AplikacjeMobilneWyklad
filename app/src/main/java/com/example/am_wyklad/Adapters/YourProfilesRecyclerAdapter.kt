package com.example.am_wyklad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.am_wyklad.DataModel
import com.example.am_wyklad.Database.UserDatabase
import com.example.am_wyklad.Fragments.ChooseFragment
import com.example.am_wyklad.Fragments.ProfileFragment
import com.example.am_wyklad.R
import com.example.am_wyklad.StaticVariables
import com.google.androidgamesdk.gametextinput.Listener

class YourProfilesRecyclerAdapter(private val dataSet: MutableList<String>, val clickListner: ClickListner) : RecyclerView.Adapter<YourProfilesRecyclerAdapter.ViewHolder>() {

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
            StaticVariables.profile = holder.profile.text.toString()
            clickListner.onItemClick(dataSet[position])
        }

        holder.removeButton.setOnClickListener(){
            StaticVariables.deleteProfile = dataSet[position]
            clickListner.onItemClick(dataSet[position])
            removeItem(position)
        }
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyDataSetChanged()
    }

    interface ClickListner{
        fun onItemClick(string: String)
    }

}