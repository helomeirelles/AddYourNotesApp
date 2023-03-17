package com.addyournotes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.addyournotes.model.Note
import com.addyournotes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                _noteList.value = listOfNotes
            }
        }
    }
//    private var noteList = mutableStateListOf<Note>()

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note = note) }

    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note = note) }

    suspend fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note = note) }
}