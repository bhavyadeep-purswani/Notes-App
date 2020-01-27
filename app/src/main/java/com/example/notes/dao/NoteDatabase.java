package com.example.notes.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.notes.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    private static NoteDatabase mInstance;

    public static NoteDatabase getInstance(final Context context) {
        if(mInstance == null) {
            synchronized (NoteDatabase.class) {
                if(mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "notes_database")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return mInstance;
    }

}
