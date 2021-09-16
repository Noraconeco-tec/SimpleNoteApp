package jp.co.noraconeco.simplenoteapp.database.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class DBNote(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "contents") val contents: String
)