package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NoteListViewModel @Inject constructor() : ViewModel() {
    val tempString: String = "HELLO NOTE LIST"
}