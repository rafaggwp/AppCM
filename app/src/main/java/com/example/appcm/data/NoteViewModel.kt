package com.example.appcm.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository


    init {
        val noteDao = NoteDB.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNote(note: Note){
        viewModelScope.launch { Dispatchers.IO
        repository.addNote(note)
        }
    }
}