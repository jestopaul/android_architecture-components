package com.android.jp.samples.androidarchitecturewithmvvm.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jp.samples.androidarchitecturewithmvvm.R;

import org.jetbrains.annotations.NotNull;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public TextView tvDescription;
    public TextView tvPriority;

    public NoteViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDescription = itemView.findViewById(R.id.tv_description);
        tvPriority = itemView.findViewById(R.id.tv_priority);
    }


}
