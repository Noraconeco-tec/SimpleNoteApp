package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
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
        noteId?.let { id ->
            val note = simpleNote.allNote.first { it.id.toString() == id }
            this.id = id
            summary.value = note.summary
            contents.value = note.contents
        }
    }

    fun updateNote() {
        val id = this.id
        val summary = summary.value!!
        val contents = contents.value!!
        simpleNote.updateNote(id, summary, contents)
    }
}