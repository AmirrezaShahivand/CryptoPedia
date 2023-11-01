package com.example.shahicripto.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shahicripto.model.local.CoinsData.CoinsData
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity


@Database(version = 1, exportSchema = false, entities = [CoinsDataEntitity::class])
abstract class MyDatabase : RoomDatabase() {


    abstract val studentDao: CoinsDataDao


    companion object {

        @Volatile
        private var dataBase: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

         //   var instance = dataBase

           // if (instance == null) {

            synchronized(this){
                if (dataBase == null){
//                instance = Room.databaseBuilder(

                    dataBase = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "MyDatabase.db"
                    )
                        .build()
                }

                // return instance
                return dataBase!!
            }
            }



    }


}