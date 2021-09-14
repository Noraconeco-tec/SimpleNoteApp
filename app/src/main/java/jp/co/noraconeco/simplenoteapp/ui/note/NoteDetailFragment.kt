package jp.co.noraconeco.simplenoteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteDetailBinding

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()

        const val KEY_NOTE_ID = "noteId"
    }

    private val viewModel: NoteDetailViewModel by viewModels()
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding: FragmentNoteDetailBinding
        get() = _binding!!

    private val noteId: String?
        get() = arguments?.getString(KEY_NOTE_ID)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchNoteData(noteId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}