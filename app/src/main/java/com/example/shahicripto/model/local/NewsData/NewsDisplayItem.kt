package com.example.shahicripto.model.local.NewsData

data class NewsDisplayItem(
    val original: NewsDataEntity,
    val title: String,
    val body: String,
    val source: String,
    val isTranslating: Boolean = false
)
