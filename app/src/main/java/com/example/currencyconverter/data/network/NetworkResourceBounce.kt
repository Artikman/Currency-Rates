package com.example.currencyconverter.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.currencyconverter.data.network.response.ApiResponse

abstract class NetworkResourceBounce<T> {

    fun asLiveData() = liveData<DataState<T>> {
        emit(DataState.Loading())
        if (shouldFetch(query())) {
            val disposable = emitSource(queryObservable().map { DataState.Loading(it) })
            val fetchedData = fetch()
            disposable.dispose()
            when (fetchedData) {
                is ApiResponse.Error<*> -> {}
                is ApiResponse.Success -> {
                    saveFetchResult(fetchedData.data)
                    emitSource(queryObservable().map { DataState.Loaded(it) })
                }
            }
        } else {
            emitSource(queryObservable().map { DataState.Loaded(it) })
        }
    }

    abstract suspend fun query(): T

    abstract fun queryObservable(): LiveData<T>

    abstract suspend fun fetch(): ApiResponse<T>

    abstract suspend fun saveFetchResult(data: T)

    abstract fun shouldFetch(data: T?): Boolean
}