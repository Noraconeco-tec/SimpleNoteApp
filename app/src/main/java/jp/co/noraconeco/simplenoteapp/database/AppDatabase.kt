package jp.co.noraconeco.simplenoteapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.noraconeco.simplenoteapp.database.note.DBNote
import jp.co.noraconeco.simplenoteapp.database.note.DBNoteDao

@Database(entities = [DBNote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): DBNoteDao
}