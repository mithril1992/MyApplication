package com.example.myapplication.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dao.CurrencyPair
import com.example.myapplication.dao.ZaifDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyListViewModel(application: Application): AndroidViewModel(application) {
    private val dao = ZaifDao()
    val currencyPairs: MutableLiveData<List<CurrencyPair>> = MutableLiveData()
    val loadingViewVisible = MutableLiveData<Boolean>()

    suspend fun loadCurrencyPairs() {
        loadingViewVisible.postValue(true)
        GlobalScope.launch {
            currencyPairs.postValue(dao.requestCurrencies().await())
            loadingViewVisible.postValue(false)
        }
    }
}