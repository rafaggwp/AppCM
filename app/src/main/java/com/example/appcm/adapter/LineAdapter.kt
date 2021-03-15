package com.example.appcm.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appcm.R
import com.example.appcm.dataclasses.Nota



class LineAdapter(val list: ArrayList<Nota>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.activity_recycler_line, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        holder.title.text = currentPlace.title
        holder.description.text = currentPlace.description
        holder.date.text = currentPlace.date.toString()
    }

}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val title = itemView.title
    val description = itemView.description
    var date = itemView.date

}

