package com.example.bil496.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

import static com.example.bil496.R.*;
import static com.example.bil496.R.layout.*;

public class DashboardFragment extends Fragment {
    final ArrayList<String> newsTitle = new ArrayList<>();
    final ArrayList<String> newsContent = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    Context cont;
    private ListView listView;
    private View root;
    private FoundationNewsListAdapter listAdapter;

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsTitle.add("atakan");
        newsContent.add("deneme");

        cont = getContext();
        //dashboardViewModel = (ListView)getView().findViewById(R.id.listView);
        root = inflater.inflate(fragment_dashboard, container, false);

        listView = root.findViewById(id.lv_foundationNews);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRefQuoteRequestList = firebaseDatabase.getReference("Foundations").child("Green Peace")
                .child("bulletin");

        listAdapter = new FoundationNewsListAdapter(inflater);
        dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                newsTitle.clear(); // ArrayList<Pojo/Object> \\
                newsContent.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String content = postSnapshot.child("content").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    System.out.println("title:" + title);
                    //Use the dataType you are using and also use the reference of those childs inside arrays\\

                    // Putting Data into Getter Setter \\
                    newsTitle.add(title);
                    newsContent.add(content);

                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String[] titlearr = Arrays.copyOf(newsTitle.toArray(), newsTitle.size(), String[].class);
        String[] contentarr = Arrays.copyOf(newsContent.toArray(), newsContent.size(), String[].class);
        listAdapter.setTitles(titlearr);
        listAdapter.setContents(contentarr);
        listView.setAdapter(listAdapter);


        /*dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);*/

        /*dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;

    }
}