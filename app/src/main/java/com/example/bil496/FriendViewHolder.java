package com.example.bil496;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    TextView friendName, friendEmail;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        friendName = itemView.findViewById(R.id.friendName);
        friendEmail = itemView.findViewById(R.id.friendEmail);
    }

}
