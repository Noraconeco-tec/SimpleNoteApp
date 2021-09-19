package jp.co.noraconeco.simplenoteapp.database.note

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DBNoteDao {
    @Query("SELECT * FROM note ORDER BY created_date DESC")
    fun getAll(): List<DBNote>

    @Query("SELECT * FROM note ORDER BY created_date DESC")
    fun getAllFlow(): Flow<List<DBNote>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun get(id: String): DBNote?

    @Query("SELECT * FROM note WHERE id IN (:ids)")
    fun fetch(ids: List<String>): List<DBNote>

    @Update
    fun update(user: DBNote)

    @Insert
    fun insert(note: DBNote)

    @Insert
    fun insertAll(vararg notes: DBNote)

    @Delete
    fun delete(note: DBNote)

    @Query("DELETE FROM note")
    fun deleteAll()
}