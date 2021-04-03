package com.example.bil496.ui.carboncalculation.add_menus;

public class total_data_listener {
    private float total_data = 0;
    private total_data_listener.ChangeListener listener;

    public total_data_listener(float total_data) {
        this.total_data =total_data;
    }

    public float getTotal_data() {
        return total_data;
    }

    public void setTotal_data(float total_data) {
        this.total_data = total_data;
        if (listener != null) listener.onChange();
    }

    public total_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(total_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
