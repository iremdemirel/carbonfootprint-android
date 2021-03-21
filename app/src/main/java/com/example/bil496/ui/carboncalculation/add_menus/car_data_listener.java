package com.example.bil496.ui.carboncalculation.add_menus;

public class car_data_listener {
    private float car_data = 0;
    private ChangeListener listener;

    public car_data_listener(float car_data) {
        this.car_data =car_data;
    }

    public float getCar_data() {
        return car_data;
    }

    public void setCar_data(float car_data) {
        this.car_data = car_data;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
