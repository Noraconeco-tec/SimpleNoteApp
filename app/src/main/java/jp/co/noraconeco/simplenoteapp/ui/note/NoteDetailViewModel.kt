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

        viewModelScope.launch {
            val noteData = simpleNote.allNote.firstOrNull {
                it.id.toString() == id
            }

            _summary.value = noteData?.summary
            _contents.value = noteData?.contents
        }
    }
}