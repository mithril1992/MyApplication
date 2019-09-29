package com.example.myapplication.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dao.CurrencyPair
import com.example.myapplication.databinding.FragmentMvvmBinding
import com.example.myapplication.databinding.MvvmListCellBinding
import com.example.myapplication.mvvm.viewmodel.CurrencyListViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MVVMFragment : Fragment() {
    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var binding: FragmentMvvmBinding
    private val adapter = CurrencyPairListAdapter()

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvvm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        view.findViewById<RecyclerView>(R.id.list_view).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CurrencyListViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.loadingViewVisible.observe(viewLifecycleOwner, Observer {
            binding.loading = if(it) View.VISIBLE else View.GONE
        })

        viewModel.currencyPairs.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        job = GlobalScope.launch {
            viewModel.loadCurrencyPairs()
        }
    }

    override fun onPause() {
        super.onPause()
        val currentJob = job ?: return
        if (currentJob.isActive) {
            currentJob.cancel()
        }
    }
}

class CurrencyPairListCellHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: MvvmListCellBinding = DataBindingUtil.bind(itemView)!!

    fun bind(model: CurrencyPair) {
        binding.currencyPair = model
    }
}

class CurrencyPairListAdapter : RecyclerView.Adapter<CurrencyPairListCellHolder>() {
    private var list = mutableListOf<CurrencyPair>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyPairListCellHolder {
        val itemView = View.inflate(parent.context, R.layout.mvvm_list_cell, null)
        return CurrencyPairListCellHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyPairListCellHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(pairs: List<CurrencyPair>) {
        list = pairs.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}