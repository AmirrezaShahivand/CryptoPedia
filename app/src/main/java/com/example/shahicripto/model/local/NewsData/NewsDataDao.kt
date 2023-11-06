package com.example.shahicripto.model.local.NewsData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import retrofit2.http.GET

@Dao
interface NewsDataDao {

    @Query("SELECT * FROM newsDataEntity")
    fun getAllNews() : LiveData<List<NewsDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsTop : NewsDataEntity)

}