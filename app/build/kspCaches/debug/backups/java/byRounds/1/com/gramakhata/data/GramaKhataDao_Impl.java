package com.gramakhata.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GramaKhataDao_Impl implements GramaKhataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Customer> __insertionAdapterOfCustomer;

  private final EntityInsertionAdapter<Transaction> __insertionAdapterOfTransaction;

  private final EntityInsertionAdapter<ShopSettings> __insertionAdapterOfShopSettings;

  private final EntityDeletionOrUpdateAdapter<Customer> __updateAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<ShopSettings> __updateAdapterOfShopSettings;

  private final SharedSQLiteStatement __preparedStmtOfClearAllCustomers;

  private final SharedSQLiteStatement __preparedStmtOfClearAllTransactions;

  public GramaKhataDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomer = new EntityInsertionAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `customers` (`id`,`name`,`phone`,`photoUrl`,`totalDue`,`lastTransactionAt`,`preferredLanguage`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        if (entity.getPhotoUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUrl());
        }
        statement.bindLong(5, entity.getTotalDue());
        statement.bindLong(6, entity.getLastTransactionAt());
        statement.bindString(7, entity.getPreferredLanguage());
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfTransaction = new EntityInsertionAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`customerId`,`amount`,`type`,`date`,`note`,`voiceNoteUrl`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindLong(3, entity.getAmount());
        statement.bindString(4, entity.getType());
        statement.bindLong(5, entity.getDate());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
        if (entity.getVoiceNoteUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getVoiceNoteUrl());
        }
      }
    };
    this.__insertionAdapterOfShopSettings = new EntityInsertionAdapter<ShopSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `settings` (`id`,`shopName`,`ownerName`,`avatarUrl`,`language`,`theme`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShopSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getShopName());
        statement.bindString(3, entity.getOwnerName());
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAvatarUrl());
        }
        statement.bindString(5, entity.getLanguage());
        statement.bindString(6, entity.getTheme());
      }
    };
    this.__updateAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `customers` SET `id` = ?,`name` = ?,`phone` = ?,`photoUrl` = ?,`totalDue` = ?,`lastTransactionAt` = ?,`preferredLanguage` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        if (entity.getPhotoUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUrl());
        }
        statement.bindLong(5, entity.getTotalDue());
        statement.bindLong(6, entity.getLastTransactionAt());
        statement.bindString(7, entity.getPreferredLanguage());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__updateAdapterOfShopSettings = new EntityDeletionOrUpdateAdapter<ShopSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `settings` SET `id` = ?,`shopName` = ?,`ownerName` = ?,`avatarUrl` = ?,`language` = ?,`theme` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShopSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getShopName());
        statement.bindString(3, entity.getOwnerName());
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAvatarUrl());
        }
        statement.bindString(5, entity.getLanguage());
        statement.bindString(6, entity.getTheme());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfClearAllCustomers = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM customers";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllTransactions = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transactions";
        return _query;
      }
    };
  }

  @Override
  public Object insertCustomer(final Customer customer,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCustomer.insertAndReturnId(customer);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTransaction(final Transaction transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransaction.insert(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSettings(final ShopSettings settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfShopSettings.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCustomer(final Customer customer,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSettings(final ShopSettings settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfShopSettings.handle(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addTransactionAndUpdateCustomer(final Transaction transaction,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> GramaKhataDao.DefaultImpls.addTransactionAndUpdateCustomer(GramaKhataDao_Impl.this, transaction, __cont), $completion);
  }

  @Override
  public Object clearAllCustomers(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllCustomers.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAllCustomers.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllTransactions(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllTransactions.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAllTransactions.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getCustomerByName(final String name,
      final Continuation<? super Customer> $completion) {
    final String _sql = "SELECT * FROM customers WHERE name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, name);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Customer>() {
      @Override
      @Nullable
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfTotalDue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDue");
          final int _cursorIndexOfLastTransactionAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastTransactionAt");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Customer _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final long _tmpTotalDue;
            _tmpTotalDue = _cursor.getLong(_cursorIndexOfTotalDue);
            final long _tmpLastTransactionAt;
            _tmpLastTransactionAt = _cursor.getLong(_cursorIndexOfLastTransactionAt);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Customer(_tmpId,_tmpName,_tmpPhone,_tmpPhotoUrl,_tmpTotalDue,_tmpLastTransactionAt,_tmpPreferredLanguage,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Customer>> getAllCustomers() {
    final String _sql = "SELECT * FROM customers ORDER BY totalDue DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<List<Customer>>() {
      @Override
      @NonNull
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfTotalDue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDue");
          final int _cursorIndexOfLastTransactionAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastTransactionAt");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final long _tmpTotalDue;
            _tmpTotalDue = _cursor.getLong(_cursorIndexOfTotalDue);
            final long _tmpLastTransactionAt;
            _tmpLastTransactionAt = _cursor.getLong(_cursorIndexOfLastTransactionAt);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Customer(_tmpId,_tmpName,_tmpPhone,_tmpPhotoUrl,_tmpTotalDue,_tmpLastTransactionAt,_tmpPreferredLanguage,_tmpCreatedAt);
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

  @Override
  public Flow<List<Customer>> searchCustomers(final String search) {
    final String _sql = "SELECT * FROM customers WHERE name LIKE ? || '%' ORDER BY totalDue DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, search);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<List<Customer>>() {
      @Override
      @NonNull
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfTotalDue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDue");
          final int _cursorIndexOfLastTransactionAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastTransactionAt");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final long _tmpTotalDue;
            _tmpTotalDue = _cursor.getLong(_cursorIndexOfTotalDue);
            final long _tmpLastTransactionAt;
            _tmpLastTransactionAt = _cursor.getLong(_cursorIndexOfLastTransactionAt);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Customer(_tmpId,_tmpName,_tmpPhone,_tmpPhotoUrl,_tmpTotalDue,_tmpLastTransactionAt,_tmpPreferredLanguage,_tmpCreatedAt);
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

  @Override
  public Object getCustomerById(final int id, final Continuation<? super Customer> $completion) {
    final String _sql = "SELECT * FROM customers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Customer>() {
      @Override
      @Nullable
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfTotalDue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDue");
          final int _cursorIndexOfLastTransactionAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastTransactionAt");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Customer _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final long _tmpTotalDue;
            _tmpTotalDue = _cursor.getLong(_cursorIndexOfTotalDue);
            final long _tmpLastTransactionAt;
            _tmpLastTransactionAt = _cursor.getLong(_cursorIndexOfLastTransactionAt);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Customer(_tmpId,_tmpName,_tmpPhone,_tmpPhotoUrl,_tmpTotalDue,_tmpLastTransactionAt,_tmpPreferredLanguage,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Transaction>> getTransactionsForCustomer(final int customerId) {
    final String _sql = "SELECT * FROM transactions WHERE customerId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfVoiceNoteUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNoteUrl");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpVoiceNoteUrl;
            if (_cursor.isNull(_cursorIndexOfVoiceNoteUrl)) {
              _tmpVoiceNoteUrl = null;
            } else {
              _tmpVoiceNoteUrl = _cursor.getString(_cursorIndexOfVoiceNoteUrl);
            }
            _item = new Transaction(_tmpId,_tmpCustomerId,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpVoiceNoteUrl);
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

  @Override
  public Flow<List<Transaction>> getAllTransactions() {
    final String _sql = "SELECT * FROM transactions ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfVoiceNoteUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNoteUrl");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpVoiceNoteUrl;
            if (_cursor.isNull(_cursorIndexOfVoiceNoteUrl)) {
              _tmpVoiceNoteUrl = null;
            } else {
              _tmpVoiceNoteUrl = _cursor.getString(_cursorIndexOfVoiceNoteUrl);
            }
            _item = new Transaction(_tmpId,_tmpCustomerId,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpVoiceNoteUrl);
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

  @Override
  public Flow<ShopSettings> getSettings() {
    final String _sql = "SELECT * FROM settings LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"settings"}, new Callable<ShopSettings>() {
      @Override
      @Nullable
      public ShopSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfOwnerName = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerName");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "theme");
          final ShopSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final String _tmpOwnerName;
            _tmpOwnerName = _cursor.getString(_cursorIndexOfOwnerName);
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            final String _tmpLanguage;
            _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            final String _tmpTheme;
            _tmpTheme = _cursor.getString(_cursorIndexOfTheme);
            _result = new ShopSettings(_tmpId,_tmpShopName,_tmpOwnerName,_tmpAvatarUrl,_tmpLanguage,_tmpTheme);
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
