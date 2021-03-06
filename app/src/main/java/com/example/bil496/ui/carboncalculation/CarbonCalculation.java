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
import com.example.bil496.ui.carboncalculation.add_menus.add_publictransport_menu;
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

    public String a ;
    private LinearLayout activejourney;
    private LinearLayout flight;
    private LinearLayout car;
    private LinearLayout bike;
    private LinearLayout bus;
    private PieChart pieChart;
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

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(34f, "PartyA"));
        yValues.add(new PieEntry(24f, "USA"));
        yValues.add(new PieEntry(14f, "EK"));
        yValues.add(new PieEntry(38, "s"));
        yValues.add(new PieEntry(54, "PartyYY"));
        yValues.add(new PieEntry(36, "hhhhh"));

        pieChart.animate();
        PieDataSet dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
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
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
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