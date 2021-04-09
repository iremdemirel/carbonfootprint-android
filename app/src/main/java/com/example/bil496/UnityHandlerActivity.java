package com.example.bil496;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.unity3d.player.UnityPlayerActivity;

public class UnityHandlerActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(UnityHandlerActivity.this, UnityPlayerActivity.class);
        startActivity(intent);
    }
}
