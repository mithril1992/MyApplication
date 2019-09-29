package com.example.myapplication.view.component

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// NOTE: 多分ListCellModelはインターフェースであるべき
interface ListCellModel {
    val text: String
}

class ListAdapter: RecyclerView.Adapter<ListCellHolder>() {
    private var list = mutableListOf<ListCellModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCellHolder {
        val itemView = View.inflate(parent.context, R.layout.list_cell, null)
        return ListCellHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListCellHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun updateList(list: List<ListCellModel>) {
        this@ListAdapter.list = list.toMutableList()
        notifyDataSetChanged()
    }
}

// NOTE: 多分Viewではなくて、ListCellViewインターフェースで受けるべき
class ListCellHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titileTextView = itemView.findViewById<TextView>(R.id.list_cell_title)

    fun bind(cellModel: ListCellModel) {
        titileTextView.text = cellModel.text
    }
}