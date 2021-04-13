package com.example.bil496.ui.carboncalculation.add_menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bil496.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class add_bus_menu extends AppCompatActivity {
    FloatingActionButton fab_add_bus_journey;
    boolean isClicked = false;
    private ListView table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_menu);
        table = (ListView) findViewById(R.id.list_bus_journey);
        fab_add_bus_journey = (FloatingActionButton) findViewById(R.id.fab_add_bus_journey);
        setupAddBusJourneyButton();
        setupTable();
    }

    private void setupAddBusJourneyButton() {
        fab_add_bus_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_bus_activity.class));
            }
        });
    }



    private void setupTable() {
        table = (ListView) findViewById(R.id.list_bus_journey);
        TextView emptyJourneyTxt = (TextView) findViewById(R.id.emptyJourneyTxt);
        emptyJourneyTxt.setVisibility(View.VISIBLE);

    }
}