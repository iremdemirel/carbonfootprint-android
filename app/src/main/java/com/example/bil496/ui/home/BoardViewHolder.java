package com.example.bil496.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bil496.R;

public class BoardViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    public BoardViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
    }
}
