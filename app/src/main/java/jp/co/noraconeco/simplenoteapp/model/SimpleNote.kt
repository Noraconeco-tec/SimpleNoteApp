package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.model.note.Note

internal interface SimpleNote {
    val allNote: Collection<Note>
    fun createNote(note: Note)
    fun deleteNote(note: Note)
}

