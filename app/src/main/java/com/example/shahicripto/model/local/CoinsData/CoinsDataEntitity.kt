package com.example.shahicripto.model.local.CoinsData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinsDataEntitity(
    @PrimaryKey
    val name: String,
    val price: String,
    val change: Double,
    val hajm: Double,
    val url: String

)