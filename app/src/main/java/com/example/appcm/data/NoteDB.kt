package com.example.appcm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDB: RoomDatabase(){

    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDB?=null

        fun getDatabase(context: Context): NoteDB{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}