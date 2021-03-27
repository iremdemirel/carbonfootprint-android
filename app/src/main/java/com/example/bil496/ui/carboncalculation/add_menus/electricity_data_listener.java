package com.example.bil496.ui.carboncalculation.add_menus;

public class electricity_data_listener {
    private float electricity_data = 0;
    private electricity_data_listener.ChangeListener listener;

    public electricity_data_listener(float electricity_data) {
        this.electricity_data =electricity_data;
    }

    public float getElectricity_data() {
        return electricity_data;
    }

    public void setElectricity_data(float electricity_data) {
        this.electricity_data = electricity_data;
        if (listener != null) listener.onChange();
    }

    public electricity_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(electricity_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}

