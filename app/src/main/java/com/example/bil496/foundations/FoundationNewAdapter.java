package com.example.bil496.foundations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bil496.R;

public class FoundationNewAdapter extends BaseAdapter {
    String title;
    String content;
    Context context;

    public FoundationNewAdapter(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public FoundationNewAdapter(String title, String content, Context context) {
        this.title = title;
        this.content = content;
        this.context = context;
    }

    private static LayoutInflater inflater = null;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v;
        v = inflater.inflate(R.layout.list_item, null);
        TextView tw = (TextView) v.findViewById(R.id.title_foundationNew);
        TextView cw = (TextView) v.findViewById(R.id.content_foundationNew);
        tw.setText(this.title);
        cw.setText(this.content);
        return v;
    }
}
