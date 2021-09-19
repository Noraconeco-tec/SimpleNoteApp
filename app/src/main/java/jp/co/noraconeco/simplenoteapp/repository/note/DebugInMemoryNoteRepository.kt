package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


internal class DebugInMemoryNoteRepository @Inject constructor() : NoteRepository {

    companion object {
        const val FLOW_DELAY_MILLIS = 500L
    }

    @Volatile
    private var needUpdate: Boolean = false

    private val noteList: MutableList<Note> = mutableListOf<Note>().apply {
        CoroutineScope(Dispatchers.IO).launch {
            (1..100).forEach {
                val note = Note(UUID.randomUUID(), "サマリー$it", (0..it).joinToString { "内容" })
                this@DebugInMemoryNoteRepository.add(note)
                delay(1L)
            }
        }
    }

    override suspend fun getAllFlow(): Flow<Collection<Note>> {
        return flow {
            while (true) {
                if (needUpdate) {
                    emit(noteList.toList().sortedByDescending { it.createdDate })
                    needUpdate = false
                }
                delay(FLOW_DELAY_MILLIS)
            }
        }
    }

    override suspend fun add(item: Note) {
        noteList.add(item)
        needUpdate = true
    }

    override suspend fun remove(item: Note) {
        noteList.remove(item)
        needUpdate = true
    }

    override suspend fun addAll(item: Collection<Note>) {
        noteList.addAll(item)
        needUpdate = true
    }

    override suspend fun removeAll() {
        noteList.removeAll { true }
        needUpdate = true
    }

    override suspend fun update(item: Note) {
        noteList.find { it.id == item.id }?.apply {
            summary = item.summary
            contents = item.contents
        }
        needUpdate = true
    }

    override suspend fun get(index: UUID): Note? = noteList.firstOrNull { it.id == index }

    override suspend fun fetch(selection: Collection<UUID>): Collection<Note> =
        selection.map { get(it)!! }

    override suspend fun getAll(): Collection<Note> = noteList
}