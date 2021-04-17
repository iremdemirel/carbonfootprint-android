package com.example.bil496;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bil496.forFirebase.Blog;
import com.example.bil496.forFirebase.BlogText;
import com.example.bil496.forFirebase.Users;
import com.example.bil496.ui.dashboard.NewsFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    TextView bio;
    EditText editbio;
    AlertDialog dialog;
    Button editBioButton;
    DatabaseReference reference;

    AlertDialog friendPopup;
    private FirebaseRecyclerOptions<Users> posts;
    private FirebaseRecyclerAdapter<Users, FriendViewHolder> adapter;

    private String currentUserID;

    AlertDialog addPostScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getUid();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            insertUsertoDatabase();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_foundations, R.id.navigation_blog)
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

//        FoundationData.totalDonation[0] = dbRef.get().toString()
        setNotification();


    }

    //for notifications
    private void setNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("error:", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d("token", token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void initAddPostScreen(final View view) {

        addPostScreen = new AlertDialog.Builder(this).create();

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText postHeader = new EditText(MainActivity.this);
        postHeader.setHint("Başlık");
        layout.addView(postHeader);

        final EditText postDescription = new EditText(MainActivity.this);
        postDescription.setHint("Açıklama");
        layout.addView(postDescription);

        addPostScreen.setView(layout);
        addPostScreen.setTitle("Yeni gönderi");

        addPostScreen.setButton(DialogInterface.BUTTON_POSITIVE, "Gönder", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Blog newPost = new Blog(new BlogText(postHeader.getText().toString(), postDescription.getText().toString()), new Date().toString());


                final String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                reference.child("Users").child(currentUserID).child("blog").push().setValue(newPost);

            }
        });

        addPostScreen.show();
    }

    public void insertUsertoDatabase() {
        final String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("email").exists())) {
                    //Toast.makeText(MainActivity.this, "Veritabanına kayıtlı", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = reference.child("Users").child(currentUserID);

                    Users users = new Users();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    users.setName(user.getDisplayName());
                    users.setEmail(user.getEmail());
                    users.setPhotoURL(Objects.requireNonNull(user.getPhotoUrl()).toString());
                    users.setBio("");

                    ref.setValue(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(MainActivity.this, "Veritabanına kaydedilmedi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Logout başarısız", Toast.LENGTH_SHORT).show();
            }
        });

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(view.getContext(), LoginActivity.class));
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

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bio.setText(editbio.getText());

                final String currentUserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                reference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.child("email").exists())) {

                            reference.child("Users").child(currentUserID).child("bio").setValue(editbio.getText().toString()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Toast.makeText(MainActivity.this, "Veritabanına bio kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            //t.makeText(MainActivity.this, "Veritabanında kullanıcı kayıtlı degil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Toast.makeText(MainActivity.this, "Veritabanına bio kaydedilmedi", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        editbio.setText(bio.getText());
        dialog.show();
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

    //Opens a popup to add friend
    public void addFriendPopup(final View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.friend_dialog_layout, null);

        builder.setView(dialogView);

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.dialogRecycler);
        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(this));


        posts = new FirebaseRecyclerOptions.Builder<Users>().setQuery(reference.child("Users"), Users.class).build();
        adapter = new FirebaseRecyclerAdapter<Users, FriendViewHolder>(posts) {
            @NonNull
            @Override
            public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_view_layout, parent, false);
                return new FriendViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull FriendViewHolder holder, final int position, @NonNull final Users model) {
                holder.friendName.setText(model.getName());
                holder.friendEmail.setText(model.getEmail());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String key = getRef(position).getKey();
                        //Toast.makeText(MainActivity.this, "key:" + key, Toast.LENGTH_SHORT).show();


                        reference.child("Users").child(currentUserID).child("friends").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (key != null) {
                                    if (currentUserID.equalsIgnoreCase(key)) {
                                        //Toast.makeText(MainActivity.this, "Kendi hesabınız", Toast.LENGTH_SHORT).show();
                                    } else if ((dataSnapshot.hasChild(key))) {

                                        //Toast.makeText(MainActivity.this, "Arkadaş kayıtlı", Toast.LENGTH_SHORT).show();

                                    } else {
                                        reference.child("Users").child(currentUserID).child("friends").child(key).setValue(model);
                                        //Toast.makeText(MainActivity.this, "Arkadaş kaydedildi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //Toast.makeText(MainActivity.this, "Arkadaş kaydedilmedi", Toast.LENGTH_SHORT).show();
                            }
                        });
                        friendPopup.dismiss();
                    }
                });


            }


        };

        adapter.startListening();
        rv.setAdapter(adapter);

        friendPopup = builder.create();

        friendPopup.show();


    }

    public void friendAddCancel(View view) {
        friendPopup.dismiss();
    }
}


