package com.example.shahicripto.model.local.NewsData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDataDao {

    @Query("SELECT * FROM newsDataEntity")
    fun getAllNews() : LiveData<List<NewsDataEntity>>

    @Query("DELETE FROM newsDataEntity")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsTop : NewsDataEntity)

}
