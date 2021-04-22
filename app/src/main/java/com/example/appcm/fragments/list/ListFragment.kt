package com.example.appcm.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcm.R
import com.example.appcm.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)


        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(viewLifecycleOwner, Observer {note ->
            adapter.setData(note)
        })


        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        //addmenu

        setHasOptionsMenu(true)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllNotes()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.yes) { _, _ ->

        mNoteViewModel.deleteAllNotes()
        Toast.makeText(requireContext(),
            R.string.rem,
            Toast.LENGTH_SHORT ).show()

        }

        builder.setNegativeButton(R.string.no) { _, _ -> }
        builder.setTitle(R.string.del)
        builder.setMessage(R.string.uSureA)
        builder.create().show()

    }


}