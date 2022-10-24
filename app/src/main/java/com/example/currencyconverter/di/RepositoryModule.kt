package com.example.currencyconverter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.currencyconverter.data.local.AppDatabase
import com.example.currencyconverter.data.network.ApiClient
import com.example.currencyconverter.domain.repository.CurrencyConverterRepository
import com.example.currencyconverter.domain.repository.CurrencyConverterRepositoryImp
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyConverterRepository(
        apiClient: ApiClient,
        database: AppDatabase
    ): CurrencyConverterRepository {
        return CurrencyConverterRepositoryImp(apiClient, database)
    }
}