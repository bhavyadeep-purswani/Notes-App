package com.example.notes.presenter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notes.View.NotesListItemView;
import com.example.notes.R;
import com.example.notes.model.Note;
import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListItemView> {
    private List<Note> mNotes = new ArrayList<>();
    private NotesListItemView.ItemClickEventHandler mEventHandler;
    private static NotesListAdapter notesListAdapter;

    private NotesListAdapter(NotesListItemView.ItemClickEventHandler eventHandler) {
        mEventHandler = eventHandler;

    }
    public static NotesListAdapter getInstance(NotesListItemView.ItemClickEventHandler eventHandler) {
        if(notesListAdapter==null) {
            notesListAdapter = new NotesListAdapter(eventHandler);
        }
        notesListAdapter.mEventHandler = eventHandler;
        return notesListAdapter;
    }
    @NonNull
    @Override
    public NotesListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View notesList = layoutInflater.inflate(R.layout.notes_list_item,parent,false);
        return new NotesListItemView(notesList);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListItemView holder, int position) {
        Note note = mNotes.get(position);
        holder.setItemTitle(note.getMNoteTitle());
        holder.setItemContent(note.getMNoteContent());
        holder.setItemClickListener(note,mEventHandler);
        holder.setItemLongClickListener(note,mEventHandler);

    }
    public void setNotesList(List<Note> notes) {
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
