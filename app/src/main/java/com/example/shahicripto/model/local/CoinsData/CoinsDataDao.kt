package com.example.shahicripto.model.local.CoinsData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDataDao {

    @Query("SELECT * FROM coinsdataentitity")
    fun getAllCoins() : LiveData<List<CoinsDataEntitity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coinsTop : CoinsDataEntitity)

}