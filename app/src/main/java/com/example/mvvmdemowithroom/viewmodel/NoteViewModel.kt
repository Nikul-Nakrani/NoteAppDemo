package com.example.mvvmdemowithroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemowithroom.database.Note
import com.example.mvvmdemowithroom.database.NoteDatabase
import com.example.mvvmdemowithroom.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    // for our all notes list and repository
    val allNotes: LiveData<List<Note>>
    val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }


    // on below line we are creating a new method for deleting a note.
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    // on below line we are creating a new method for updating a note.
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    // on below line we are creating a new method for adding a new note to our database
    fun addNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }


}