package com.example.appcm.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Nota")

class Nota (

    @PrimaryKey(autoGenerate = true) val id: Int ? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: Date
)