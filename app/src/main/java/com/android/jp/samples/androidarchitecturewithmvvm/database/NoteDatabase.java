package com.android.jp.samples.androidarchitecturewithmvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.jp.samples.androidarchitecturewithmvvm.dao.NoteDao;
import com.android.jp.samples.androidarchitecturewithmvvm.domain.Note;

import org.jetbrains.annotations.NotNull;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getNoteDatabaseInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDefaultValuesToDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDefaultValuesToDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDefaultValuesToDbAsyncTask(NoteDatabase noteDatabase) {
            noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1", "First title created with priority", 1));
            noteDao.insert(new Note("Title2", "Second title created with priority2", 2));
            noteDao.insert(new Note("Title3", "Third title created with priority 3", 3));
            return null;
        }
    }


}
