package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        viewModelScope.launch(Dispatchers.IO) {
            simpleNote.createNote(summary, contents)
        }
    }
}