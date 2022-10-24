package com.example.currencyconverter.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconverter.data.model.Currency

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insert(currency: Currency)

    @Insert
    suspend fun insert(currencies: List<Currency>)

    @Query("SELECT * FROM Currency")
    fun getAll(): LiveData<List<Currency>>

    @Query("DELETE FROM Currency")
    suspend fun deleteAll()

    @Query("SELECT * FROM Currency ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLast(): List<Currency>

}