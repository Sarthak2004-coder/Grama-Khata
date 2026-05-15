package com.gramakhata.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerId: Int,
    val amount: Long, // Positive = Give (Credit), Negative = Take (Payment)
    val type: String, // "give" or "take"
    val date: Long,
    val note: String? = null,
    val voiceNoteUrl: String? = null
)
