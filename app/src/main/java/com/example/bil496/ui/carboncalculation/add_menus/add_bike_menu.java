package com.example.bil496.ui.carboncalculation.add_menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bil496.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class add_bike_menu extends AppCompatActivity {

    FloatingActionButton fab_add_bike_journey;
    private ListView table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike_menu);

        table = (ListView) findViewById(R.id.list_bike_journey);
        fab_add_bike_journey = (FloatingActionButton) findViewById(R.id.fab_add_bike_journey);

        setupAddBikeJourneyButton();
        setupTable();
    }

    private void setupAddBikeJourneyButton() {

        fab_add_bike_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_bike_activity.class));
            }
        });
    }



    private void setupTable() {
        table = (ListView) findViewById(R.id.list_bike_journey);
        TextView emptyJourneyTxt = (TextView) findViewById(R.id.emptyJourneyTxt);
        emptyJourneyTxt.setVisibility(View.VISIBLE);

    }
}