package com.example.appcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appcm.dataclasses.Nota

class RecyclerNotas : AppCompatActivity() {


    private lateinit var myList: ArrayList<Nota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_notas)


        myList = ArrayList<Nota>()


        //
    }
}