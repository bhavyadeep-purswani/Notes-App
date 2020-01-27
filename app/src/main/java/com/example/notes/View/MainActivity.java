package com.example.notes.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.notes.R;
import com.example.notes.View.controllers.NotesListController;

public class MainActivity extends AppCompatActivity {
    private Router mRouter;
    private NotesListController mNotesListController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRouter = Conductor.attachRouter(this, (ViewGroup) findViewById(R.id.controller_container), savedInstanceState);
        mNotesListController = new NotesListController();
        mNotesListController.setLifecycle(getLifecycle());
        if(!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(mNotesListController));
        }
    }

    @Override
    public void onBackPressed() {
        if(!mRouter.handleBack()) {
            super.onBackPressed();

        }
    }
}
