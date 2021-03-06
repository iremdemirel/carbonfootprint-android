package com.example.bil496.ui.carboncalculation.add_menus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.bil496.R;
import com.example.bil496.ui.carboncalculation.CarbonCalculation;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static java.lang.Integer.parseInt;


public class add_car_menu extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner car_type_spinner;
    private EditText car_journey_distance;
    String spinner_car_text;
    TextView mActionOk, mActionCancel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_menu, null);

        mActionOk = v.findViewById(R.id.action_ok);
        mActionCancel = v.findViewById(R.id.action_cancel);
        car_type_spinner = v.findViewById(R.id.car_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.car_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        car_type_spinner.setAdapter(adapter);
        car_type_spinner.setOnItemSelectedListener(this);

        car_journey_distance = v.findViewById(R.id.edit_car_distance);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String input = car_journey_distance.getText().toString();
                getDialog().dismiss();
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url("https://carbonfootprint1.p.rapidapi.com/CarbonFootprintFromCarTravel?distance=" + input + "&vehicle=" + spinner_car_text)
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
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}