package jp.co.noraconeco.simplenoteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteCreatingBinding

@AndroidEntryPoint
class NoteCreatingFragment : Fragment() {

    companion object {
        fun newInstance() = NoteCreatingFragment()
    }

    private val viewModel: NoteCreatingViewModel by viewModels()
    private var _binding: FragmentNoteCreatingBinding? = null
    private val binding: FragmentNoteCreatingBinding
        get() = _binding!!

    private val onClickSaveListener = View.OnClickListener {
        viewModel.createNote()
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteCreatingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.onClickSave = onClickSaveListener
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}