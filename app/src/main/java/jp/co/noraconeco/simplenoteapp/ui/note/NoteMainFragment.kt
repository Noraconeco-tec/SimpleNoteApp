package jp.co.noraconeco.simplenoteapp.ui.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.noraconeco.simplenoteapp.R

class NoteMainFragment : Fragment() {

    companion object {
        fun newInstance() = NoteMainFragment()
    }

    private lateinit var viewModel: NoteMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_note_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}