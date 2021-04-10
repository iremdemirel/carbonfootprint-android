package com.example.bil496.foundations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bil496.MainActivity;
import com.example.bil496.R;
import com.example.bil496.ui.dashboard.DashboardFragment;
import com.example.bil496.ui.dashboard.NewsFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class FoundationNewsListAdapter extends BaseAdapter implements Filterable{
    String[] titles;
    String[] contents;
    String[] displayedTitles;
    String[] displayedContents;
    Context context;
    private static LayoutInflater inflater = null;

    public String[] getDisplayedTitles() {
        return displayedTitles;
    }

    public void setDisplayedTitles(String[] displayedTitles) {
        this.displayedTitles = displayedTitles;
    }

    public String[] getDisplayedContents() {
        return displayedContents;
    }

    public void setDisplayedContents(String[] displayedContents) {
        this.displayedContents = displayedContents;
    }

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
        holder.title.setText(displayedTitles[position]);
        holder.content.setText(displayedContents[position]);
        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((MainActivity)context).changeFrame(holder.title.getText().toString(), holder.content.getText().toString(), inflater);

            }
        });
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                HashMap<String, String> filteredNews = new HashMap<String, String>();

                if(charSequence == null || charSequence.length() == 0){
                    results.count = titles.length;
                    for(int a = 0; a< titles.length ; a++){
                        filteredNews.put(titles[a], contents[a]);
                    }
                    results.values = filteredNews;

                }

                else{
                    charSequence = charSequence.toString().toLowerCase();
                    for(int a = 0; a<titles.length; a++){
                        String title = titles[a];
                        String content = contents[a];
                        if(title.toLowerCase().contains(charSequence.toString().toLowerCase()) || content.toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredNews.put(title, content);
                        }

                    }
                    results.values = filteredNews;
                    results.count = filteredNews.size();

                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                HashMap<String, String> filteredNews = (HashMap<String, String>) filterResults.values;
                if(displayedTitles == null){
                    displayedTitles = new String[filteredNews.size()];
                }
                if(displayedContents == null){
                    displayedContents = new String[filteredNews.size()];
                }
                int i = 0;
                for(String title: filteredNews.keySet()){
                    displayedTitles[i] = title;
                    displayedContents[i] = filteredNews.get(title);
                    i++;
                }
                notifyDataSetChanged();
            }

        };
    return filter;
    }

    public class Holder{
        TextView title;
        TextView content;
    }
}
