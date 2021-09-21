package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.FlowRepository
import java.util.*

internal interface NoteRepository : FlowRepository<Note, UUID>