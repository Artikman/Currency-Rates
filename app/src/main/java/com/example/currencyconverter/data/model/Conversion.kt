package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.example.currencyconverter.data.local.converter.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class Conversion(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("source")
    val source: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("rate")
    val amount: Double,
    @SerializedName("createdAt")
    val createdAt: Date = Date(System.currentTimeMillis())
)
