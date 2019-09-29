package com.example.myapplication.mvp.presenter

import com.example.myapplication.dao.ZaifDao
import com.example.myapplication.mvp.AbstractCurrencyListCellModel
import com.example.myapplication.mvp.AbstractCurrencyListPresenter
import com.example.myapplication.mvp.AbstractCurrencyListView
import com.example.myapplication.mvp.model.CurrencyListCellModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Presenter: AbstractCurrencyListPresenter {
    private val dao = ZaifDao()
    private var job: Job? = null
    override fun onViewResumed(view: AbstractCurrencyListView) {
        view.showLoadingView()

        job = GlobalScope.launch {
            val cellModels = loadCurrency()
            launch(Dispatchers.Main) {
                view.updateListCellModel(cellModels)
                view.hideLoadingView()
            }
        }
    }

    override fun onViewPaused(view: AbstractCurrencyListView) {
        val currentJob = job ?: return
        if (currentJob.isActive) {
            currentJob.cancel()
        }
    }

    private suspend fun loadCurrency() = dao.requestCurrencies().await().map {
        CurrencyListCellModel(it.name)
    }
}