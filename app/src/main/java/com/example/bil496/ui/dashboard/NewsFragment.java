package com.example.bil496.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bil496.R;

import static com.example.bil496.R.layout.fragment_dashboard;

public class NewsFragment extends Fragment {
    private String title;
    private String content;

    public NewsFragment(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        TextView titleView = (TextView) root.findViewById(R.id.title_foundationNew);
        titleView.setText(this.title);

        TextView contentView = (TextView) root.findViewById(R.id.content_foundationNew);
        contentView.setText(this.content);
        return root;
    }
}
