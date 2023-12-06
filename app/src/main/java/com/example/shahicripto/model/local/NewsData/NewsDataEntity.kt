package com.example.shahicripto.model.local.NewsData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class NewsDataEntity(
    @PrimaryKey
    val title: String,
    val url: String ,
    val image : String ,
    val body : String
):Parcelable