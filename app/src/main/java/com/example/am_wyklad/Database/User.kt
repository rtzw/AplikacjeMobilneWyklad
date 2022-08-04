package com.example.am_wyklad.Database


import java.io.Serializable


data class User(val id: Int = -1, val login: String, val password: String, val name: String)