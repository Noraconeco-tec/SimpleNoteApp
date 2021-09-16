package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun fetchCellData() {
        viewModelScope.launch(Dispatchers.IO) {
            // getCarListData() is a suspend function
            val noteList = simpleNote.getAllNote()

            val cellViewModels = noteList.map {
                NoteListSummaryCellViewModel(it.id.toString(), it.summary, it.contents)
            }

            _cellDataList.postValue(cellViewModels)
        }
    }
}