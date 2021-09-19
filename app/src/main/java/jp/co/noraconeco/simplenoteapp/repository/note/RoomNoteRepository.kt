package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.database.AppDatabase
import jp.co.noraconeco.simplenoteapp.database.note.DBNote
import jp.co.noraconeco.simplenoteapp.model.note.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class RoomNoteRepository @Inject constructor(
    private val database: AppDatabase
) : NoteRepository {

    override suspend fun add(item: Note) {
        database.noteDao().insert(item.toDBNote())
    }

    override suspend fun remove(item: Note) {
        database.noteDao().delete(item.toDBNote())
    }

    override suspend fun addAll(item: Collection<Note>) {
        val dbNotes = item.map { it.toDBNote() }
        database.noteDao().insertAll(*dbNotes.toTypedArray())
    }

    override suspend fun removeAll() {
        database.noteDao().deleteAll()
    }

    override suspend fun update(item: Note) {
        database.noteDao().update(item.toDBNote())
    }

    override suspend fun get(index: UUID): Note? {
        return database.noteDao().get(index.toString())?.toNote()
    }

    override suspend fun fetch(selection: Collection<UUID>): Collection<Note> {
        return database
            .noteDao()
            .fetch(
                selection.map { it.toString() }
            )
            .map { it.toNote() }
    }

    override suspend fun getAll(): Collection<Note> {
        return database.noteDao().getAll().map { it.toNote() }
    }

    override suspend fun getAllFlow(): Flow<Collection<Note>> {
        return flow {
            database.noteDao().getAllFlow().collect { dbNotes ->
                emit(dbNotes.map { it.toNote() })
            }
        }
    }

    private fun Note.toDBNote(): DBNote {
        return run {
            DBNote(id.toString(), summary, contents, createdDate)
        }
    }

    private fun DBNote.toNote(): Note {
        return run {
            Note(UUID.fromString(id), summary, contents, createdDate)
        }
    }
}