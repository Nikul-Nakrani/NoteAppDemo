package com.example.mvvmdemowithroom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from notesTable order by id asc")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

}