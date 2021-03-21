package com.example.bil496.ui.carboncalculation.add_menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bil496.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class add_electricity_menu extends AppCompatActivity {

    FloatingActionButton fab_add_electricity;
    boolean isClicked = false;
    private ListView table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_electricity_menu);

        table = (ListView) findViewById(R.id.list_electricity);
        fab_add_electricity = (FloatingActionButton) findViewById(R.id.fab_add_electricity);
        setupAddElectricityButton();
        setupTable();
    }

    private void setupAddElectricityButton() {

        fab_add_electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_electricity_activity.class));
            }
        });
    }


    private void setupTable() {
        table = (ListView) findViewById(R.id.list_electricity);
        TextView emptyJourneyTxt = (TextView) findViewById(R.id.emptyJourneyTxt);
        emptyJourneyTxt.setVisibility(View.VISIBLE);

    }
}