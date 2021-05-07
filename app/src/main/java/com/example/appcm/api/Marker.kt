package com.example.appcm.api

data class Marker(
    val id: Int,
    val descr: String,
    val mType: Int,
    val lat: String,
    val lng: String,
    val user_id: Int
)