package com.example.noteapp.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class],
    version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun NoteDAO() : NoteDAO

    companion object{
        @Volatile
        private var Instance : NoteDatabase? = null

        fun getDatabase(context: Context) : NoteDatabase {
            return Instance?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "Sample"
                ).build().also { Instance = it }
            }
        }
    }
}