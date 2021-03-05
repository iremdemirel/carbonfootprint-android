package com.example.bil496;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bil496.forFirebase.Users;
import com.example.bil496.foundations.WebScrapingGreenPeace;
import com.example.bil496.foundations.WebScrapingTema;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView bio;
    EditText editbio;
    AlertDialog dialog;
    Button editBioButton;
    DatabaseReference reference;
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

        reference = FirebaseDatabase.getInstance().getReference();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            insertUsertoDatabase();
        }


        // web scraping for foundations bulletin
        WebScrapingTema temaScraper = new WebScrapingTema();
        temaScraper.scrape();
        WebScrapingGreenPeace greenPeaceScraper = new WebScrapingGreenPeace();
        greenPeaceScraper.scrape();

    }

    private void insertUsertoDatabase() {
        final String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("email").exists())) {
                    Toast.makeText(MainActivity.this, "Veritabanına kayıt", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = reference.child("Users").child(currentUserID);

                    Users users = new Users();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    users.setName(user.getDisplayName());
                    users.setEmail(user.getEmail());
                    users.setPhotoURL(user.getPhotoUrl().toString());
                    users.setBio("");

                    ref.setValue(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Veritabanına kaydedilmedi", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void editBio(final View view) {

        bio = (TextView) findViewById(R.id.bio);
        dialog = new AlertDialog.Builder(this).create();
        editbio = new EditText(this);

        dialog.setTitle("Bio düzenle");
        dialog.setView(editbio);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Kaydet", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bio.setText(editbio.getText());

                final String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                reference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.child("email").exists())) {

                            reference.child("Users").child(currentUserID).child("bio").setValue(editbio.getText().toString()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(MainActivity.this, "Veritabanına bio kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Veritabanında kullanıcı kayıtlı degil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Veritabanına bio kaydedilmedi", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        editbio.setText(bio.getText());
        dialog.show();
    }
}
