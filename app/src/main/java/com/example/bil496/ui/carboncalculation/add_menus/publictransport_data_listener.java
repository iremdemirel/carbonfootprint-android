package com.example.bil496.ui.carboncalculation.add_menus;

public class publictransport_data_listener {
    private float publictransport_data = 0;
    private publictransport_data_listener.ChangeListener listener;

    public publictransport_data_listener(float publictransport_data) {
        this.publictransport_data =publictransport_data;
    }

    public float getPublictransport_data() {
        return publictransport_data;
    }

    public void setPublictransport_data(float publictransport_data) {
        this.publictransport_data = publictransport_data;
        if (listener != null) listener.onChange();
    }

    public publictransport_data_listener.ChangeListener getListener() {
        return listener;
    }

    public void setListener(publictransport_data_listener.ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
