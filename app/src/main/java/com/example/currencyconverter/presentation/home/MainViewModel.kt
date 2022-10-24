package com.example.currencyconverter.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.currencyconverter.data.model.Conversion
import com.example.currencyconverter.data.network.DataState
import com.example.currencyconverter.domain.repository.CurrencyConverterRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
) : ViewModel() {

    val supportedCurrencies = currencyConverterRepository.getSupportedCurrencies()

    private val _currencyConversions: MediatorLiveData<DataState<List<Conversion>>> =
        MediatorLiveData()
    val conversions: LiveData<DataState<List<Conversion>>> = _currencyConversions

    fun convert(source: String, amount: Double = 1.00) {
        val conversionsLiveData = currencyConverterRepository.convertCurrency(source, amount)
        _currencyConversions.addSource(conversionsLiveData) {
            _currencyConversions.postValue(it)
        }
    }

}
