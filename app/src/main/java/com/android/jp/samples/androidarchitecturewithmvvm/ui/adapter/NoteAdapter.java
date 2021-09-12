package com.android.jp.samples.androidarchitecturewithmvvm.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jp.samples.androidarchitecturewithmvvm.R;
import com.android.jp.samples.androidarchitecturewithmvvm.domain.Note;
import com.android.jp.samples.androidarchitecturewithmvvm.ui.listeners.IOnItemClickListener;
import com.android.jp.samples.androidarchitecturewithmvvm.ui.viewholder.NoteViewHolder;

import org.jetbrains.annotations.NotNull;

public class NoteAdapter extends ListAdapter<Note,NoteViewHolder> {

    //private List<Note> allNotes = new ArrayList<>();
    private IOnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Note oldItem, @NonNull @NotNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Note oldItem, @NonNull @NotNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription()
                    .equals(newItem.getDescription()) && oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        Note note = getItem(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDescription.setText(note.getDescription());
        holder.tvPriority.setText(String.valueOf(note.getPriority()));
        holder.itemView.setOnClickListener(view -> {
            int position1 = holder.getAdapterPosition();
            if (listener != null && position1 != RecyclerView.NO_POSITION) {
                listener.onItemClick(getItem(position1));
            }


        });

    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;

    }

}
