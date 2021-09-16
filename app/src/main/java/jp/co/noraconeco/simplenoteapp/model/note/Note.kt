package jp.co.noraconeco.simplenoteapp.model.note

import java.util.UUID

data class Note(
    val id: UUID,
    var summary: String,
    var contents: String,
)
