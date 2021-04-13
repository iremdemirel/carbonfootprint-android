package com.example.bil496.ui.carboncalculation.add_menus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bil496.R;

public class add_active_journey_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_active_journey_menu);


        TextView title = (TextView) findViewById(R.id.active_journey_text);
        title.setText("hi");


    }
}