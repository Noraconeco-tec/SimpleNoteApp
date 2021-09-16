package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.model.note.Note

interface SimpleNote {
    suspend fun getAllNote(): Collection<Note>
    suspend fun createNote(summary: String, contents: String)
    suspend fun updateNote(id: String, summary: String, contents: String)
    suspend fun deleteNote(note: Note)
}

