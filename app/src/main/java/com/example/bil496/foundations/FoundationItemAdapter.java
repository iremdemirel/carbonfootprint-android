package com.example.bil496.foundations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bil496.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoundationItemAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.foundation_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindView(position);
    }


    @Override
    public int getItemCount() {
        return FoundationData.description.length;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView foundation_desc;
        ImageView foundation_logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foundation_desc = (TextView) itemView.findViewById(R.id.foundation_description);
            foundation_logo = (ImageView) itemView.findViewById(R.id.foundation_logo);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            foundation_desc.setText(FoundationData.description[position]);
            foundation_logo.setImageResource(FoundationData.picturePath[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

