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
internal class NoteDetailViewModel @Inject constructor(
    private val simpleNote: SimpleNote
) : ViewModel() {

    val summary: LiveData<String>
        get() = _summary
    private val _summary: MutableLiveData<String> = MutableLiveData()

    val contents: LiveData<String>
        get() = _contents
    private val _contents: MutableLiveData<String> = MutableLiveData()

    fun fetchNoteData(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteData = simpleNote.getAllNote().firstOrNull {
                it.id.toString() == id
            }

            _summary.postValue(noteData?.summary)
            _contents.postValue(noteData?.contents)
        }
    }
}