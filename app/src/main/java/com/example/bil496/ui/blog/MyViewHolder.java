package com.example.bil496.ui.blog;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bil496.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView date, header, description;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.textDate);
        header = (TextView) itemView.findViewById(R.id.textHeader);
        description = (TextView) itemView.findViewById(R.id.textDescription);
    }
}
