package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository
import javax.inject.Inject

internal class SimpleNoteImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : SimpleNote {

    override val allNote: Collection<Note> = noteRepository.getAll()

    override fun createNote(note: Note) {
        noteRepository.add(note)
    }

    override fun deleteNote(note: Note) {
        noteRepository.remove(note)
    }
}