package com.example.appcm.api

data class LoginResponse (
    val error: Boolean,
    val message: String,
    val user: User
)