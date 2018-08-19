package com.example.douglasoliveira.myapplication.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.douglasoliveira.myapplication.MainActivity
import com.example.douglasoliveira.myapplication.R
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(private val context: MainActivity,
                private val list: List<String>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.contentDescription.text = list[p1]
        p0.contentDescription.setOnClickListener {
            Toast.makeText(context, "Deu certo!", Toast.LENGTH_SHORT).show()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentDescription = itemView.itemDescription
    }

}
