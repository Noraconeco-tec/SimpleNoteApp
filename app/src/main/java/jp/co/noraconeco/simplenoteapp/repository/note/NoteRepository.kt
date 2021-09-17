package jp.co.noraconeco.simplenoteapp.repository.note

import jp.co.noraconeco.simplenoteapp.model.note.Note
import jp.co.noraconeco.simplenoteapp.repository.Repository
import java.util.*

internal interface NoteRepository : Repository<Note, UUID>