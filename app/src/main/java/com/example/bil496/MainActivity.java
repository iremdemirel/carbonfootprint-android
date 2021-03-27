package com.example.bil496;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bil496.foundations.Foundation;
import com.example.bil496.foundations.FoundationNewAdapter;
import com.example.bil496.foundations.FoundationNews;
import com.example.bil496.foundations.WebScrapingGreenPeace;
import com.example.bil496.foundations.WebScrapingTema;
import com.example.bil496.ui.dashboard.NewsFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.example.bil496.R.layout.fragment_dashboard;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends AppCompatActivity {

    ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    RelativeLayout cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_foundations)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        /*Button button = findViewById(R.id.navigation_game);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
                startActivity(intent);
            }
        });

        */
        // web scraping for foundations bulletin
        /*WebScrapingTema temaScraper= new WebScrapingTema();
        temaScraper.scrape();
        WebScrapingGreenPeace greenPeaceScraper = new WebScrapingGreenPeace();
        greenPeaceScraper.scrape();
        */

    }

    /*
        public void logout(final View view) {
            FirebaseAuth.getInstance().signOut();

            GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                    .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Logout failed", Toast.LENGTH_SHORT).show();
                }
            });
        }


     */
    public void changeFrame(String title, String content, LayoutInflater inflater) {
        NewsFragment newsFragment = new NewsFragment(title, content);
        MainActivity.this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_dashboard_container, newsFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}