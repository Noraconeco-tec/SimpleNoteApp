package jp.co.noraconeco.simplenoteapp.ui.note

import jp.co.noraconeco.simplenoteapp.R

class NoteListSummaryCellViewModel(val summary: String) : NoteListCellViewModel(VIEW_TYPE_SUMMARY) {
    override val layoutId: Int
        get() = R.layout.cell_note_list
}