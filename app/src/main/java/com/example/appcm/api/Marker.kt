package com.example.appcm.api

data class Marker(
    val id: Int,
    val address: String,
    val country: String,
    val lat: String,
    val lng: String,
    val user_id: Int
)