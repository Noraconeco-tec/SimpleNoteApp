package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NoteListViewModel @Inject constructor(
    private val simpleNote: SimpleNote
) : ViewModel() {

    val cellDataList: LiveData<List<NoteListSummaryCellViewModel>>
        get() = _cellDataList
    private val _cellDataList = MutableLiveData<List<NoteListSummaryCellViewModel>>(emptyList())

    init {
        fetchCellData()
    }

    private fun fetchCellData() {
        viewModelScope.launch {
            // getCarListData() is a suspend function
            val noteList = simpleNote.allNote

            val cellViewModels = noteList.map {
                NoteListSummaryCellViewModel(it.summary)
            }

            _cellDataList.value = cellViewModels
        }
    }
}