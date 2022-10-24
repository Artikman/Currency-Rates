package com.example.currencyconverter.data.network.response

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error<out T>(val data: T? = null, val message: String? = null) :
        ApiResponse<Nothing>()
}