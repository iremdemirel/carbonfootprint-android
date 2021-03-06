package com.example.bil496.ui.carboncalculation.add_menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bil496.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class add_flight_menu extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText flight_journey_distance;
    TextView mActionOk, mActionCancel;
    String spinner_flight_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_flight_menu,null);

        mActionOk = v.findViewById(R.id.action_ok);
        mActionCancel = v.findViewById(R.id.action_cancel);
        Spinner flight_type_spinner = v.findViewById(R.id.flight_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.flight_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flight_type_spinner.setAdapter(adapter);
        flight_type_spinner.setOnItemSelectedListener(this);

        flight_journey_distance = v.findViewById(R.id.edit_flight_distance);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = flight_journey_distance.getText().toString();
                getDialog().dismiss();
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url("https://carbonfootprint1.p.rapidapi.com/CarbonFootprintFromFlight?distance="+ input + "&type=" + spinner_flight_text)
                                    .get()
                                    .addHeader("x-rapidapi-key", "aaed0b37a1msh4673114dfa083a6p14111ejsnf86b67abc762")
                                    .addHeader("x-rapidapi-host", "carbonfootprint1.p.rapidapi.com")
                                    .build();


                            Response response = client.newCall(request).execute();
                            System.out.println(response.body().string());

                        } catch (Exception e) {
                            System.out.println(e.getMessage() + "EEEEEEEEEEEEEEEEEEEEEEEE");
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });
        return v;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner_flight_text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}