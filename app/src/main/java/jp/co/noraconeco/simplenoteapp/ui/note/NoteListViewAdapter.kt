package jp.co.noraconeco.simplenoteapp.ui.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import jp.co.noraconeco.simplenoteapp.BR
import jp.co.noraconeco.simplenoteapp.R

class NoteListViewAdapter :
    RecyclerView.Adapter<NoteListViewAdapter.ViewHolder>() {

    companion object {
        const val DEFAULT_VIEW_TYPE = 0
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cellViewModel: NoteListCellViewModel) {
            val clickListener =
                Navigation.createNavigateOnClickListener(
                    R.id.show_note_detail,
                    bundleOf(NoteDetailFragment.KEY_NOTE_ID to cellViewModel.id)
                )

            binding.setVariable(BR.viewModel, cellViewModel)
            binding.setVariable(BR.clickListener, clickListener)
        }
    }

    private var cellViewModels: List<NoteListCellViewModel> = emptyList()
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
        val layoutId = viewTypeToLayoutId[viewType] ?: DEFAULT_VIEW_TYPE
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cellViewModels[position])
    }

    override fun getItemCount(): Int = cellViewModels.count()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<NoteListCellViewModel>?) {
        cellViewModels = items ?: emptyList()
        notifyDataSetChanged()
    }
}