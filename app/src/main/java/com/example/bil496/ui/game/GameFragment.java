package com.example.bil496.ui.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.bil496.MainActivity;
import com.example.bil496.R;
import com.example.bil496.UnityHandlerActivity;
import com.unity3d.player.UnityPlayerActivity;

import static com.example.bil496.R.layout.fragment_dashboard;
import static com.example.bil496.R.layout.fragment_game;

public class GameFragment extends Fragment {
    private View root;
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(fragment_game, container, false);
        Button button = root.findViewById(R.id.neWorld_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( (MainActivity) getContext(), UnityHandlerActivity.class));
            }
        });
        return root;
    }
}
