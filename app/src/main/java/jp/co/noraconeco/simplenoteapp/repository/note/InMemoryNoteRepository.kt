package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import javax.inject.Inject

internal class InMemoryNoteRepository @Inject constructor() : NoteRepository {

    private val noteList: MutableList<Note> = mutableListOf()

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

    override suspend fun get(index: Int): Note? = noteList.getOrNull(index)

    override suspend fun fetch(selection: Collection<Int>): Collection<Note> =
        selection.map { noteList[it] }

    override suspend fun getAll(): Collection<Note> = noteList
}