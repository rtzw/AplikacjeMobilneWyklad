package com.example.am_wyklad

import com.example.am_wyklad.Database.User
import com.example.am_wyklad.Fragments.AddParticipants

class StaticVariables {
    companion object {
        lateinit var addedParticipants: MutableList<String>
        lateinit var addedChallenges: MutableList<String>
        lateinit var loggedUser: User
    }

    init {
        addedParticipants = mutableListOf<String>()
        addedChallenges = mutableListOf<String>()
    }
}