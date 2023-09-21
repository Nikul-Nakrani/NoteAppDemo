package com.example.mvvmdemowithroom.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String,
    val description: String,
    val timeStamp: String,
)
