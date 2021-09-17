package jp.co.noraconeco.simplenoteapp.model

import jp.co.noraconeco.simplenoteapp.di.annotation.Release
import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SimpleNoteImpl @Inject constructor(
    @Release private val noteRepository: NoteRepository
) : SimpleNote {

    override suspend fun getAllNote(): Collection<Note> = noteRepository.getAll()

    override suspend fun createNote(summary: String, contents: String) {
        val createdDate = Date()
        val newNote = Note(createNewId(), summary, contents, createdDate)
        noteRepository.add(newNote)
    }

    override suspend fun updateNote(id: String, summary: String, contents: String) {
        val note = Note(UUID.fromString(id), summary, contents)
        noteRepository.update(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteRepository.remove(note)
    }

    override suspend fun undoNote(note: Note) {
        noteRepository.add(note)
    }

    private suspend fun createNewId(): UUID {
        var uuid = UUID.randomUUID()
        while (getAllNote().map { it.id }.contains(uuid)) {
            uuid = UUID.randomUUID()
        }
        return uuid
    }
}