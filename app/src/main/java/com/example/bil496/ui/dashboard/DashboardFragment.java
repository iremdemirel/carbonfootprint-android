package com.example.bil496.ui.dashboard;

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
import com.example.bil496.foundations.FoundationNews;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.bil496.R.*;
import static com.example.bil496.R.layout.*;

public class DashboardFragment extends Fragment {
    ArrayList<FoundationNews> news = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    private ListView listView;
    private View root;
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        listView = root.findViewById(id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        final ArrayList<String> newsList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(root.getContext(), R.layout.list_item, newsList);
        listView.setAdapter(adapter);

        DatabaseReference dbRefQuoteRequestList = firebaseDatabase.getReference("Foundations").child("Green Peace")
                .child("bulletin");
        dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                news.clear(); // ArrayList<Pojo/Object> \\

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String content = postSnapshot.child("content").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    //Use the dataType you are using and also use the reference of those childs inside arrays\\

                    // Putting Data into Getter Setter \\
                    /*FoundationNews fnew = new FoundationNews();
                    fnew.setTitle(title);
                    fnew.setContent(content);
                    */
                    newsList.add(title);

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //dashboardViewModel = (ListView)getView().findViewById(R.id.listView);
        root = inflater.inflate(fragment_dashboard, container, false);


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