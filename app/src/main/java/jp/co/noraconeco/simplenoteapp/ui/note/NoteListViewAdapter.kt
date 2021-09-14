package jp.co.noraconeco.simplenoteapp.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.co.noraconeco.simplenoteapp.BR

class NoteListViewAdapter :
    RecyclerView.Adapter<NoteListViewAdapter.ViewHolder>() {

    companion object {
        const val DEFAULT_VIEW_TYPE = 0
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cellViewModel: NoteListCellViewModel) {
            binding.setVariable(BR.viewModel, cellViewModel)
        }
    }

    var cellViewModels: List<NoteListCellViewModel> = emptyList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()

    override fun getItemViewType(position: Int): Int {
        val item = cellViewModels[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)) {
            viewTypeToLayoutId[item.viewType] = item.layoutId
        }
        return item.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewType = viewTypeToLayoutId[viewType] ?: DEFAULT_VIEW_TYPE
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            inflater,
            viewType,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cellViewModels[position])
    }

    override fun getItemCount(): Int = cellViewModels.count()

    fun updateItems(items: List<NoteListCellViewModel>?) {
        cellViewModels = items ?: emptyList()
    }
}