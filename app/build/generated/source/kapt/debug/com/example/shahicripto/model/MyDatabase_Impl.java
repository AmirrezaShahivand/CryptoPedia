package com.example.shahicripto.model;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao;
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao_Impl;
import com.example.shahicripto.model.local.NewsData.NewsDataDao;
import com.example.shahicripto.model.local.NewsData.NewsDataDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabase_Impl extends MyDatabase {
  private volatile CoinsDataDao _coinsDataDao;

  private volatile NewsDataDao _newsDataDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CoinsDataEntitity` (`name` TEXT NOT NULL, `price` TEXT NOT NULL, `change` REAL NOT NULL, `hajm` REAL NOT NULL, `url` TEXT NOT NULL, `oPEN24HOUR` TEXT NOT NULL, `hIGH24HOUR` TEXT NOT NULL, `lOW24HOUR` TEXT NOT NULL, `cHANGE24HOUR` TEXT NOT NULL, `algorithm` TEXT NOT NULL, `tOTALVOLUME24H` TEXT NOT NULL, `mKTCAP` TEXT NOT NULL, `sUPPLY` TEXT NOT NULL, `fullName` TEXT NOT NULL, `cHANGEPCT24HOUR` TEXT NOT NULL, `cHANGE24HOUR_RAW` REAL NOT NULL, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `NewsDataEntity` (`title` TEXT NOT NULL, `url` TEXT NOT NULL, `image` TEXT NOT NULL, `body` TEXT NOT NULL, PRIMARY KEY(`title`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '363e4994fe187aaf0e958c2e8d6bcd12')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `CoinsDataEntitity`");
        _db.execSQL("DROP TABLE IF EXISTS `NewsDataEntity`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCoinsDataEntitity = new HashMap<String, TableInfo.Column>(16);
        _columnsCoinsDataEntitity.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("price", new TableInfo.Column("price", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("change", new TableInfo.Column("change", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("hajm", new TableInfo.Column("hajm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("oPEN24HOUR", new TableInfo.Column("oPEN24HOUR", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("hIGH24HOUR", new TableInfo.Column("hIGH24HOUR", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("lOW24HOUR", new TableInfo.Column("lOW24HOUR", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("cHANGE24HOUR", new TableInfo.Column("cHANGE24HOUR", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("algorithm", new TableInfo.Column("algorithm", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("tOTALVOLUME24H", new TableInfo.Column("tOTALVOLUME24H", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("mKTCAP", new TableInfo.Column("mKTCAP", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("sUPPLY", new TableInfo.Column("sUPPLY", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("fullName", new TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("cHANGEPCT24HOUR", new TableInfo.Column("cHANGEPCT24HOUR", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinsDataEntitity.put("cHANGE24HOUR_RAW", new TableInfo.Column("cHANGE24HOUR_RAW", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCoinsDataEntitity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCoinsDataEntitity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCoinsDataEntitity = new TableInfo("CoinsDataEntitity", _columnsCoinsDataEntitity, _foreignKeysCoinsDataEntitity, _indicesCoinsDataEntitity);
        final TableInfo _existingCoinsDataEntitity = TableInfo.read(_db, "CoinsDataEntitity");
        if (! _infoCoinsDataEntitity.equals(_existingCoinsDataEntitity)) {
          return new RoomOpenHelper.ValidationResult(false, "CoinsDataEntitity(com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity).\n"
                  + " Expected:\n" + _infoCoinsDataEntitity + "\n"
                  + " Found:\n" + _existingCoinsDataEntitity);
        }
        final HashMap<String, TableInfo.Column> _columnsNewsDataEntity = new HashMap<String, TableInfo.Column>(4);
        _columnsNewsDataEntity.put("title", new TableInfo.Column("title", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsDataEntity.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsDataEntity.put("image", new TableInfo.Column("image", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsDataEntity.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNewsDataEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNewsDataEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNewsDataEntity = new TableInfo("NewsDataEntity", _columnsNewsDataEntity, _foreignKeysNewsDataEntity, _indicesNewsDataEntity);
        final TableInfo _existingNewsDataEntity = TableInfo.read(_db, "NewsDataEntity");
        if (! _infoNewsDataEntity.equals(_existingNewsDataEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "NewsDataEntity(com.example.shahicripto.model.local.NewsData.NewsDataEntity).\n"
                  + " Expected:\n" + _infoNewsDataEntity + "\n"
                  + " Found:\n" + _existingNewsDataEntity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "363e4994fe187aaf0e958c2e8d6bcd12", "545b8c1b6560062a40afc260f8957583");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "CoinsDataEntitity","NewsDataEntity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `CoinsDataEntitity`");
      _db.execSQL("DELETE FROM `NewsDataEntity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CoinsDataDao.class, CoinsDataDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NewsDataDao.class, NewsDataDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public CoinsDataDao getCoinsDataDao() {
    if (_coinsDataDao != null) {
      return _coinsDataDao;
    } else {
      synchronized(this) {
        if(_coinsDataDao == null) {
          _coinsDataDao = new CoinsDataDao_Impl(this);
        }
        return _coinsDataDao;
      }
    }
  }

  @Override
  public NewsDataDao getNewsDataDao() {
    if (_newsDataDao != null) {
      return _newsDataDao;
    } else {
      synchronized(this) {
        if(_newsDataDao == null) {
          _newsDataDao = new NewsDataDao_Impl(this);
        }
        return _newsDataDao;
      }
    }
  }
}
