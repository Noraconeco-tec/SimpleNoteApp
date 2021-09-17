package jp.co.noraconeco.simplenoteapp.database.note

import androidx.room.*

@Dao
interface DBNoteDao {
    @Query("SELECT * FROM note ORDER BY created_date DESC")
    fun getAll(): List<DBNote>

    @Query("SELECT * FROM note WHERE id = :id")
    fun get(id: String): DBNote?

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