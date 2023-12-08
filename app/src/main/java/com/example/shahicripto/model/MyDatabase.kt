package com.example.shahicripto.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsDataDao
import com.example.shahicripto.model.local.NewsData.NewsDataEntity


@Database(version =2, exportSchema = false, entities = [CoinsDataEntitity::class , NewsDataEntity::class])
abstract class MyDatabase : RoomDatabase() {


    abstract val coinsDataDao: CoinsDataDao
    abstract val newsDataDao : NewsDataDao

    companion object {

        @Volatile
        private var dataBase: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

            synchronized(this){
                if (dataBase == null){

                    dataBase = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "MyDatabase.db"
                    )
                        .build()
                }

                return dataBase!!
            }
            }



    }


}