package com.example.shahicripto.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.CoinsData.CoinCatalogDao
import com.example.shahicripto.model.local.CoinsData.PriceSnapshotDao
import com.example.shahicripto.model.local.CoinsData.CoinCatalogEntity
import com.example.shahicripto.model.local.CoinsData.PriceSnapshotEntity
import com.example.shahicripto.model.local.NewsData.NewsDataDao
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.model.local.NewsData.NewsTranslationDao
import com.example.shahicripto.model.local.NewsData.NewsTranslationEntity


@Database(
    version = 5,
    exportSchema = false,
    entities = [
        CoinsDataEntitity::class,
        NewsDataEntity::class,
        CoinCatalogEntity::class,
        PriceSnapshotEntity::class,
        NewsTranslationEntity::class
    ]
)
abstract class MyDatabase : RoomDatabase() {


    abstract val coinsDataDao: CoinsDataDao
    abstract val coinCatalogDao: CoinCatalogDao
    abstract val priceSnapshotDao: PriceSnapshotDao
    abstract val newsDataDao : NewsDataDao
    abstract val newsTranslationDao: NewsTranslationDao

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
                        // Cached market data can safely be recreated when upgrading from
                        // an older schema that has no explicit migration.
                        .addMigrations(MIGRATION_3_4, MIGRATION_4_5)
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return dataBase!!
            }
        }

        private val MIGRATION_3_4 = object : androidx.room.migration.Migration(3, 4) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS NewsTranslationEntity (newsUrl TEXT NOT NULL, language TEXT NOT NULL, translatedTitle TEXT NOT NULL, translatedBody TEXT NOT NULL, PRIMARY KEY(newsUrl, language))"
                )
            }
        }

        private val MIGRATION_4_5 = object : androidx.room.migration.Migration(4, 5) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE NewsTranslationEntity ADD COLUMN sourceHash TEXT NOT NULL DEFAULT ''"
                )
            }
        }
    }


}
