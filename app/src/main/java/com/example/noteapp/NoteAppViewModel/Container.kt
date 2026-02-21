package com.example.noteapp

import android.app.Application
import android.content.Context
import com.example.noteapp.DataBase.NoteDatabase
import com.example.noteapp.DataBase.NoteRepo

class Container(private val context: Context) {
    val userRepo : NoteRepo by lazy {
        NoteRepo(NoteDatabase.getDatabase(context).NoteDAO())
    }
}

class UserApplication : Application() {
    lateinit var container: Container

    override fun onCreate() {
        super.onCreate()
        container = Container(this)
    }
}