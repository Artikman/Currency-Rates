package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepositoryImp {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            ApiResponse.Success(apiCall.invoke())
        }
    }
}