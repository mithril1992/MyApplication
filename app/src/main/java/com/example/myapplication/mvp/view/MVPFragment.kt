package com.example.myapplication.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.mvp.AbstractCurrencyListCellModel
import com.example.myapplication.mvp.AbstractCurrencyListView
import com.example.myapplication.mvp.presenter.Presenter

class MVPFragment : Fragment(), AbstractCurrencyListView {
    private lateinit var loadingView: ProgressBar
    private lateinit var listView: RecyclerView

    private val adapter = CurrencyListAdapter()
    val presenter = Presenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = view.findViewById(R.id.loading_view)
        listView = view.findViewById(R.id.list_view)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed(this)
    }

    override fun showLoadingView() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        loadingView.visibility = View.GONE
    }

    override fun updateListCellModel(cellModels: List<AbstractCurrencyListCellModel>) {
        adapter.updateList(cellModels)
    }
}

class CurrencyListAdapter: RecyclerView.Adapter<CurrencyListCellHolder>() {
    private var list = mutableListOf<AbstractCurrencyListCellModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListCellHolder {
        val itemView = View.inflate(parent.context, R.layout.list_cell, null)
        return CurrencyListCellHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyListCellHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun updateList(list: List<AbstractCurrencyListCellModel>) {
        this@CurrencyListAdapter.list = list.toMutableList()
        notifyDataSetChanged()
    }
}

class CurrencyListCellHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titileTextView = itemView.findViewById<TextView>(R.id.list_cell_title)

    fun bind(cellModel: AbstractCurrencyListCellModel) {
        titileTextView.text = cellModel.text
    }
}