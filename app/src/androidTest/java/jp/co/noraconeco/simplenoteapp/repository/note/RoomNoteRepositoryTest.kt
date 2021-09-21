package jp.co.noraconeco.simplenoteapp.repository.note

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.noraconeco.simplenoteapp.database.AppDatabase
import jp.co.noraconeco.simplenoteapp.model.note.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
internal class RoomNoteRepositoryTest {

    lateinit var target: RoomNoteRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        target = RoomNoteRepository(database)
    }

    @After
    fun tearDown() {
    }

    // add test
    @Test
    fun add_readableInsertedData() {
        runBlocking {
            // テスト用のデータ
            val id = UUID.randomUUID()
            val summary = "Test summary"
            val contents = "Test contents"
            val createdDate = Date()
            val note = Note(id, summary, contents, createdDate)

            // 保存
            target.add(note)
            // 読込
            val notes = target.getAll()

            // 保存ができているか
            assertEquals(1, notes.count())

            val actual = notes.toList()[0]
            // 保存したものと読み込んだものが一致しているか
            assertEquals(note.id, actual.id)
            assertEquals(note.summary, actual.summary)
            assertEquals(note.contents, actual.contents)
            assertEquals(note.createdDate, actual.createdDate)
        }
    }

    @Test
    fun add_emptyDataParameters() {
        // テスト用のデータ
        runBlocking {
            val id = UUID.randomUUID()
            val summary = ""
            val contents = ""
            val createdDate = Date()
            val note = Note(id, summary, contents, createdDate)

            // 保存
            target.add(note)
            // 読込
            val notes = target.getAll()

            // 保存ができているか
            assertEquals(1, notes.count())

            val actual = notes.toList()[0]
            // 保存したものと読み込んだものが一致しているか
            assertEquals(note.id, actual.id)
            assertEquals(note.summary, actual.summary)
            assertEquals(note.contents, actual.contents)
            assertEquals(note.createdDate, actual.createdDate)
        }
    }

    @Test(expected = SQLiteConstraintException::class)
    fun add_sameParameters() {
        runBlocking {
            // テスト用のデータ
            val id = UUID.randomUUID()
            val summary = "Test summary"
            val contents = "Test contents"
            val createdDate = Date()
            val note = Note(id, summary, contents, createdDate)

            // 保存
            target.add(note)
            // 同じIDは2つ保存できない
            target.add(note)
            // 保存できた場合はテスト失敗
            fail()
        }
    }

    // remove test
    @Test
    fun remove_existsData() {
        val id = UUID.randomUUID()
        val summary = "Test summary"
        val contents = "Test contents"
        val createdDate = Date()
        val note = Note(id, summary, contents, createdDate)

        runBlocking {
            target.add(note)
        }

        runBlocking {
            val loadedNote = target.get(id)
            assertNotNull(loadedNote)
        }

        runBlocking {
            target.remove(note)
        }

        runBlocking {
            val loadedNote = target.get(id)
            assertNull(loadedNote)
        }
    }

    @Test
    fun remove_notExistsData() {
        val id = UUID.randomUUID()
        val summary = "Test summary"
        val contents = "Test contents"
        val createdDate = Date()
        val note = Note(id, summary, contents, createdDate)

        runBlocking {
            target.add(note)
        }

        runBlocking {
            val loadedNote = target.get(id)
            assertNotNull(loadedNote)
        }

        val otherId = UUID.randomUUID()
        val otherNote = Note(otherId, summary, contents, createdDate)
        runBlocking {
            target.remove(otherNote)
        }

        runBlocking {
            val loadedNote = target.get(id)
            assertNotNull(loadedNote)
        }
    }

    // addAll test
    @Test
    fun addAll_emptyList() {
        val noteList: List<Note> = emptyList()
        runBlocking {
            target.addAll(noteList)
        }

        runBlocking {
            val loadedNote = target.getAll()
            assertEquals(0, loadedNote.count())
        }
    }

    @Test
    fun addAll_newList() {
        val size = 10
        val newNoteList: List<Note> =
            (1..size).map {
                Note(
                    UUID.randomUUID(),
                    "Test summary $it",
                    "Test contents $it"
                )
            }

        runBlocking {
            target.addAll(newNoteList)
        }

        runBlocking {
            val loadedNote = target.getAll()
            assertEquals(size, loadedNote.count())
        }
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addAll_savedList() {
        val size = 10
        val newNoteList: List<Note> =
            (1..size).map {
                Note(
                    UUID.randomUUID(),
                    "Test summary $it",
                    "Test contents $it"
                )
            }

        runBlocking {
            target.addAll(newNoteList)
        }

        runBlocking {
            val loadedNote = target.getAll()
            assertEquals(size, loadedNote.count())
        }

        runBlocking {
            target.addAll(newNoteList)
        }

        fail()
    }

    // removeAll test
    @Test
    fun removeAll_allDelete() {
        val savingCount = 10

        // データを登録
        runBlocking {
            val notes = (0 until savingCount).map {
                Note(UUID.randomUUID(), "Summary $it", "Contents $it")
            }

            target.addAll(notes)
        }

        // 登録されているデータを確認
        runBlocking {
            val loadedNotes = target.getAll()
            assertEquals(loadedNotes.count(), savingCount)
        }

        // 登録されているデータを削除
        runBlocking {
            target.removeAll()
        }

        // データが0なことを確認
        runBlocking {
            val loadedNotes = target.getAll()
            assertEquals(loadedNotes.count(), 0)
        }
    }

    @Test
    fun removeAll_notExistsData() {
        // データが0なことを確認
        runBlocking {
            val loadedNotes = target.getAll()
            assertEquals(loadedNotes.count(), 0)
        }

        // データなしでremoveAll
        runBlocking {
            target.removeAll()
        }

        // データが0なことを確認
        runBlocking {
            val loadedNotes = target.getAll()
            assertEquals(loadedNotes.count(), 0)
        }
    }

    // update test
    @Test
    fun update_existsData() {
        val id = UUID.randomUUID()
        val summary = "Test summary"
        val contents = "Test contents"
        val createdDate = Date()
        val note = Note(id, summary, contents)

        val updatedSummary = "Test summary updated!!"
        val updatedContents = "Test contents updated!!"

        runBlocking {
            target.add(note)
        }

        val updateNote = Note(
            id,
            updatedSummary,
            updatedContents,
            createdDate
        )

        runBlocking {
            target.update(updateNote)
            val loadedNote = target.get(id)

            loadedNote!!.let {
                assertEquals(it.id, id)
                assertEquals(it.summary, updatedSummary)
                assertEquals(it.contents, updatedContents)
                assertEquals(it.createdDate, createdDate)
            }
        }
    }

    @Test
    fun update_notExistsData() {
        val id = UUID.randomUUID()
        val summary = "Test summary"
        val contents = "Test contents"
        val note = Note(id, summary, contents)

        runBlocking {
            target.update(note)
            val notes = target.getAll()
            assertEquals(notes.count(), 0)
        }
    }

    // get test
    @Test
    fun get_notExistsData() {
        val targetId = UUID.randomUUID()

        runBlocking {
            val loadedNote = target.get(targetId)

            assertNull(loadedNote)
        }
    }

    @Test
    fun get_existsData() {
        val ids = (1..3).map {
            UUID.randomUUID()
        }

        val targetIndex = 0
        val targetId = ids[targetIndex]

        val notes = ids.mapIndexed { index, id ->
            Note(
                id,
                "Test summary $index",
                "Test contents $index"
            )
        }

        runBlocking {
            target.addAll(notes)
        }

        runBlocking {
            val loadedNote = target.get(targetId)

            assertNotNull(loadedNote)

            loadedNote!!.let {
                assertEquals(it.id, targetId)
                assertEquals(it.summary, "Test summary $targetIndex")
                assertEquals(it.contents, "Test contents $targetIndex")
            }
        }
    }

    // fetch test
    @Test
    fun fetch_notExistsData() {
        val targetIds = (0..3).map {
            UUID.randomUUID()
        }

        runBlocking {
            val loadedNote = target.fetch(targetIds)
            assertEquals(loadedNote.count(), 0)
        }
    }

    @Test
    fun fetch_existsData() {
        val targetIds = (0..3).map {
            UUID.randomUUID()
        }

        val notes = targetIds.mapIndexed { index, id ->
            Note(
                id,
                "Test summary $index",
                "Test contents $index"
            )
        }
        runBlocking {
            target.addAll(notes)
        }

        runBlocking {
            val loadedNote = target.fetch(targetIds)
            assertEquals(loadedNote.count(), targetIds.count())
        }
    }

    @Test
    fun fetch_someExistingData() {
        val targetIds = (0..3).map {
            UUID.randomUUID()
        }
        val noExistingIds = (0..3).map {
            UUID.randomUUID()
        }

        val notes = targetIds.mapIndexed { index, id ->
            Note(
                id,
                "Test summary $index",
                "Test contents $index"
            )
        }
        runBlocking {
            target.addAll(notes)
        }

        runBlocking {
            val loadedNote = target.fetch(targetIds + noExistingIds)
            assertEquals(loadedNote.count(), targetIds.count())
        }
    }

    // getAll test
    @Test
    fun getAll_notExistsData() {
        runBlocking {
            val savedData = target.getAll()
            assertEquals(0, savedData.count())
        }
    }

    @Test
    fun getAll_existsData() {
        val savingCount = 10

        // データを登録
        runBlocking {
            val notes = (0 until savingCount).map {
                Note(UUID.randomUUID(), "Summary $it", "Contents $it")
            }
            target.addAll(notes)
            val savedData = target.getAll()
            assertEquals(savingCount, savedData.count())
        }
    }

    // getAllFlow test
    @Test
    fun getAllFlow_noUpdate() {
        val updateCount = 0
        var count = 0
        runBlocking {
            val collectJob = launch(Dispatchers.IO) {
                target.getAllFlow().cancellable().collect {
                    count++
                }
            }

            for (i in 0 until updateCount) {
                target.add(
                    Note(
                        UUID.randomUUID(),
                        "Test summary $i",
                        "Test contents $i",
                    )
                )
                delay(1000L)
            }

            delay(1000L)
            collectJob.cancel()
            assertEquals(updateCount + 1, count)
        }
    }

    @Test
    fun getAllFlow_threeAdd() {
        val updateCount = 3
        var count = 0
        runBlocking {
            val collectJob = launch(Dispatchers.IO) {
                target.getAllFlow().cancellable().collect {
                    count++
                }
            }

            for (i in 0 until updateCount) {
                target.add(
                    Note(
                        UUID.randomUUID(),
                        "Test summary $i",
                        "Test contents $i",
                    )
                )
                delay(1000L)
            }

            delay(1000L)
            collectJob.cancel()
            assertEquals(updateCount + 1, count)
        }
    }

    @Test
    fun getAllFlow_addAndUpdate() {
        val note = Note(
            UUID.randomUUID(),
            "Test summary",
            "Test contents",
        )

        var count = 0
        runBlocking {
            val collectJob = launch(Dispatchers.IO) {
                target.getAllFlow().cancellable().collect {
                    count++
                }
            }

            delay(1000L)
            target.add(note)
            delay(1000L)
            note.contents += "updated."
            target.update(note)
            delay(1000L)
            collectJob.cancel()
            // init + add + update
            assertEquals(1 + 1 + 1, count)
        }
    }
}