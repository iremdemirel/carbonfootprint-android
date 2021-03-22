package com.example.bil496.ui.foundations;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bil496.R;
import com.example.bil496.foundations.DonationDialog;
import com.example.bil496.foundations.FoundationItemAdapter;
import com.example.bil496.foundations.FoundationNewsListAdapter;
import com.example.bil496.ui.carboncalculation.add_menus.add_car_menu;
import com.example.bil496.ui.dashboard.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.bil496.R.layout.fragment_dashboard;

public class FoundationPageFragment extends Fragment {
    private String description;
    private ImageView logo;
    private TextView donationText;
    static FirebaseDatabase database;
    private FoundationNewsListAdapter listAdapter;
    private ListView listView;

    public FoundationPageFragment(String description, ImageView logo) {
        this.description = description;
        this.logo = logo;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foundationpage, container, false);


        TextView desc = (TextView) view.findViewById(R.id.foundation_page_description);
        desc.setText(this.description);

        ImageView contentView = (ImageView) view.findViewById(R.id.foundation_logo);
        contentView.setImageDrawable(logo.getDrawable());

        Button button = (Button) view.findViewById(R.id.donation_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DonationDialog donationDialog = new DonationDialog(description.substring(0,description.indexOf(" ")));
                AppCompatActivity activity = (AppCompatActivity)view.getContext();

                donationDialog.show(activity.getSupportFragmentManager(),"dialog");
                System.out.println("button clicked donation");
            }
        });
        donationText = view.findViewById(R.id.donation_text);
        readData(new Callback(){
            @Override
            public void onCallback(String title, String content) {

            }
        });

        listView = view.findViewById(R.id.lv_foundationNews);
        listAdapter = new FoundationNewsListAdapter(inflater, getActivity());

        readNewsData(new Callback(){
            @Override
            public void onCallback(String title, String content) {

            }
        });

        return view;

    }
    public static void setDonationText(int donation){

    }

    public void readData(final Callback callback){
            database = FirebaseDatabase.getInstance();
            final DatabaseReference dbRefQuoteRequestList = database.getReference("Foundations").child(description.substring(0,description.indexOf(" ")))
                    .child("totalDonation");


            dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                    Long totalD = (Long) dataSnapshot.getValue();
                    donationText.setText(totalD.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }


    public void readNewsData(final Callback callback){
        database = FirebaseDatabase.getInstance();
        DatabaseReference dbRefQuoteRequestList = database.getReference("Foundations").child(description.substring(0,description.indexOf(" ")))
                .child("bulletin");

        dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                ArrayList<String> newsTitle = new ArrayList<>();
                ArrayList<String> newsContent = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String content = postSnapshot.child("content").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    newsTitle.add(title);
                    newsContent.add(content);

                }
                String[] titlearr = Arrays.copyOf(newsTitle.toArray(), newsTitle.size(), String[].class);
                String[] contentarr = Arrays.copyOf(newsContent.toArray(), newsContent.size(), String[].class);
                listAdapter.setTitles(titlearr);
                listAdapter.setContents(contentarr);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
