package com.example.shahicripto.model.local.NewsData

import androidx.room.Entity

@Entity(primaryKeys = ["newsUrl", "language"])
data class NewsTranslationEntity(
    val newsUrl: String,
    val language: String,
    val translatedTitle: String,
    val translatedBody: String,
    val sourceHash: String = ""
)
