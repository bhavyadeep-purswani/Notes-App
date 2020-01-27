package com.example.notes.View;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

public interface NotesListView {
    void updateAdapter();
    Context getAppContext();
    Activity getParentActivity();
    LifecycleOwner getLifecycleOwner();
    void displayToast(String message);
}
