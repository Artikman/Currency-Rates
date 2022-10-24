package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.response.CurrencyConversionRateResponse
import com.example.currencyconverter.data.network.response.SupportedCurrenciesResponse
import retrofit2.http.GET

interface ApiClient {

    @GET("list")
    suspend fun getSupportedCurrencies(): SupportedCurrenciesResponse

    @GET("live")
    suspend fun getConversionRate(): CurrencyConversionRateResponse

}