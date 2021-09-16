package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.di.annotation.Release
import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SimpleNoteImpl @Inject constructor(
    @Release private val noteRepository: NoteRepository
): SimpleNote {

    override val allNote: Collection<Note> = noteRepository.getAll()

    override fun createNote(summary: String, contents: String) {
        val newId = createNewId()
        val newNote = Note(
            newId,
            summary,
            contents
        )
        noteRepository.add(newNote)
    }

    override fun deleteNote(note: Note) {
        noteRepository.remove(note)
    }

    private fun createNewId(): UUID {
        var uuid = UUID.randomUUID()
        while (allNote.map { it.id }.contains(uuid)) {
            uuid = UUID.randomUUID()
        }
        return uuid
    }
}