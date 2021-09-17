package jp.co.noraconeco.simplenoteapp.model.note

import java.util.*

data class Note(
    val id: UUID,
    var summary: String,
    var contents: String,
    val createdDate: Date = Date()
)
