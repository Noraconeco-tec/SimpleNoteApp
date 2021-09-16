package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.model.note.Note

interface SimpleNote {
    val allNote: Collection<Note>
    fun createNote(summary: String, contents: String)
    fun deleteNote(note: Note)
}

