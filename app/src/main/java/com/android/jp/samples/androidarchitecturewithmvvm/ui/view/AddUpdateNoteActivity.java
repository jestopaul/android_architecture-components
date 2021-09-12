package com.android.jp.samples.androidarchitecturewithmvvm.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.jp.samples.androidarchitecturewithmvvm.R;
import com.android.jp.samples.androidarchitecturewithmvvm.Utils.Constants;

public class AddUpdateNoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_note);
        initializeUi();
    }

    private void initializeUi() {
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        numberPickerPriority = findViewById(R.id.numberPicker);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_ID)) {
            setTitle("Update Note");
            etTitle.setText(intent.getStringExtra(Constants.EXTRA_TITLE));
            etDescription.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(Constants.EXTRA_PRIORITY,
                    Constants.DATA_VALUE_ONE));
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNotes() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this,
                    "Please insert valid title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_TITLE, title);
        intent.putExtra(Constants.EXTRA_DESCRIPTION, description);
        intent.putExtra(Constants.EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(Constants.EXTRA_ID, Constants.DATA_VALUE_MINUS_ONE);
        if (id != Constants.DATA_VALUE_MINUS_ONE) {
            intent.putExtra(Constants.EXTRA_ID, id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}