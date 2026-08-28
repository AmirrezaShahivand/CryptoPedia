package com.example.shahicripto.model.local.NewsData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class NewsDataEntity(
    @PrimaryKey
    val title: String,
    val url: String ,
    val image : String ,
    val body : String
) : Serializable
