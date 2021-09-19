package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import jp.co.noraconeco.simplenoteapp.model.note.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
        observeAllNote()
    }

    private fun observeAllNote() {
        viewModelScope.launch(Dispatchers.IO) {
            simpleNote.getAllNoteFlow().collect { noteList ->
                updateCellDataList(noteList)
            }
        }
    }

    private fun updateCellDataList(noteList: Collection<Note>) {
        val cellViewModels = noteList.map {

            val deleteNote = createNoteDeletingCoroutines(it)
            val undoNote = createNoteDeleteUndoCoroutines(it)

            NoteListSummaryCellViewModel(
                it.id.toString(),
                it.summary,
                it.contents,
                deleteNote,
                undoNote
            )
        }
        _cellDataList.postValue(cellViewModels)
    }

    private fun createNoteDeletingCoroutines(note: Note): () -> Unit {
        return {
            viewModelScope.launch(Dispatchers.IO) {
                simpleNote.deleteNote(note)
            }
        }
    }

    private fun createNoteDeleteUndoCoroutines(note: Note): () -> Unit {
        return {
            viewModelScope.launch(Dispatchers.IO) {
                simpleNote.undoNote(note)
            }
        }
    }
}