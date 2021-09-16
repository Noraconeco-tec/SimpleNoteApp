package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import java.util.*
import javax.inject.Inject


internal class DebugInMemoryNoteRepository @Inject constructor() : NoteRepository {

    private val noteList: MutableList<Note> = MutableList(100) {
        Note(UUID.randomUUID(), "サマリー$it", (0..it).joinToString { "内容" })
    }

    override fun add(item: Note) {
        noteList.add(item)
    }

    override fun remove(item: Note) {
        noteList.remove(item)
    }

    override fun addAll(item: Collection<Note>) {
        noteList.addAll(item)
    }

    override fun removeAll() {
        noteList.removeAll { true }
    }

    override fun update(item: Note) {
        noteList.find { it.id == item.id }?.apply {
            summary = item.summary
            contents = item.contents
        }
    }

    override fun get(index: Int): Note? = noteList.getOrNull(index)

    override fun fetch(selection: Collection<Int>): Collection<Note> =
        selection.map { noteList[it] }

    override fun getAll(): Collection<Note> = noteList
}