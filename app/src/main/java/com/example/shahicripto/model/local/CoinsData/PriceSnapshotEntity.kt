package com.example.shahicripto.model.local.CoinsData

import androidx.room.Entity

@Entity(
    tableName = "price_snapshots",
    primaryKeys = ["coinId", "timestamp"]
)
data class PriceSnapshotEntity(
    val coinId: String,
    val timestamp: Long,
    val priceUsdt: Double,
    val volumeUsdt: Double,
    val marketCapUsdt: Double
)
