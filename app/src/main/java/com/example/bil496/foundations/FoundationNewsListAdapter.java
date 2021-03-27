package com.example.bil496.foundations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bil496.MainActivity;
import com.example.bil496.R;
import com.example.bil496.ui.dashboard.DashboardFragment;
import com.example.bil496.ui.dashboard.NewsFragment;

import java.util.ArrayList;

public class FoundationNewsListAdapter extends BaseAdapter {
    String[] titles;
    String[] contents;
    Context context;
    private static LayoutInflater inflater = null;

    public FoundationNewsListAdapter(LayoutInflater inflater, ArrayList<String> titles, ArrayList<String> contents, Context context){
        this.titles = (String[]) titles.toArray();
        this.contents = (String[]) contents.toArray();
        this.inflater = inflater;
        this.context = context;
        //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public FoundationNewsListAdapter(LayoutInflater layoutInflater, Context context) {
        this.inflater = layoutInflater;
        this.context = context;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final Holder holder = new Holder();
        final View v;
        v = inflater.inflate(R.layout.list_item, null);
        holder.title = (TextView) v.findViewById(R.id.title_foundationNew);
        holder.content = (TextView) v.findViewById(R.id.content_foundationNew);
        holder.title.setText(titles[position]);
        holder.content.setText(contents[position]);
        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((MainActivity)context).changeFrame(holder.title.getText().toString(), holder.content.getText().toString(), inflater);

            }
        });
        return v;
    }

    public class Holder{
        TextView title;
        TextView content;
    }
}
