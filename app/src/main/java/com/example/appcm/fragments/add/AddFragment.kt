package com.example.appcm.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcm.R
import com.example.appcm.data.Note
import com.example.appcm.data.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.add_btn.setOnClickListener{
            insertDatatoDatabase()
        }
        return view

    }

    private fun insertDatatoDatabase(){

        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
                  if (inputCheck(title, description)){
                            val note = Note(0, title, description,0)
                            mNoteViewModel.addNote(note)
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_addFragment_to_listFragment)
                  }else{
                            Toast.makeText(requireContext(), "Insuccess", Toast.LENGTH_SHORT).show()

                  }
    }

    private fun inputCheck(title: String, description: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description))
    }

}