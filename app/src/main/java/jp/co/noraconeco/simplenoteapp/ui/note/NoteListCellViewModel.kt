package jp.co.noraconeco.simplenoteapp.ui.note

import jp.co.noraconeco.simplenoteapp.ui.CellViewModel

abstract class NoteListCellViewModel(
    override val viewType: Int,
    val deleteNote: () -> Unit,
    val undoNote: () -> Unit,
) : CellViewModel {

    abstract val id: String

    companion object {
        const val VIEW_TYPE_SUMMARY = 0
    }
}