package com.addyournotes.data

import com.addyournotes.model.Note

class NotesDataSource {
    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "First Note", description = "First Description"),
            Note(title = "Second Note", description = "Second Description"),
            Note(title = "Third Note", description = "Third Description")
        )
    }
}