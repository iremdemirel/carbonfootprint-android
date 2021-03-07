package com.example.bil496.ui.carboncalculation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bil496.R;
import com.example.bil496.ui.carboncalculation.add_menus.add_active_journey_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_car_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_flight_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_motorbike_menu;
import com.example.bil496.ui.carboncalculation.add_menus.add_publictransport_menu;
import com.example.bil496.ui.carboncalculation.add_menus.car_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.flight_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.motorbike_data_listener;
import com.example.bil496.ui.carboncalculation.add_menus.publictransport_data_listener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.datatype.Duration;

public class CarbonCalculation extends Fragment{

    public static car_data_listener car_data = new car_data_listener(0);
    public static flight_data_listener flight_data = new flight_data_listener(0);
    public static motorbike_data_listener motorbike_data = new motorbike_data_listener(0);
    public static publictransport_data_listener publictransport_data = new publictransport_data_listener(0);

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carbon_calculation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        yValues.add(new PieEntry(flight_data.getFlight_data(), "Uçuş"));
        yValues.add(new PieEntry(car_data.getCar_data(), "Araba"));
        yValues.add(new PieEntry(motorbike_data.getMotorbike_data(), "Motorsiklet"));
        yValues.add(new PieEntry(publictransport_data.getPublictransport_data(), "Toplu Taşıma"));


        pieChart.animate();
        dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        final PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);

        activejourney = (LinearLayout) v.findViewById(R.id.active_journey_button);
        flight =(LinearLayout) v.findViewById(R.id.fly_button);
        car = (LinearLayout) v.findViewById(R.id.car_button);
        bike = (LinearLayout) v.findViewById(R.id.bicycle_button);
        bus = (LinearLayout) v.findViewById(R.id.bus_button);
        electricity = (LinearLayout) v.findViewById(R.id.electricity_button);
        gas = (LinearLayout) v.findViewById(R.id.gas_button);


        activejourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_active_journey_menu.class));
            }
        });

        flight_data.setListener(new flight_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(0,new PieEntry(flight_data.getFlight_data(), "Uçuş"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
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
            }
        });

        motorbike_data.setListener(new motorbike_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(2, new PieEntry(motorbike_data.getMotorbike_data(), "Motorsiklet"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_motorbike_menu dialog = new add_motorbike_menu();
                dialog.show(getFragmentManager(), "add_motorbike?menu");
                System.out.println(car_data.getCar_data());
            }
        });

        publictransport_data.setListener(new publictransport_data_listener.ChangeListener() {
            @Override
            public void onChange() {
                yValues.set(3, new PieEntry(publictransport_data.getPublictransport_data(), "Toplu Taşıma"));
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_publictransport_menu dialog = new add_publictransport_menu();
                dialog.show(getFragmentManager(), "add_publictransport_menu");
            }
        });

       /* electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_electricity_menu.class));
            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), add_gas_menu.class));
            }
        });

*/


    }

}