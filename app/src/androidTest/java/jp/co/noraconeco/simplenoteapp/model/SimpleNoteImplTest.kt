package jp.co.noraconeco.simplenoteapp.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import jp.co.noraconeco.simplenoteapp.database.AppDatabase
import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.note.RoomNoteRepository
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class SimpleNoteImplTest {

    private lateinit var target: SimpleNoteImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        val noteRepository = RoomNoteRepository(database)
        target = SimpleNoteImpl(noteRepository)
    }

    @After
    fun tearDown() {

    }

    // getAllNote test
    @Test
    fun getAllNote_dataNotExisting() {
        val allNote = runBlocking {
            target.getAllNote()
        }

        assertEquals(0, allNote.count())
    }

    @Test
    fun getAllNote_threeDataExisting() {
        val noteCount = 3

        runBlocking {
            for (i in 1..noteCount) {
                target.createNote(
                    "Test summary $i",
                    "Test contents $i"
                )
            }
        }

        val allNote = runBlocking {
            target.getAllNote()
        }

        assertEquals(noteCount, allNote.count())
    }

    // getAllNoteFlow test
    @Test
    fun getAllNoteFlow_noAction() {
        var count = 0
        runBlocking {
            val flow = target.getAllNoteFlow()

            val job = launch(Dispatchers.IO) {
                flow.collect {
                    count++
                }
            }
            delay(1000L)
            job.cancel()

        }
        assertEquals(1, count)
    }

    @Test
    fun getAllNoteFlow_onceCreate() {
        var count = 0
        runBlocking {
            val flow = target.getAllNoteFlow()

            val job = launch(Dispatchers.IO) {
                flow.collect {
                    count++
                }
            }
            delay(1000L)
            target.createNote(
                "Test summary",
                "Test contents",
            )
            delay(1000L)
            job.cancel()

        }
        // init + create = 2
        assertEquals(2, count)
    }

    @Test
    fun getAllNoteFlow_onceCreateAndOnceUpdate() {
        var count = 0
        var notes: List<Note> = emptyList()
        runBlocking {
            val flow = target.getAllNoteFlow()

            val job = launch(Dispatchers.IO) {
                flow.collect {
                    count++
                    notes = it.toList()
                }
            }
            delay(1000L)
            target.createNote(
                "Test summary",
                "Test contents",
            )
            delay(1000L)
            val updatedNote =
                Note(
                    notes.first().id,
                    "Test summary updated",
                    "Test contents updated"
                )
            target.updateNote(updatedNote)
            delay(1000L)
            job.cancel()

        }
        // init + create + update = 3
        assertEquals(3, count)
    }


    // createNote test
    @Test
    fun createNote_savedSummaryAndContents() {
        val summary = "Test summary"
        val contents = "Test contents"
        runBlocking {
            target.createNote(summary, contents)
        }

        val allNote = runBlocking {
            target.getAllNote()
        }

        assertEquals(1, allNote.count())

        val firstNote = allNote.first()

        assertEquals(summary, firstNote.summary)
        assertEquals(contents, firstNote.contents)
    }

    @Test
    fun createNote_sameSummaryNotes() {
        val summary = "Test summary"
        val contents = "Test contents"
        runBlocking {
            for (i in 1..3) {
                target.createNote(
                    summary,
                    "$contents $i"
                )
            }
        }

        val allNote = runBlocking {
            target.getAllNote()
        }

        assertEquals(3, allNote.count())

        allNote.forEach { note ->
            assertEquals(summary, note.summary)
        }
    }

    @Test
    fun createNote_sameContentsNotes() {
        val summary = "Test summary"
        val contents = "Test contents"
        runBlocking {
            for (i in 1..3) {
                target.createNote(
                    "$summary $i",
                    contents
                )
            }
        }

        val allNote = runBlocking {
            target.getAllNote()
        }

        assertEquals(3, allNote.count())

        allNote.forEach { note ->
            assertEquals(contents, note.contents)
        }
    }

    //  updateNote test
    @Test
    fun updateNote_summaryUpdating() {
        val summary = "Test summary"
        val contents = "Test contents"
        val updateSummary = "$summary updated!"

        runBlocking {
            target.createNote(summary, contents)
        }

        val updatedNote = runBlocking {
            val createdNote = target.getAllNote().first()
            val updatedNote = createdNote.let {
                Note(
                    it.id,
                    updateSummary,
                    it.contents,
                    it.createdDate
                )
            }
            target.updateNote(updatedNote)

            target.getAllNote().first { it.id == createdNote.id }
        }

        assertEquals(updateSummary, updatedNote.summary)
        assertEquals(contents, updatedNote.contents)
    }

    @Test
    fun updateNote_contentsUpdating() {
        val summary = "Test summary"
        val contents = "Test contents"
        val updatedContents = "$contents updated!"

        runBlocking {
            target.createNote(summary, contents)
        }

        val updatedNote = runBlocking {
            val createdNote = target.getAllNote().first()
            val updatedNote = createdNote.let {
                Note(
                    it.id,
                    it.summary,
                    updatedContents,
                    it.createdDate
                )
            }
            target.updateNote(updatedNote)

            target.getAllNote().first { it.id == createdNote.id }
        }

        assertEquals(summary, updatedNote.summary)
        assertEquals(updatedContents, updatedNote.contents)
    }

    @Test
    fun updateNote_dataNotExisting() {
        val summary = "Test summary"
        val contents = "Test contents"

        val notExistingNote = Note(
            UUID.randomUUID(),
            summary,
            contents,
        )

        val loadedNote = runBlocking {
            target.updateNote(notExistingNote)
            target.getAllNote().firstOrNull { it.id == notExistingNote.id }
        }

        assertNull(loadedNote)
    }

    // deleteNote test
    @Test
    fun deleteNote_dataExisting() {
        val summary = "Test summary"
        val contents = "Test contents"
        val afterCreatingNotes = runBlocking {
            target.createNote(
                summary,
                contents
            )
            target.getAllNote()
        }
        assertEquals(1, afterCreatingNotes.count())

        val existingNote = afterCreatingNotes.first()

        val afterDeletingNote = runBlocking {
            target.deleteNote(existingNote)
            target.getAllNote()
        }

        assertEquals(0, afterDeletingNote.count())
    }

    @Test
    fun deleteNote_dataNotExisting() {
        val summary = "Test summary"
        val contents = "Test contents"

        val notExistingNote = Note(
            UUID.randomUUID(),
            summary,
            contents,
        )

        val afterCreatingNotes = runBlocking {
            target.createNote(
                summary,
                contents
            )
            target.getAllNote()
        }
        assertEquals(1, afterCreatingNotes.count())

        val afterDeletingNote = runBlocking {
            target.deleteNote(notExistingNote)
            target.getAllNote()
        }

        assertEquals(1, afterDeletingNote.count())
    }

    // undoNote test
    @Test
    fun undoNote() {
        val summary = "Test summary"
        val contents = "Test contents"

        val afterCreatingNotes = runBlocking {
            target.createNote(
                summary,
                contents
            )
            target.getAllNote()
        }

        assertEquals(1, afterCreatingNotes.count())
        val loadedNote = afterCreatingNotes.first()

        val afterDeletingNotes = runBlocking {
            target.deleteNote(loadedNote)
            target.getAllNote()
        }
        assertEquals(0, afterDeletingNotes.count())

        val afterUndoingNotes = runBlocking {
            target.undoNote(loadedNote)
            target.getAllNote()
        }

        assertEquals(1, afterUndoingNotes.count())
        val undoNote = afterUndoingNotes.first()

        assertEquals(loadedNote.id, undoNote.id)
        assertEquals(loadedNote.summary, undoNote.summary)
        assertEquals(loadedNote.contents, undoNote.contents)
        assertEquals(loadedNote.createdDate, undoNote.createdDate)
    }
}