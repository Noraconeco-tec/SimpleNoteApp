package jp.co.noraconeco.simplenoteapp.ui.note

import jp.co.noraconeco.simplenoteapp.ui.CellViewModel

abstract class NoteListCellViewModel(
    override val viewType: Int
) : CellViewModel {

    companion object {
        const val VIEW_TYPE_SUMMARY = 0
    }
}