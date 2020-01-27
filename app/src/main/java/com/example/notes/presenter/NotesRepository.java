package com.example.notes.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.notes.dao.NoteDatabase;
import com.example.notes.dao.NotesDao;
import com.example.notes.model.Note;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesRepository {
    private NotesDao mNotesDao;
    public NotesRepository(Context context) {
        mNotesDao = NoteDatabase.getInstance(context).notesDao();
    }
    public List<Note> getAllNotes() {
        return mNotesDao.fetchAllNotes();
    }

    public Note getNoteById(long note_id) {
        Note n = mNotesDao.fetchNoteById(note_id);
        return n;
    }

    public long insertNote(final Note note) {
        insertNote in =new insertNote(note, mNotesDao);
        long id=0;
        try {
            id = in.execute().get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        return id;
    }
    public void deleteNote(final Note note) {
        new deleteNote(note, mNotesDao).execute();
    }
    public void deleteAll() {
        new deleteAll(mNotesDao).execute();
    }
    public void updateNode(final Note note) {
        new updateNote(note, mNotesDao).execute();
    }

    private static class insertNote extends AsyncTask<Void,Void,Long> {
        private Note mNote;
        private NotesDao mNotesDao;
        public insertNote(Note note, NotesDao notesDao) {
            Log.d("repo", "insertNote: " + note);
            mNote = note;
            mNotesDao = notesDao;
        }
        @Override
        protected Long doInBackground(Void... voids) {


            return mNotesDao.insertNote(mNote);
        }
    }
    private static class deleteNote extends AsyncTask<Void, Void, Void> {
        private Note mNote;
        private NotesDao mNotesDao;
        public deleteNote(Note note, NotesDao notesDao) {
            mNote = note;
            mNotesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNotesDao.deleteNote(mNote);
            return null;
        }
    }
    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private NotesDao mNotesDao;
        public deleteAll(NotesDao notesDao) {
            mNotesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNotesDao.deleteAll();
            return null;
        }
    }
    private static class updateNote extends AsyncTask<Void, Void, Void> {
        private Note mNote;
        private NotesDao mNotesDao;
        public updateNote(Note note, NotesDao notesDao) {
            mNote = note;
            mNotesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNotesDao.updateNote(mNote);
            return null;
        }
    }
}
