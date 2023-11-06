package com.example.shahicripto.model;

import java.lang.System;

@androidx.room.Database(version = 1, exportSchema = false, entities = {com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity.class, com.example.shahicripto.model.local.NewsData.NewsDataEntity.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\f"}, d2 = {"Lcom/example/shahicripto/model/MyDatabase;", "Landroidx/room/RoomDatabase;", "()V", "coinsDataDao", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataDao;", "getCoinsDataDao", "()Lcom/example/shahicripto/model/local/CoinsData/CoinsDataDao;", "newsDataDao", "Lcom/example/shahicripto/model/local/NewsData/NewsDataDao;", "getNewsDataDao", "()Lcom/example/shahicripto/model/local/NewsData/NewsDataDao;", "Companion", "app_debug"})
public abstract class MyDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull
    public static final com.example.shahicripto.model.MyDatabase.Companion Companion = null;
    @kotlin.jvm.Volatile
    private static volatile com.example.shahicripto.model.MyDatabase dataBase;
    
    public MyDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.shahicripto.model.local.CoinsData.CoinsDataDao getCoinsDataDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.shahicripto.model.local.NewsData.NewsDataDao getNewsDataDao();
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/shahicripto/model/MyDatabase$Companion;", "", "()V", "dataBase", "Lcom/example/shahicripto/model/MyDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.shahicripto.model.MyDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}