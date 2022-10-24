package com.example.currencyconverter.data.network.response

data class SupportedCurrenciesResponse(
    val success: Boolean,
    val currencies: Map<String, String>?
)
