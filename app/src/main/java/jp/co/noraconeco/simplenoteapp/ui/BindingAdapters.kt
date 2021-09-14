package jp.co.noraconeco.simplenoteapp.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.noraconeco.simplenoteapp.ui.note.NoteListCellViewModel
import jp.co.noraconeco.simplenoteapp.ui.note.NoteListViewAdapter

object BindingAdapters {

    @BindingAdapter("cellViewModels")
    @JvmStatic
    fun bindNoteCellViewModels(
        recyclerView: RecyclerView,
        cellViewModels: List<NoteListCellViewModel>?
    ) {
        val adapter = getOrCreateAdapter(recyclerView)
        adapter.updateItems(cellViewModels)
    }

    private fun getOrCreateAdapter(recyclerView: RecyclerView): NoteListViewAdapter {
        return if (recyclerView.adapter is NoteListViewAdapter) {
            recyclerView.adapter as NoteListViewAdapter
        } else {
            val adapter = NoteListViewAdapter()
            recyclerView.adapter = adapter
            adapter
        }
    }
}