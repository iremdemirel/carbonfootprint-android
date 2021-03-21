package com.example.bil496;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bil496.foundations.Foundation;
import com.example.bil496.foundations.WebScrapingGreenPeace;
import com.example.bil496.foundations.WebScrapingTema;
import com.example.bil496.ui.dashboard.NewsFragment;
import com.example.bil496.ui.foundations.FoundationPageFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


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


        // web scraping for foundations bulletin
        /*WebScrapingTema temaScraper= new WebScrapingTema();
        temaScraper.scrape();
        WebScrapingGreenPeace greenPeaceScraper = new WebScrapingGreenPeace();
        greenPeaceScraper.scrape();
        */

    }


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

    public void changeFrame(String title, String content, LayoutInflater inflater){
        /*FoundationNewAdapter foundationNewAdapter = new FoundationNewAdapter(title, content, MainActivity.this);
        View root = inflater.inflate(fragment_dashboard, container, false);
        listlistView = root.findViewById(R.id.lv_foundationNews);*/
        NewsFragment newsFragment = new NewsFragment(title, content); //yeni acacagin fragment
        MainActivity.this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_dashboard_container, newsFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
        /*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.navigation_dashboard, newsFragment, null);
        transaction.commit();
        */
        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.lv_foundationNews, newsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        View v = inflater.inflate(R.layout.list_item, null);
        holder.title = (TextView) v.findViewById(R.id.title_foundationNew);
        holder.content = (TextView) v.findViewById(R.id.content_foundationNew);
        holder.title.setText(titles[position]);
        holder.content.setText(contents[position]);*/
    }

}