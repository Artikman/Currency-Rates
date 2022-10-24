package com.example.currencyconverter.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconverter.data.model.Conversion

@Dao
interface ConversionDao {

    @Insert
    suspend fun insert(conversions: List<Conversion>)

    @Query("SELECT * FROM Conversion")
    fun getAll(): LiveData<List<Conversion>>

    @Query("SELECT * FROM Conversion ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLast(): List<Conversion>

    @Query(
        """
        SELECT id,:source as source,currency, amount * (SELECT :amount/amount FROM Conversion WHERE currency = :source LIMIT 1) as amount,createdAt FROM Conversion WHERE currency != :source
        UNION 
        SELECT id,:source as source ,'USD' as currency,:amount/amount as amount,createdAt FROM Conversion WHERE currency = :source 
        """
    )
    fun convertCurrency(source: String, amount: Double): LiveData<List<Conversion>>

    @Query("DELETE FROM Conversion")
    suspend fun deleteAll()
}