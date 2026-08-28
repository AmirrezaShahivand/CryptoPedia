package com.example.shahicripto.model.local.NewsData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsTranslationDao {
    @Query("SELECT * FROM NewsTranslationEntity WHERE newsUrl = :url AND language = :language LIMIT 1")
    fun find(url: String, language: String): NewsTranslationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(value: NewsTranslationEntity)
}
