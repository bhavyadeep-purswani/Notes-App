package com.example.notes.View.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.notes.R;
import com.example.notes.View.NotesListItemView;
import com.example.notes.View.NotesListView;
import com.example.notes.model.Note;
import com.example.notes.presenter.NotesListAdapter;
import com.example.notes.presenter.NotesPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListController extends Controller implements NotesListView, LifecycleOwner, NotesListItemView.ItemClickEventHandler, NoteDisplayController.NoteDisplayCallback {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNoteButton;
    private Lifecycle mLifecycle;
    private Activity parentActivity;
    private NotesPresenter notesPresenter;
    private NotesListAdapter notesListAdapter;
    private Context appContext;
    private Router mRouter;

    public void setLifecycle(Lifecycle lifecycle) {
        mLifecycle = lifecycle;

    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        parentActivity = getActivity();
        mRouter = getRouter();
        View view = inflater.inflate(R.layout.notes_list,container, false);
        appContext = getApplicationContext();
        mAddNoteButton = view.findViewById(R.id.addNote);
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddNote();
            }
        });
        mRecyclerView = view.findViewById(R.id.notesList);
        notesPresenter = NotesPresenter.getInstance(this);
        notesListAdapter = NotesListAdapter.getInstance(this);
        notesListAdapter.setNotesList(notesPresenter.getNotesList());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(notesListAdapter);
        return view;
    }
    @Override
    public void viewNoteDetails(Note note) {
        LayoutInflater layoutInflater = LayoutInflater.from(getView().getContext());
        View noteDetailsView = layoutInflater.inflate(R.layout.note_details_popup,null);
        ((TextView)noteDetailsView.findViewById(R.id.dateCreated)).setText(note.getMDateCreated().toString());
        ((TextView)noteDetailsView.findViewById(R.id.dateLastModified)).setText(note.getMDateLastModified().toString());
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(noteDetailsView, width, height, true);
        popupWindow.showAtLocation(getView(), Gravity.CENTER,0,0);
        noteDetailsView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void deleteNote(Note note) {
        notesPresenter.deleteNote(note);
    }

    private void goToAddNote() {
        mRouter.pushController(RouterTransaction.with(new NoteDisplayController(this)));
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        parentActivity = getActivity();
        updateAdapter();
    }

    @Override
    public void updateAdapter() {
        notesListAdapter.setNotesList(notesPresenter.getNotesList());
        notesListAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getAppContext() {
        return appContext;
    }

    @Override
    public Activity getParentActivity() {
        return parentActivity;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    public void displayNote(Note note) {
        NoteDisplayController noteDisplayController = new NoteDisplayController(this);
        noteDisplayController.addNoteDetails(note);
        mRouter.pushController(RouterTransaction.with(noteDisplayController));
    }

    @Override
    public void onSaveCallback(String noteTitle, String noteContent) {
        notesPresenter.addNote(noteTitle,noteContent);
    }

    @Override
    public void onSaveCallback(long noteId, String noteTitle, String noteContent) {
        notesPresenter.addNote(noteId,noteTitle,noteContent);
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(parentActivity.getApplicationContext(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }
}
