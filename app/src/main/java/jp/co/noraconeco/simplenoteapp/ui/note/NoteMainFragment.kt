package jp.co.noraconeco.simplenoteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteMainBinding

@AndroidEntryPoint
class NoteMainFragment : Fragment() {

    companion object {
        fun newInstance() = NoteMainFragment()
    }

    private var _binding: FragmentNoteMainBinding? = null
    private val binding: FragmentNoteMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}