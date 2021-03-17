package com.example.appcm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appcm.model.Note
import com.example.appcm.data.NoteDB
import com.example.appcm.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository


    init {
        val noteDao = NoteDB.getDatabase(
            application
        ).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)

        }

    }

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNote(note)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }

}