package jp.co.noraconeco.simplenoteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteEditingBinding

@AndroidEntryPoint
class NoteEditingFragment : Fragment() {

    companion object {
        fun newInstance() = NoteEditingFragment()

        const val KEY_NOTE_ID = "noteId"
    }

    private val viewModel: NoteEditingViewModel by viewModels()
    private var _binding: FragmentNoteEditingBinding? = null
    private val binding: FragmentNoteEditingBinding
        get() = _binding!!

    private val noteId: String?
        get() = arguments?.getString(KEY_NOTE_ID)

    private val onClickSaveListener = View.OnClickListener {
        viewModel.updateNote()
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.onClickSave = onClickSaveListener
        binding.lifecycleOwner = viewLifecycleOwner
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