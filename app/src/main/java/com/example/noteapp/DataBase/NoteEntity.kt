package com.example.noteapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    val title : String = "New Note",
    val author : String? = null,
    val notes : String,

    @PrimaryKey(autoGenerate = true)
    val noteNum : Int = 0
)
