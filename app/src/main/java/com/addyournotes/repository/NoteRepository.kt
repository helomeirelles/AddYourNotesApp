package com.addyournotes.repository

import com.addyournotes.data.NoteDatabaseDAO
import com.addyournotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(val noteDatabaseDAO: NoteDatabaseDAO) {
    suspend fun addNote(note: Note) = noteDatabaseDAO.insert(note = note)
    suspend fun updateNote(note: Note) = noteDatabaseDAO.update(note = note)
    suspend fun deleteNote(note:Note) = noteDatabaseDAO.deleteNote(note = note)
    fun deleteAll() = noteDatabaseDAO.deleteAll()
    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDAO.getNotes().flowOn(Dispatchers.IO).conflate()
}