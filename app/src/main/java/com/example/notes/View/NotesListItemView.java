package com.example.notes.View;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Note;

public class NotesListItemView extends RecyclerView.ViewHolder {
    private TextView mNotesListItemTitle, mNotesListItemContent;
    private LinearLayout mNotesListItem;
    public NotesListItemView(@NonNull View itemView) {
        super(itemView);
        mNotesListItemTitle = (TextView) itemView.findViewById(R.id.noteListItemTitle);
        mNotesListItemContent = (TextView) itemView.findViewById(R.id.noteListItemContent);
        mNotesListItem = (LinearLayout) itemView.findViewById(R.id.noteListItem);
    }
    public void setItemTitle(String title){
        mNotesListItemTitle.setText(title);
    }
    public void setItemContent(String content) {
        mNotesListItemContent.setText(content);
    }
    public void setItemClickListener(final Note note, final ItemClickEventHandler eventHandler) {
        mNotesListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventHandler.displayNote(note);
            }
        });
    }
    public void setItemLongClickListener(final Note note, final ItemClickEventHandler eventHandler) {
        mNotesListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(),v);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                eventHandler.deleteNote(note);
                                break;
                            case R.id.details:
                                eventHandler.viewNoteDetails(note);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
    }


    public interface ItemClickEventHandler {
        void displayNote(Note note);
        void viewNoteDetails(Note note);
        void deleteNote(Note note);
    }
}
