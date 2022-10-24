package com.example.currencyconverter.domain.repository

import androidx.lifecycle.LiveData
import com.example.currencyconverter.data.model.Conversion
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.network.DataState

interface CurrencyConverterRepository {

    fun getSupportedCurrencies(): LiveData<DataState<List<Currency>>>

    fun convertCurrency(source: String, amount:Double): LiveData<DataState<List<Conversion>>>

}