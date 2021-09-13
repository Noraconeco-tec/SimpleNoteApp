package jp.co.noraconeco.simplenoteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteDetailBinding

class NoteDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()
    }

    private val viewModel: NoteDetailViewModel by viewModels()
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding: FragmentNoteDetailBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}