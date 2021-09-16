package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import javax.inject.Inject

@HiltViewModel
internal class NoteCreatingViewModel @Inject constructor(
    private val simpleNote: SimpleNote
) : ViewModel() {

    val summary: MutableLiveData<String> = MutableLiveData("")
    val contents: MutableLiveData<String> = MutableLiveData("")
    private val _canCreate: MediatorLiveData<Boolean> = MediatorLiveData()
    val canCreate: LiveData<Boolean>
        get() = _canCreate

    init {
        this._canCreate.addSource(summary, ::updateCanCreate)
        this._canCreate.addSource(contents, ::updateCanCreate)
        updateCanCreate("")
    }

    private fun updateCanCreate(_unused: String) {
        _canCreate.value = !summary.value.isNullOrBlank() && !contents.value.isNullOrBlank()
    }

    fun createNote() {
        val summary = summary.value!!
        val contents = contents.value!!
        simpleNote.createNote(summary, contents)
    }
}