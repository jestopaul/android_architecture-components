package com.android.jp.samples.androidarchitecturewithmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.jp.samples.androidarchitecturewithmvvm.domain.Note;
import com.android.jp.samples.androidarchitecturewithmvvm.respository.NoteRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insertNotes(Note note) {
        noteRepository.insert(note);
    }

    public void updateNotes(Note note) {
        noteRepository.update(note);
    }

    public void deleteNotes(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
