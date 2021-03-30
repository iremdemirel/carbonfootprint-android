package com.example.bil496.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bil496.MainActivity;
import com.example.bil496.R;
import com.example.bil496.foundations.Foundation;
import com.example.bil496.foundations.FoundationNews;
import com.example.bil496.foundations.FoundationNewsListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.bil496.R.*;
import static com.example.bil496.R.layout.*;

public class DashboardFragment extends Fragment implements SearchView.OnQueryTextListener{
    ///final ArrayList<String> newsTitle = new ArrayList<>();
    //final ArrayList<String> newsContent = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    Context cont;
    private ListView listView;
    private EditText etSearch;
    private View root;
    private FoundationNewsListAdapter listAdapter;
    private SearchView searchView;
    public DashboardFragment() {
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        searchView.setOnQueryTextListener(this);
        /*etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                listAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

        });
*/
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cont = getContext();
        root = inflater.inflate(fragment_dashboard, container, false);
        listView = root.findViewById(id.lv_foundationNews);
        searchView = root.findViewById(id.search_view);
        //etSearch = (EditText) root.findViewById(R.id.etSearch);
        listAdapter = new FoundationNewsListAdapter(inflater, getActivity());

        readData(new Callback(){
            @Override
            public void onCallback(String title, String content) {

            }
        });
        return root;

    }

    public void readData(final Callback callback){
        firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference dbRefQuoteRequestList = firebaseDatabase.getReference("Foundations").child("Green Peace")
        //        .child("bulletin");
        DatabaseReference dbRefQuoteRequestList = firebaseDatabase.getReference();
        dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                ArrayList<String> newsTitle = new ArrayList<>();
                ArrayList<String> newsContent = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.child("Foundations").getChildren()) {

                    for(DataSnapshot postSnapshot2: postSnapshot.child("bulletin").getChildren()){

                        String content = postSnapshot2.child("content").getValue(String.class);
                        String title = postSnapshot2.child("title").getValue(String.class);
                        newsTitle.add(title);
                        newsContent.add(content);
                    }
                }
                for (DataSnapshot postSnapshot : dataSnapshot.child("Users").getChildren()) {

                    for(DataSnapshot postSnapshot2: postSnapshot.child("blog").getChildren()){

                        String content = postSnapshot2.child("blogtext").child("text").getValue(String.class);
                        String title = postSnapshot2.child("blogtext").child("header").getValue(String.class);
                        newsTitle.add(title);
                        newsContent.add(content);
                    }
                }
                String[] titlearr = Arrays.copyOf(newsTitle.toArray(), newsTitle.size(), String[].class);
                String[] contentarr = Arrays.copyOf(newsContent.toArray(), newsContent.size(), String[].class);
                listAdapter.setTitles(titlearr);
                listAdapter.setContents(contentarr);
                listAdapter.setDisplayedTitles(titlearr);
                listAdapter.setDisplayedContents(contentarr);
                listView.setAdapter(listAdapter);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        listAdapter.getFilter().filter(s);
                        listAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}