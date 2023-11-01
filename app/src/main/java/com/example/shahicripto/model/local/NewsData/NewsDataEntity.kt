package com.example.shahicripto.model.local.NewsData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsDataEntity(
    @PrimaryKey
    val title: String,
    val url: String
)