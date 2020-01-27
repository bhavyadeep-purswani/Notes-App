package com.example.notes.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.notes.model.DateConverter;
import com.example.notes.model.Note;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public final class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Note`(`note_id`,`mNoteTitle`,`mNoteContent`,`mDateCreated`,`mDateLastModified`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getMNoteId());
        if (value.getMNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMNoteTitle());
        }
        if (value.getMNoteContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMNoteContent());
        }
        final Long _tmp;
        _tmp = DateConverter.fromDate(value.getMDateCreated());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateConverter.fromDate(value.getMDateLastModified());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Note` WHERE `note_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getMNoteId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Note` SET `note_id` = ?,`mNoteTitle` = ?,`mNoteContent` = ?,`mDateCreated` = ?,`mDateLastModified` = ? WHERE `note_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getMNoteId());
        if (value.getMNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMNoteTitle());
        }
        if (value.getMNoteContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMNoteContent());
        }
        final Long _tmp;
        _tmp = DateConverter.fromDate(value.getMDateCreated());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateConverter.fromDate(value.getMDateLastModified());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        stmt.bindLong(6, value.getMNoteId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Note";
        return _query;
      }
    };
  }

  @Override
  public long insertNote(Note note) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfNote.insertAndReturnId(note);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(Note note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateNote(Note note) {
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Note> fetchAllNotes() {
    final String _sql = "SELECT * FROM Note ORDER BY mDateLastModified DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfMNoteTitle = _cursor.getColumnIndexOrThrow("mNoteTitle");
      final int _cursorIndexOfMNoteContent = _cursor.getColumnIndexOrThrow("mNoteContent");
      final int _cursorIndexOfMDateCreated = _cursor.getColumnIndexOrThrow("mDateCreated");
      final int _cursorIndexOfMDateLastModified = _cursor.getColumnIndexOrThrow("mDateLastModified");
      final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Note _item;
        final String _tmpMNoteTitle;
        _tmpMNoteTitle = _cursor.getString(_cursorIndexOfMNoteTitle);
        final String _tmpMNoteContent;
        _tmpMNoteContent = _cursor.getString(_cursorIndexOfMNoteContent);
        final Date _tmpMDateCreated;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMDateCreated)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMDateCreated);
        }
        _tmpMDateCreated = DateConverter.toDate(_tmp);
        final Date _tmpMDateLastModified;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMDateLastModified)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMDateLastModified);
        }
        _tmpMDateLastModified = DateConverter.toDate(_tmp_1);
        _item = new Note(_tmpMNoteTitle,_tmpMNoteContent,_tmpMDateCreated,_tmpMDateLastModified);
        final long _tmpMNoteId;
        _tmpMNoteId = _cursor.getLong(_cursorIndexOfMNoteId);
        _item.setMNoteId(_tmpMNoteId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Note fetchNoteById(long note_id) {
    final String _sql = "SELECT * FROM Note WHERE note_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, note_id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfMNoteTitle = _cursor.getColumnIndexOrThrow("mNoteTitle");
      final int _cursorIndexOfMNoteContent = _cursor.getColumnIndexOrThrow("mNoteContent");
      final int _cursorIndexOfMDateCreated = _cursor.getColumnIndexOrThrow("mDateCreated");
      final int _cursorIndexOfMDateLastModified = _cursor.getColumnIndexOrThrow("mDateLastModified");
      final Note _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMNoteTitle;
        _tmpMNoteTitle = _cursor.getString(_cursorIndexOfMNoteTitle);
        final String _tmpMNoteContent;
        _tmpMNoteContent = _cursor.getString(_cursorIndexOfMNoteContent);
        final Date _tmpMDateCreated;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMDateCreated)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMDateCreated);
        }
        _tmpMDateCreated = DateConverter.toDate(_tmp);
        final Date _tmpMDateLastModified;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMDateLastModified)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMDateLastModified);
        }
        _tmpMDateLastModified = DateConverter.toDate(_tmp_1);
        _result = new Note(_tmpMNoteTitle,_tmpMNoteContent,_tmpMDateCreated,_tmpMDateLastModified);
        final long _tmpMNoteId;
        _tmpMNoteId = _cursor.getLong(_cursorIndexOfMNoteId);
        _result.setMNoteId(_tmpMNoteId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
