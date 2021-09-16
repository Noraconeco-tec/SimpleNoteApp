package jp.co.noraconeco.simplenoteapp.database.note

import androidx.room.*

@Dao
interface DBNoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<DBNote>

    @Update
    fun update(user: DBNote)

    @Insert
    fun insert(note: DBNote)

    @Insert
    fun insertAll(vararg notes: DBNote)

    @Delete
    fun delete(note: DBNote)
}