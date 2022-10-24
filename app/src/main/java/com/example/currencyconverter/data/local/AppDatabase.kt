package com.example.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.currencyconverter.data.local.dao.ConversionDao
import com.example.currencyconverter.data.local.dao.CurrencyDao
import com.example.currencyconverter.data.model.Conversion
import com.example.currencyconverter.data.model.Currency

@Database(
    entities = [Currency::class, Conversion::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun conversionDao(): ConversionDao

    companion object {
        val migrations = arrayOf<Migration>()
    }
}