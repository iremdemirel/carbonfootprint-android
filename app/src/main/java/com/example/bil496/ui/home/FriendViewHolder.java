package com.example.bil496.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bil496.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    TextView friendName, friendEmail;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        friendName = itemView.findViewById(R.id.friendName);
        friendEmail = itemView.findViewById(R.id.friendEmail);
    }

}
