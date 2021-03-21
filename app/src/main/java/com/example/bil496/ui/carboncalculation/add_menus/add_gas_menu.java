package com.example.bil496.ui.carboncalculation.add_menus;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bil496.MainActivity;
import com.example.bil496.R;
import com.example.bil496.model.Utility;
import com.example.bil496.ui.carboncalculation.CarbonCalculation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_gas_menu extends DialogFragment{
    private Utility newUtility;
    private double billAmount;
    private int numPeople;
    private long interval;
    private String billStart;
    private String billEnd;
    TextView txt_billDateTo;
    TextView txt_billDateFrom;
    TextView txt_utilityPeople;
    TextView txt_utilityAmount;
    TextView action_ok;
    TextView action_cancel;
    Button btn_billDateTo;
    Button btn_billDateFrom;
    EditText edit_utilityPeople;
    EditText edit_utilityAmount;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, dayOfMonth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_gas_menu, null);
        txt_billDateTo = v.findViewById(R.id.txt_billDateTo);
        txt_billDateFrom = v.findViewById(R.id.txt_billDateFrom);
        txt_utilityAmount = v.findViewById(R.id.txt_utilityAmount);
        txt_utilityPeople = v.findViewById(R.id.txt_utilityPeople);
        action_ok = v.findViewById(R.id.action_ok);
        action_cancel = v.findViewById(R.id.action_cancel);
        btn_billDateTo = v.findViewById(R.id.btn_billDateTo);
        btn_billDateFrom = v.findViewById(R.id.btn_billDateFrom);
        edit_utilityAmount = v.findViewById(R.id.edit_utilityAmount);
        edit_utilityPeople = v.findViewById(R.id.edit_utilityAmount);

        btn_billDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                txt_billDateTo.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        btn_billDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                txt_billDateFrom.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });



        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = edit_utilityAmount.getText().toString();
                String people= edit_utilityPeople.getText().toString();
                billAmount = Double.parseDouble(amount);
                numPeople = Integer.parseInt(people);
                billStart= txt_billDateFrom.getText().toString();
                billEnd = txt_billDateTo.getText().toString();
                newUtility = new Utility("Natural Gas",billStart,billEnd,billAmount, numPeople);
                double d= newUtility.getTotalEmission();
                getDialog().dismiss();
                CarbonCalculation.gas_data.setGas_data( CarbonCalculation.gas_data.getGas_data()+Float.parseFloat(""+d));

            }
        });

        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();

            }
        });
        return v;

    }
}