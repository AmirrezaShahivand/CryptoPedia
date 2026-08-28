package com.example.shahicripto.model.local.CoinsData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_catalog")
data class CoinCatalogEntity(
    @PrimaryKey val coinId: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val type: String
)
