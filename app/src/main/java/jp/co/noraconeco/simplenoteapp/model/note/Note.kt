package jp.co.noraconeco.simplenoteapp.model.note

import java.util.UUID

data class Note(
    val id: UUID,
    val summary: String,
    val contents: String,
)
