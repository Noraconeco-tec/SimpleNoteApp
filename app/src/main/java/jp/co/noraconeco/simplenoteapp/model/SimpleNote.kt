package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.model.note.Note

interface SimpleNote {
    suspend fun getAllNote(): Collection<Note>
    suspend fun createNote(summary: String, contents: String)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun undoNote(note: Note)
}

