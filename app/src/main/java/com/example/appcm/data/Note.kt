package com.example.appcm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "note_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val date: Int
)