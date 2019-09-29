package com.example.myapplication.mvp

interface AbstractCurrencyListView {
    fun showLoadingView()
    fun hideLoadingView()
    fun updateListCellModel(cellModels: List<AbstractCurrencyListCellModel>)
}

interface AbstractCurrencyListPresenter {
    fun onViewResumed(view: AbstractCurrencyListView)
    fun onViewPaused(view: AbstractCurrencyListView)
}

interface AbstractCurrencyListCellModel {
    val text: String
}