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
        return "INSERT OR REPLACE INTO `CoinsDataEntitity` (`name`,`price`,`change`,`hajm`,`url`,`oPEN24HOUR`,`hIGH24HOUR`,`lOW24HOUR`,`cHANGE24HOUR`,`algorithm`,`tOTALVOLUME24H`,`mKTCAP`,`sUPPLY`,`fullName`,`cHANGEPCT24HOUR`,`cHANGE24HOUR_RAW`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        if (value.getOPEN24HOUR() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getOPEN24HOUR());
        }
        if (value.getHIGH24HOUR() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getHIGH24HOUR());
        }
        if (value.getLOW24HOUR() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getLOW24HOUR());
        }
        if (value.getCHANGE24HOUR() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCHANGE24HOUR());
        }
        if (value.getAlgorithm() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getAlgorithm());
        }
        if (value.getTOTALVOLUME24H() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getTOTALVOLUME24H());
        }
        if (value.getMKTCAP() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getMKTCAP());
        }
        if (value.getSUPPLY() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getSUPPLY());
        }
        if (value.getFullName() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getFullName());
        }
        if (value.getCHANGEPCT24HOUR() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getCHANGEPCT24HOUR());
        }
        stmt.bindDouble(16, value.getCHANGE24HOUR_RAW());
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
          final int _cursorIndexOfOPEN24HOUR = CursorUtil.getColumnIndexOrThrow(_cursor, "oPEN24HOUR");
          final int _cursorIndexOfHIGH24HOUR = CursorUtil.getColumnIndexOrThrow(_cursor, "hIGH24HOUR");
          final int _cursorIndexOfLOW24HOUR = CursorUtil.getColumnIndexOrThrow(_cursor, "lOW24HOUR");
          final int _cursorIndexOfCHANGE24HOUR = CursorUtil.getColumnIndexOrThrow(_cursor, "cHANGE24HOUR");
          final int _cursorIndexOfAlgorithm = CursorUtil.getColumnIndexOrThrow(_cursor, "algorithm");
          final int _cursorIndexOfTOTALVOLUME24H = CursorUtil.getColumnIndexOrThrow(_cursor, "tOTALVOLUME24H");
          final int _cursorIndexOfMKTCAP = CursorUtil.getColumnIndexOrThrow(_cursor, "mKTCAP");
          final int _cursorIndexOfSUPPLY = CursorUtil.getColumnIndexOrThrow(_cursor, "sUPPLY");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfCHANGEPCT24HOUR = CursorUtil.getColumnIndexOrThrow(_cursor, "cHANGEPCT24HOUR");
          final int _cursorIndexOfCHANGE24HOURRAW = CursorUtil.getColumnIndexOrThrow(_cursor, "cHANGE24HOUR_RAW");
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
            final String _tmpOPEN24HOUR;
            if (_cursor.isNull(_cursorIndexOfOPEN24HOUR)) {
              _tmpOPEN24HOUR = null;
            } else {
              _tmpOPEN24HOUR = _cursor.getString(_cursorIndexOfOPEN24HOUR);
            }
            final String _tmpHIGH24HOUR;
            if (_cursor.isNull(_cursorIndexOfHIGH24HOUR)) {
              _tmpHIGH24HOUR = null;
            } else {
              _tmpHIGH24HOUR = _cursor.getString(_cursorIndexOfHIGH24HOUR);
            }
            final String _tmpLOW24HOUR;
            if (_cursor.isNull(_cursorIndexOfLOW24HOUR)) {
              _tmpLOW24HOUR = null;
            } else {
              _tmpLOW24HOUR = _cursor.getString(_cursorIndexOfLOW24HOUR);
            }
            final String _tmpCHANGE24HOUR;
            if (_cursor.isNull(_cursorIndexOfCHANGE24HOUR)) {
              _tmpCHANGE24HOUR = null;
            } else {
              _tmpCHANGE24HOUR = _cursor.getString(_cursorIndexOfCHANGE24HOUR);
            }
            final String _tmpAlgorithm;
            if (_cursor.isNull(_cursorIndexOfAlgorithm)) {
              _tmpAlgorithm = null;
            } else {
              _tmpAlgorithm = _cursor.getString(_cursorIndexOfAlgorithm);
            }
            final String _tmpTOTALVOLUME24H;
            if (_cursor.isNull(_cursorIndexOfTOTALVOLUME24H)) {
              _tmpTOTALVOLUME24H = null;
            } else {
              _tmpTOTALVOLUME24H = _cursor.getString(_cursorIndexOfTOTALVOLUME24H);
            }
            final String _tmpMKTCAP;
            if (_cursor.isNull(_cursorIndexOfMKTCAP)) {
              _tmpMKTCAP = null;
            } else {
              _tmpMKTCAP = _cursor.getString(_cursorIndexOfMKTCAP);
            }
            final String _tmpSUPPLY;
            if (_cursor.isNull(_cursorIndexOfSUPPLY)) {
              _tmpSUPPLY = null;
            } else {
              _tmpSUPPLY = _cursor.getString(_cursorIndexOfSUPPLY);
            }
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpCHANGEPCT24HOUR;
            if (_cursor.isNull(_cursorIndexOfCHANGEPCT24HOUR)) {
              _tmpCHANGEPCT24HOUR = null;
            } else {
              _tmpCHANGEPCT24HOUR = _cursor.getString(_cursorIndexOfCHANGEPCT24HOUR);
            }
            final double _tmpCHANGE24HOUR_RAW;
            _tmpCHANGE24HOUR_RAW = _cursor.getDouble(_cursorIndexOfCHANGE24HOURRAW);
            _item = new CoinsDataEntitity(_tmpName,_tmpPrice,_tmpChange,_tmpHajm,_tmpUrl,_tmpOPEN24HOUR,_tmpHIGH24HOUR,_tmpLOW24HOUR,_tmpCHANGE24HOUR,_tmpAlgorithm,_tmpTOTALVOLUME24H,_tmpMKTCAP,_tmpSUPPLY,_tmpFullName,_tmpCHANGEPCT24HOUR,_tmpCHANGE24HOUR_RAW);
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
