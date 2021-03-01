package com.example.bil496.ui.foundations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bil496.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FoundationFragment extends Fragment {

    private FoundationViewModel foundationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foundationViewModel =
                ViewModelProviders.of(this).get(FoundationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_foundation, container, false);
        final TextView textView = root.findViewById(R.id.text_foundation);
        foundationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}