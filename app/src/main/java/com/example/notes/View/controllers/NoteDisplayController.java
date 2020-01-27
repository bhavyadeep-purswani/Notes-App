package com.example.notes.View.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.notes.R;
import androidx.annotation.NonNull;
import com.bluelinelabs.conductor.Controller;
import com.example.notes.model.Note;

public class NoteDisplayController extends Controller {
    private boolean editNoteFlag = false;
    private Note mNote;
    private EditText mNoteTitle;
    private EditText mNoteContent;
    private NoteDisplayCallback mNoteDisplayCallback;
    public NoteDisplayController(Bundle bundle) {
        super(bundle);

    }
    public NoteDisplayController(NoteDisplayCallback noteDisplayCallback) {
        mNoteDisplayCallback = noteDisplayCallback;
    }
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view =inflater.inflate(R.layout.note_add_edit,container,false);

        mNoteTitle = view.findViewById(R.id.noteTitle);
        mNoteContent = view.findViewById(R.id.noteContent);
        Button mSaveButton = view.findViewById(R.id.saveNote);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mNoteDisplayCallback.onSaveCallback(mNoteTitle.getText().toString(),
                                                        mNoteContent.getText().toString());
                goToNotesList();
            }
        });
        if(editNoteFlag) {
            mNoteTitle.setText(mNote.getMNoteTitle());
            mNoteContent.setText(mNote.getMNoteContent());
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNoteDisplayCallback.onSaveCallback(mNote.getMNoteId(),mNoteTitle.getText().toString(),
                            mNoteContent.getText().toString());
                    goToNotesList();
                }
            });
        }
        return view;
    }
    private void goToNotesList() {
        getRouter().popCurrentController();
    }
    @Override
    public boolean handleBack() {
        if(editNoteFlag) {
            if(mNote.getMNoteTitle().equals(mNoteTitle.getText().toString()) && mNote.getMNoteContent().equals(mNoteContent.getText().toString())) {
                goToNotesList();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                builder.setTitle("Unsaved Changes");
                builder.setMessage("Do you want to save or discard the changes?");
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNoteDisplayCallback.onSaveCallback(mNote.getMNoteId(),
                                mNoteTitle.getText().toString(),
                                mNoteContent.getText().toString());
                        goToNotesList();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToNotesList();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
        else if("".equals(mNoteTitle.getText().toString()) && "".equals(mNoteContent.getText().toString())) {
            goToNotesList();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
            builder.setTitle("Unsaved Changes");
            builder.setMessage("Do you want to save or discard the changes?");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mNoteDisplayCallback.onSaveCallback(mNoteTitle.getText().toString(),
                            mNoteContent.getText().toString());
                    goToNotesList();
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToNotesList();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }

    public void addNoteDetails(Note note) {
        mNote = note;
        editNoteFlag = true;

    }
    public interface NoteDisplayCallback {
        void onSaveCallback(String noteTitle, String noteContent);
        void onSaveCallback(long noteId, String noteTitle, String noteContent);
    }
}
