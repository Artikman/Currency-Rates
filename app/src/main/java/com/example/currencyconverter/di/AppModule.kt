package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.currencyconverter.CurrencyConversionApp
import com.example.currencyconverter.data.local.AppDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext app: Context): CurrencyConversionApp {
        return app as CurrencyConversionApp
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: CurrencyConversionApp):AppDatabase{
        return Room.databaseBuilder(app,AppDatabase::class.java, "currency_converter")
            .addMigrations(*AppDatabase.migrations)
            .build()
    }

}