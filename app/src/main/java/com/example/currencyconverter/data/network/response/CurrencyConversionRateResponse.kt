package com.example.currencyconverter.data.network.response

data class CurrencyConversionRateResponse(
    val success: Boolean,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)
