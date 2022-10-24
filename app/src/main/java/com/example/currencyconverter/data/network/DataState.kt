package com.example.currencyconverter.data.network

sealed class DataState<out T> {
    data class Loading<out T>(val data: T? = null) : DataState<T>()
    data class Loaded<out T>(val data: T) : DataState<T>()
    data class Failed<out T>(val data: T? = null, val message: String? = null) : DataState<T>()
}
