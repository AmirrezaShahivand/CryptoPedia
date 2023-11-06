package com.example.shahicripto.model.local.CoinsData

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CoinsDataEntitity(
    @PrimaryKey
    val name: String,

    val price: String,
    val change: Double,
    val hajm: Double,
    val url: String,
    val oPEN24HOUR: String,
    val hIGH24HOUR: String,
    val lOW24HOUR: String,
    val cHANGE24HOUR: String,
    val algorithm: String,
    val tOTALVOLUME24H: String,
    val mKTCAP: String,
    val sUPPLY: String,
    val fullName: String,
    val cHANGEPCT24HOUR: String ,
    val cHANGE24HOUR_RAW : Double
) : Parcelable
