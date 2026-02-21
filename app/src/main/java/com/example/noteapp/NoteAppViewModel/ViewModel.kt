package com.example.noteapp.NoteAppViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteapp.DataBase.NoteEntity
import com.example.noteapp.DataBase.NoteRepo
import com.example.noteapp.UserApplication
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepo: NoteRepo) : ViewModel() {

    private val _notes : StateFlow<List<NoteEntity>> = noteRepo.notes()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )
    val notes = _notes

    fun insert(note : NoteEntity) {
        viewModelScope.launch {
            noteRepo.insertNote(note)
        }
    }

    fun delete(note : NoteEntity) {
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UserApplication)
                NoteViewModel(application.container.userRepo)
            }
        }
    }
}
