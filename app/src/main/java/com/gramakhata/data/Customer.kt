package com.gramakhata.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val photoUrl: String? = null,
    val totalDue: Long = 0, // Positive = customer owes shop, Negative = shop owes customer
    val lastTransactionAt: Long,
    val preferredLanguage: String = "en",
    val createdAt: Long = System.currentTimeMillis()
)
