package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import java.util.*
import javax.inject.Inject


internal class DebugInMemoryNoteRepository @Inject constructor() : NoteRepository {

    private val noteList: MutableList<Note> = MutableList(100) {
        Note(UUID.randomUUID(), "サマリー$it", (0..it).joinToString { "内容" })
    }

    override suspend fun add(item: Note) {
        noteList.add(item)
    }

    override suspend fun remove(item: Note) {
        noteList.remove(item)
    }

    override suspend fun addAll(item: Collection<Note>) {
        noteList.addAll(item)
    }

    override suspend fun removeAll() {
        noteList.removeAll { true }
    }

    override suspend fun update(item: Note) {
        noteList.find { it.id == item.id }?.apply {
            summary = item.summary
            contents = item.contents
        }
    }

    override suspend fun get(index: UUID): Note? = noteList.firstOrNull { it.id == index }

    override suspend fun fetch(selection: Collection<UUID>): Collection<Note> =
        selection.map { get(it)!! }

    override suspend fun getAll(): Collection<Note> = noteList
}