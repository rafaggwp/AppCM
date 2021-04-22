package com.example.appcm.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appcm.R
import com.example.appcm.model.Note
import com.example.appcm.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.updateTitle.setText(args.currentNote.title)
        view.updateDescription.setText(args.currentNote.description)

        view.update_btn.setOnClickListener {
            updateNote()
        }
        //add menu
        setHasOptionsMenu(true)



        return view
    }

    private fun updateNote() {
        val title = updateTitle.text.toString()
        val description = updateDescription.text.toString()

        if (inputCheck(title, description)) {
            val updatedNote = Note(args.currentNote.id, title, description)
            mNoteViewModel.updateNote(updatedNote)

            Toast.makeText(requireContext(), R.string.updateS, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)

        } else {
            Toast.makeText(requireContext(), R.string.updateW, Toast.LENGTH_SHORT)
                .show()

        }

    }

    private fun inputCheck(title: String, description: String): Boolean {
        return (!(TextUtils.isEmpty(title)) && !(TextUtils.isEmpty(description)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.yes) { _, _ ->

            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(requireContext(),
                R.string.rem,
                Toast.LENGTH_SHORT ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
        }


        builder.setNegativeButton(R.string.no) { _, _ -> }
        builder.setTitle(R.string.del)
        builder.setMessage(R.string.uSure)
        builder.create().show()

        }
    }
