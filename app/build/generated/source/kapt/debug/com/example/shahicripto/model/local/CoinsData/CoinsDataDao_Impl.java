package com.example.shahicripto.model.local.CoinsData;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CoinsDataDao_Impl implements CoinsDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CoinsDataEntitity> __insertionAdapterOfCoinsDataEntitity;

  public CoinsDataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCoinsDataEntitity = new EntityInsertionAdapter<CoinsDataEntitity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CoinsDataEntitity` (`name`,`price`,`change`,`hajm`,`url`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CoinsDataEntitity value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPrice());
        }
        stmt.bindDouble(3, value.getChange());
        stmt.bindDouble(4, value.getHajm());
        if (value.getUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUrl());
        }
      }
    };
  }

  @Override
  public void insertAll(final CoinsDataEntitity coinsTop) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCoinsDataEntitity.insert(coinsTop);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<CoinsDataEntitity>> getAllCoins() {
    final String _sql = "SELECT * FROM coinsdataentitity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"coinsdataentitity"}, false, new Callable<List<CoinsDataEntitity>>() {
      @Override
      public List<CoinsDataEntitity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfChange = CursorUtil.getColumnIndexOrThrow(_cursor, "change");
          final int _cursorIndexOfHajm = CursorUtil.getColumnIndexOrThrow(_cursor, "hajm");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final List<CoinsDataEntitity> _result = new ArrayList<CoinsDataEntitity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CoinsDataEntitity _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final double _tmpChange;
            _tmpChange = _cursor.getDouble(_cursorIndexOfChange);
            final double _tmpHajm;
            _tmpHajm = _cursor.getDouble(_cursorIndexOfHajm);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            _item = new CoinsDataEntitity(_tmpName,_tmpPrice,_tmpChange,_tmpHajm,_tmpUrl);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
