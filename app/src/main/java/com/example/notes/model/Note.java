package com.example.notes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;


@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private long mNoteId;

    private String mNoteTitle;

    private String mNoteContent;

    @TypeConverters(DateConverter.class)
    private Date mDateCreated;

    @TypeConverters(DateConverter.class)
    private Date mDateLastModified;

    public Note(String mNoteTitle, String mNoteContent, Date mDateCreated, Date mDateLastModified) {
        this.mNoteTitle = mNoteTitle;
        this.mNoteContent = mNoteContent;
        this.mDateCreated = mDateCreated;
        this.mDateLastModified = mDateLastModified;
    }
    public void setMNoteId(long mNoteId) {
        this.mNoteId = mNoteId;
    }
    public void setmNoteTitle(String mNoteTitle) {
        this.mNoteTitle = mNoteTitle;
    }
    public void setmNoteContent(String mNoteContent) {
        this.mNoteContent = mNoteContent;
    }

    public void setmDateCreated(Date mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public void setmDateLastModified(Date mDateLastModified) {
        this.mDateLastModified = mDateLastModified;
    }

    public long getMNoteId() {
        return mNoteId;
    }

    public String getMNoteTitle() {
        return mNoteTitle;
    }

    public String getMNoteContent() {
        return mNoteContent;
    }

    public Date getMDateCreated() {
        return mDateCreated;
    }

    public Date getMDateLastModified() {
        return mDateLastModified;
    }

    @NonNull
    @Override
    public String toString() {
        return mNoteId + "  " + mNoteTitle + "   " + mNoteContent + "   " + mDateCreated + "   " + mDateLastModified ;
    }
}
