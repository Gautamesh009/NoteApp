package com.example.noteapp.DataBase

class NoteRepo(private val noteDAO: NoteDAO) {
    fun notes() = noteDAO.notes()

    suspend fun insertNote(entity : NoteEntity) = noteDAO.insertNote(entity)

    suspend fun deleteNote(entity: NoteEntity) = noteDAO.deleteNote(entity)

    suspend fun updateNote(entity: NoteEntity) = noteDAO.updateNote(entity)

}