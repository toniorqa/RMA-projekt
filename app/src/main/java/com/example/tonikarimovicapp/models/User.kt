package com.example.tonikarimovicapp.models

data class User(
    val id: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val server: String = ""
)