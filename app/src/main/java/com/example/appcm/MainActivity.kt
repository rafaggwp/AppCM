package com.example.appcm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupActionBarWithNavController(findNavController(R.id.fragment))


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}