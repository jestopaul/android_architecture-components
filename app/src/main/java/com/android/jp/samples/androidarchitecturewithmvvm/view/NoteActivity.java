package com.android.jp.samples.androidarchitecturewithmvvm.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.jp.samples.androidarchitecturewithmvvm.R;
import com.android.jp.samples.androidarchitecturewithmvvm.Utils.Constants;
import com.android.jp.samples.androidarchitecturewithmvvm.domain.Note;
import com.android.jp.samples.androidarchitecturewithmvvm.ui.adapter.NoteAdapter;
import com.android.jp.samples.androidarchitecturewithmvvm.ui.view.AddUpdateNoteActivity;
import com.android.jp.samples.androidarchitecturewithmvvm.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class NoteActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton fabAddNotes;

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initializeUi();

        setAdapter();
        setListeners();
//        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            //update recyclerview
            adapter.submitList(notes);
        });
        setItemTouchListeners();

    }

    private void initializeUi() {
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        fabAddNotes = findViewById(R.id.fab_add_note);
    }

    private void setAdapter() {
        adapter = new NoteAdapter();
        rvNotes.setAdapter(adapter);
    }

    private void setListeners() {
        fabAddNotes.setOnClickListener(view -> {
            Intent intent = new Intent(NoteActivity.this, AddUpdateNoteActivity.class);
            startActivityForResult(intent, Constants.ADD_NOTE_REQUEST);
        });

        adapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(NoteActivity.this, AddUpdateNoteActivity.class);
            intent.putExtra(Constants.EXTRA_ID, note.getId());
            intent.putExtra(Constants.EXTRA_TITLE, note.getTitle());
            intent.putExtra(Constants.EXTRA_DESCRIPTION, note.getDescription());
            intent.putExtra(Constants.EXTRA_PRIORITY, note.getPriority());
            startActivityForResult(intent, Constants.UPDATE_NOTE_REQUEST);
        });
    }

    private void setItemTouchListeners() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView,
                                  @NonNull @NotNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.deleteNotes(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvNotes);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Constants.EXTRA_TITLE);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(Constants.EXTRA_PRIORITY, Constants.DATA_VALUE_ONE);

            Note note = new Note(title, description, priority);
            noteViewModel.insertNotes(note);
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constants.UPDATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Constants.EXTRA_ID, Constants.DATA_VALUE_MINUS_ONE);
            if (id == Constants.DATA_VALUE_MINUS_ONE) {
                Toast.makeText(NoteActivity.this, "Note can't be update", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(Constants.EXTRA_TITLE);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(Constants.EXTRA_PRIORITY, Constants.DATA_VALUE_ONE);
            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.updateNotes(note);
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes are deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}