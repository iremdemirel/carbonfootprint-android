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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bil496.R;
import com.example.bil496.ui.carboncalculation.CarbonCalculation;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class add_publictransport_menu extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText publictransport_journey_distance;
    TextView mActionOk, mActionCancel;
    String spinner_publictransport_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_publictransport_menu, null);

        mActionOk = v.findViewById(R.id.action_ok);
        mActionCancel = v.findViewById(R.id.action_cancel);
        Spinner publictransport_type_spinner = v.findViewById(R.id.publictransport_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.publictransport_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        publictransport_type_spinner.setAdapter(adapter);
        publictransport_type_spinner.setOnItemSelectedListener(this);
        publictransport_journey_distance = v.findViewById(R.id.edit_publictransport_distance);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = publictransport_journey_distance.getText().toString();
                getDialog().dismiss();
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("https://carbonfootprint1.p.rapidapi.com/CarbonFootprintFromPublicTransit?distance="+ input + "&type=" + spinner_publictransport_text)
                                    .get()
                                    .addHeader("x-rapidapi-key", "2277135e63msh0f82ad532a92f24p188d1bjsn4c952e0a2b35")
                                    .addHeader("x-rapidapi-host", "carbonfootprint1.p.rapidapi.com")
                                    .build();


                            Response response = client.newCall(request).execute();
                            JSONObject reader = new JSONObject(response.body().string());
                            CarbonCalculation.publictransport_data.setPublictransport_data( CarbonCalculation.publictransport_data.getPublictransport_data()+Float.parseFloat(""+reader.getDouble("carbonEquivalent")));

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
        spinner_publictransport_text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}