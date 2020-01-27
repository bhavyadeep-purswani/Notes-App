package com.example.notes.presenter;

import android.util.Log;
import com.example.notes.View.NotesListView;
import com.example.notes.model.Note;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotesPresenter {
    private List<Note> mNotes = new ArrayList<Note>();
    private NotesRepository mNotesRepository;
    private NotesListView mNotesListView;
    private static NotesPresenter instance;
    private NotesPresenter(final NotesListView notesListView) {
        mNotesListView = notesListView;
        mNotesRepository = new NotesRepository(mNotesListView.getAppContext());
        Single<List<Note>> observable = Single.fromCallable(new Callable<List<Note>>() {
            @Override
            public List<Note> call() throws Exception {
                return mNotesRepository.getAllNotes();
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Note>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<Note> notes) {
                            mNotes = notes;
                            mNotesListView.updateAdapter();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

    }
    public static NotesPresenter getInstance(NotesListView notesListView) {
        if(instance==null)
            instance = new NotesPresenter(notesListView);
        return instance;
    }
    public List<Note> getNotesList() {
        return mNotes;
    }

    public void addNote(final String noteTitle, final String noteContent) {
        if("".equals(noteTitle) && "".equals(noteContent)) {
            mNotesListView.displayToast("Nothing to Save. Note discarded!");
            return;
        }
        final Note noteCopy = new Note(noteTitle,noteContent,new Date(), new Date());
        Single<Long> observable = Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mNotesRepository.insertNote(noteCopy);
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        Note note = new Note(noteTitle,noteContent,new Date(), new Date());
                        note.setMNoteId(aLong);
                        mNotes.add(0,note);
                        mNotesListView.displayToast("Note Saved!");
                        mNotesListView.updateAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void addNote(final long noteId, final String noteTitle, final String noteContent) {
        if("".equals(noteTitle) && "".equals(noteContent)) {
            mNotesListView.displayToast("Nothing to Save. Note discarded!");
            return;
        }
        Single<Note> observable = Single.fromCallable(new Callable<Note>() {
            @Override
            public Note call() throws Exception {
                return mNotesRepository.getNoteById(noteId);
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Note>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Note n) {
                        Note note = n;
                        for(Note n1 : mNotes) {
                            if(n1.getMNoteId()==note.getMNoteId()) {
                                mNotes.remove(n1);
                                break;
                            }
                        }
                        note.setmDateLastModified(new Date());
                        note.setmNoteContent(noteContent);
                        note.setmNoteTitle(noteTitle);
                        mNotesRepository.updateNode(note);
                        mNotes.add(0,note);
                        mNotesListView.displayToast("Note Saved!");
                        mNotesListView.updateAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

    }
    public void deleteNote(final Note note) {
        Single<Boolean> observable = Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mNotesRepository.deleteNote(note);
                return true;
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBool) {
                        int index = mNotes.indexOf(note);
                        mNotes.remove(note);
                        mNotesListView.updateAdapter();
                        mNotesListView.displayToast("Note deleted successfully!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }
                });

    }
}

