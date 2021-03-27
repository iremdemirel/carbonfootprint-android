package com.example.bil496.ui.carboncalculation.add_menus;

public class gas_data_listener {
    private float gas_data = 0;
    private gas_data_listener.ChangeListener listener;

    public gas_data_listener(float gas_data) {
        this.gas_data =gas_data;
    }

    public float getGas_data() {
        return gas_data;
    }

    public void setGas_data(float gas_data) {
        this.gas_data = gas_data;
        if (listener != null) listener.onChange();
    }

    public gas_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(gas_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}

