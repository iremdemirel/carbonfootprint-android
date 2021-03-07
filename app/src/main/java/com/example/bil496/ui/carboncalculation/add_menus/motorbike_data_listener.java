package com.example.bil496.ui.carboncalculation.add_menus;

public class motorbike_data_listener {
    private float motorbike_data = 0;
    private motorbike_data_listener.ChangeListener listener;

    public motorbike_data_listener(float motorbike_data) {
        this.motorbike_data =motorbike_data;
    }

    public float getMotorbike_data() {
        return motorbike_data;
    }

    public void setMotorbike_data(float motorbike_data) {
        this.motorbike_data = motorbike_data;
        if (listener != null) listener.onChange();
    }

    public motorbike_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(motorbike_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
