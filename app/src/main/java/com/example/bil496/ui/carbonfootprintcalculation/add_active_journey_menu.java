package com.example.bil496.ui.carbonfootprintcalculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bil496.R;

public class add_active_journey_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_active_journey_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_utility_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.add_utility_toolbar_title);
        title.setText("hi");


    }
}