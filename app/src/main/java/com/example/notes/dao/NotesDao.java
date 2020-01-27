package com.example.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.model.Note;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    long insertNote(Note note);

    @Query("SELECT * FROM Note ORDER BY mDateLastModified DESC")
    List<Note> fetchAllNotes();

    @Query("SELECT * FROM Note WHERE note_id = :note_id")
    Note fetchNoteById(long note_id);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("DELETE FROM Note")
    void deleteAll();
}
