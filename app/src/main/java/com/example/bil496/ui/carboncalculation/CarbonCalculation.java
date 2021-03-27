package com.example.bil496.ui.carboncalculation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bil496.R;
import com.example.bil496.ui.carboncalculation.add_menus.add_car_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_electricity_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_flight_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_gas_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_motorbike_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_publictransport_menu;
import com.example.bil496.ui.carboncalculation.add_menus.car_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.electricity_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.flight_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.gas_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.motorbike_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.publictransport_data_listener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarbonCalculation extends Fragment{

    public static car_data_listener car_data = new car_data_listener(0);
    public static flight_data_listener flight_data = new flight_data_listener(0);
    public static motorbike_data_listener motorbike_data = new motorbike_data_listener(0);
    public static publictransport_data_listener publictransport_data = new publictransport_data_listener(0);
    public static gas_data_listener gas_data = new gas_data_listener(0);
    public static electricity_data_listener electricity_data = new electricity_data_listener(0);



    private LinearLayout activejourney;
    private LinearLayout flight;
    private LinearLayout car;
    private LinearLayout bike;
    private LinearLayout bus;
    public static PieChart pieChart;
    PieDataSet dataSet;
    private LinearLayout electricity;
    private LinearLayout gas;
    private View v;

    private String currentUserID;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carbon_calculation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getUid();
        v = getView();
        pieChart = (PieChart) v.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setEnabled(false);

        final ArrayList<PieEntry> yValues = new ArrayList<>();

        final DatabaseReference reference_flight= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("flight");
        reference_flight.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flight_data.setFlight_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference_car= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("car");
        reference_car.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                car_data.setCar_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference_motorbike= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("motorbike");
        reference_motorbike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                motorbike_data.setMotorbike_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference_publictransport= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("publictransport");
        reference_publictransport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                publictransport_data.setPublictransport_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference_gas= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("gas");
        reference_gas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gas_data.setGas_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference_electricity= FirebaseDatabase.getInstance().getReference().child("Users").child("irem").child("carbon").child("electricity");
        reference_electricity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                electricity_data.setElectricity_data(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        yValues.add(new PieEntry(flight_data.getFlight_data(), "Uçuş"));
        yValues.add(new PieEntry(car_data.getCar_data(), "Araba"));
        yValues.add(new PieEntry(motorbike_data.getMotorbike_data(), "Motorsiklet"));
        yValues.add(new PieEntry(publictransport_data.getPublictransport_data(), "Toplu Taşıma"));
        yValues.add(new PieEntry(gas_data.getGas_data(), "Doğal gaz"));
        yValues.add(new PieEntry(electricity_data.getElectricity_data(), "Elektrik"));


      pieChart.animate();
        dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        final PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);

        //activejourney = (LinearLayout) v.findViewById(R.id.active_journey_button);
        flight =(LinearLayout) v.findViewById(R.id.fly_button);
        car = (LinearLayout) v.findViewById(R.id.car_button);
        bike = (LinearLayout) v.findViewById(R.id.bicycle_button);
        bus = (LinearLayout) v.findViewById(R.id.bus_button);
        electricity = (LinearLayout) v.findViewById(R.id.electricity_button);
        gas = (LinearLayout) v.findViewById(R.id.gas_button);

        flight_data.setListener(new flight_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(0,new PieEntry(flight_data.getFlight_data(), "Uçuş"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                reference.child("Users").child("irem").child("carbon").child("flight").setValue(flight_data.getFlight_data());

            }
        });

        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_flight_menu dialog = new add_flight_menu();
                dialog.show(getFragmentManager(), "add_flight_menu");
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               add_car_menu dialog = new add_car_menu();
               dialog.show(getFragmentManager(), "add_car_menu");
            }
        });

        car_data.setListener(new car_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(1,new PieEntry(car_data.getCar_data(), "Araba"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                reference.child("Users").child("irem").child("carbon").child("car").setValue(car_data.getCar_data());

            }
        });

        motorbike_data.setListener(new motorbike_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(2, new PieEntry(motorbike_data.getMotorbike_data(), "Motorsiklet"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                reference.child("Users").child("irem").child("carbon").child("motorbike").setValue(motorbike_data.getMotorbike_data());
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_motorbike_menu dialog = new add_motorbike_menu();
                dialog.show(getFragmentManager(), "add_motorbike_menu");
                System.out.println(car_data.getCar_data());
            }
        });

        publictransport_data.setListener(new publictransport_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(3, new PieEntry(publictransport_data.getPublictransport_data(), "Toplu Taşıma"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                reference.child("Users").child("irem").child("carbon").child("publictransport").setValue(publictransport_data.getPublictransport_data());
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_publictransport_menu dialog = new add_publictransport_menu();
                dialog.show(getFragmentManager(), "add_publictransport_menu");
            }
        });

        electricity_data.setListener(new electricity_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(5, new PieEntry(electricity_data.getElectricity_data(),"Elektrik"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                reference.child("Users").child("irem").child("carbon").child("electricity").setValue(electricity_data.getElectricity_data());

            }
        });
        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_electricity_menu dialog = new add_electricity_menu();
                dialog.show(getFragmentManager(), "add_electricity_menu");
            }
        });

       gas_data.setListener(new gas_data_listener.ChangeListener() {
           @Override
           public void onChange() {
               yValues.set(4, new PieEntry(gas_data.getGas_data(),"Doğal Gaz"));
               pieChart.notifyDataSetChanged();
               pieChart.invalidate();
               reference.child("Users").child("irem").child("carbon").child("gas").setValue(gas_data.getGas_data());
           }
       });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_gas_menu dialog = new add_gas_menu();
                dialog.show(getFragmentManager(), "add_gas_menu");
            }
        });




    }

}