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

    @Query("DELETE FROM coinsdataentitity")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coinsTop : CoinsDataEntitity)

}

@Dao
interface CoinCatalogDao {
    @Query("SELECT COUNT(*) FROM coin_catalog")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coins: List<CoinCatalogEntity>)
}

@Dao
interface PriceSnapshotDao {
    @Query("SELECT MAX(timestamp) FROM price_snapshots WHERE coinId = :coinId")
    fun getLatestTimestamp(coinId: String): Long?

    @Query("SELECT * FROM price_snapshots WHERE coinId = :coinId AND timestamp >= :from ORDER BY timestamp ASC")
    fun getSince(coinId: String, from: Long): List<PriceSnapshotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(snapshot: PriceSnapshotEntity)
}
