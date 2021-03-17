package com.example.appcm.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcm.R
import com.example.appcm.data.Note
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<Note>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentItem = noteList[position]
        holder.itemView.txt_title.text = currentItem.title
        holder.itemView.txt_description.text = currentItem.description

    }
    fun setData(note: List<Note>){
        this.noteList = note
        notifyDataSetChanged()
    }
}