package com.example.bil496.ui.carboncalculation.add_menus;

public class flight_data_listener {
    private float flight_data = 0;
    private flight_data_listener.ChangeListener listener;

    public flight_data_listener(float flight_data) {
        this.flight_data =flight_data;
    }

    public float getFlight_data() {
        return flight_data;
    }

    public void setFlight_data(float flight_data) {
        this.flight_data = flight_data;
        if (listener != null) listener.onChange();
    }

    public flight_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(flight_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
