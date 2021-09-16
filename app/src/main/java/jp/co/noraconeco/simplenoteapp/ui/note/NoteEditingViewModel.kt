package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NoteEditingViewModel @Inject constructor(
    private val simpleNote: SimpleNote
) : ViewModel() {

    private lateinit var id: String
    val summary: MutableLiveData<String> = MutableLiveData("")
    val contents: MutableLiveData<String> = MutableLiveData("")
    private val _canSave: MediatorLiveData<Boolean> = MediatorLiveData()
    val canSave: LiveData<Boolean>
        get() = _canSave

    init {
        this._canSave.addSource(summary, ::updateCanSave)
        this._canSave.addSource(contents, ::updateCanSave)
        updateCanSave("")
    }

    private fun updateCanSave(_unused: String) {
        _canSave.value = !summary.value.isNullOrBlank() && !contents.value.isNullOrBlank()
    }

    fun fetchNoteData(noteId: String?) {
        if (noteId == null) return

        viewModelScope.launch(Dispatchers.IO) {
            val note = simpleNote.getAllNote().first { it.id.toString() == noteId }
            id = noteId
            summary.postValue(note.summary)
            contents.postValue(note.contents)
        }
    }

    fun updateNote() {
        val id = this.id
        val summary = summary.value!!
        val contents = contents.value!!

        viewModelScope.launch(Dispatchers.IO) {
            simpleNote.updateNote(id, summary, contents)
        }
    }
}