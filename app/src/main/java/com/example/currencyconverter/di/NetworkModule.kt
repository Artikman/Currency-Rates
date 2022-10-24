package com.example.currencyconverter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.CurrencyConversionApp
import com.example.currencyconverter.data.network.ApiClient
import com.example.currencyconverter.data.network.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.apilayer.com/currency_data/"

    @Singleton
    @Provides
    fun provideNetworkInterceptor(app: CurrencyConversionApp): NetworkInterceptor {
        return NetworkInterceptor(app)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        client: OkHttpClient
    ): ApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiClient::class.java)
    }
}