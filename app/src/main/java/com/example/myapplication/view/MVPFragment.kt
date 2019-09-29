package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dao.ZaifDao
import com.example.myapplication.view.component.ListAdapter
import kotlinx.coroutines.*

class MVPFragment : Fragment() {
    private lateinit var loadingView: ProgressBar
    private lateinit var listView: RecyclerView

    private val adapter = ListAdapter()
    val dao = ZaifDao()

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

        showLoadingView()

        GlobalScope.launch {
            val list = dao.requestCurrencies().await()
            launch(Dispatchers.Main) {
                adapter.updateList(list)
                hideLoadingView()
            }
        }
    }

    fun showLoadingView() {
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        loadingView.visibility = View.GONE
    }
}