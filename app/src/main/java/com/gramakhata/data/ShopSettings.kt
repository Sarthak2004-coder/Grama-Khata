package com.gramakhata.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class ShopSettings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val shopName: String,
    val ownerName: String,
    val avatarUrl: String? = null,
    val language: String = "en",
    val theme: String = "light"
)
