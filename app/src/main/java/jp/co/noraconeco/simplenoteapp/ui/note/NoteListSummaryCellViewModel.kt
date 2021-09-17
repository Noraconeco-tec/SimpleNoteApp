package jp.co.noraconeco.simplenoteapp.ui.note

import jp.co.noraconeco.simplenoteapp.R

class NoteListSummaryCellViewModel(
    override val id: String,
    val summary: String,
    val contents: String,
    deleteNote: () -> Unit,
    undoNote: () -> Unit
) : NoteListCellViewModel(VIEW_TYPE_SUMMARY, deleteNote, undoNote) {

    override val layoutId: Int
        get() = R.layout.cell_note_list
}