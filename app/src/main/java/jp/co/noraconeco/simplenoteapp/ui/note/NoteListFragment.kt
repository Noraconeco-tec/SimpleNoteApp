package jp.co.noraconeco.simplenoteapp.ui.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.noraconeco.simplenoteapp.R
import jp.co.noraconeco.simplenoteapp.databinding.FragmentNoteListBinding

@AndroidEntryPoint
class NoteListFragment : Fragment() {

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private val viewModel: NoteListViewModel by viewModels()
    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding
        get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCellData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_note_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_note -> showNewNote()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewNote(): Boolean {
        findNavController().navigate(R.id.show_note_creating)
        return true
    }
}