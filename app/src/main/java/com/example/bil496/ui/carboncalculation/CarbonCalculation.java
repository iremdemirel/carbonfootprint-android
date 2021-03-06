package com.example.bil496.ui.carboncalculation;

import android.content.Intent;
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

import javax.xml.datatype.Duration;

public class CarbonCalculation extends Fragment{

    public String a ;
    private LinearLayout activejourney;
    private LinearLayout flight;
    private LinearLayout car;
    private LinearLayout bike;
    private LinearLayout bus;
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