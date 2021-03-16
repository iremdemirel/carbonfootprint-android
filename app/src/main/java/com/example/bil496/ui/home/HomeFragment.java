package com.example.bil496.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bil496.LoginActivity;
import com.example.bil496.R;
import com.example.bil496.forFirebase.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageView imageView;
    TextView name, email, bio;
    FirebaseAuth mAuth;
    EditText editbio;
    AlertDialog dialog;
    Button editBioButton;
    DatabaseReference reference;
    public static AlertDialog frienddialog;


    private FirebaseRecyclerOptions<Users> posts;
    private FirebaseRecyclerAdapter<Users, FriendViewHolder> adapter;
    private RecyclerView recyclerView;

    private String currentUserID;
    private AlertDialog friendPopup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = (ImageView) root.findViewById(R.id.photo);
        name = (TextView) root.findViewById(R.id.name);
        email = (TextView) root.findViewById(R.id.email);
        bio = (TextView) root.findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getUid();

        recyclerView = (RecyclerView) root.findViewById(R.id.friendrecyclerView);
        initProfileInfo();

        return root;
    }

    //initialize profile information of the user
    private void initProfileInfo() {

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Glide.with(this.getActivity())
                    .load(user.getPhotoUrl())
                    .into(imageView);

            name.setText(user.getDisplayName());
            email.setText(user.getEmail());

            reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("bio").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "bio yok", Toast.LENGTH_SHORT).show();
                    } else {
                        if (task.getResult().getValue() == null) {
                            bio.setText("SET YOUR BIO");
                        } else {
                            bio.setText(String.valueOf(task.getResult().getValue()));
                        }
                    }
                }
            });

        }
    }


    //logout
    public void logout(final android.view.View view) {
        FirebaseAuth.getInstance().signOut();

        GoogleSignIn.getClient(this.getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Logout failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //edit bio of the user
    public void editBio(final View view) {
        bio = (TextView) view.findViewById(R.id.bio);
        dialog = new AlertDialog.Builder(getActivity()).create();
        editbio = new EditText(getActivity());

        dialog.setTitle("Bio düzenle");
        dialog.setView(editbio);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "KAYDET", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bio.setText(editbio.getText());
            }
        });

        editbio.setText(bio.getText());
        dialog.show();
    }

    //add friend dialog
    public void addFriendPopup(final View view) {

        Toast.makeText(getContext(), "in add friend popup", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.friend_view_layout, null);

        builder.setView(dialogView);

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.friendrecyclerView);


        //friendPopup = new AlertDialog.Builder(this.getActivity()).create();

        //CreateView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

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

                        String key = getRef(position).getKey();

                        reference.child("Users").child(currentUserID).child("friends").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.child("email").exists())) {

                                    Toast.makeText(getContext(), "Arkadaş kayıtlı", Toast.LENGTH_SHORT).show();

                                } else {

                                    reference.child("Users").child(currentUserID).child("friends").push().setValue(model);
                                    Toast.makeText(getContext(), "Arkadaş kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "Veritabanına arkadaş kaydedilmedi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        };

        adapter.startListening();
        // recyclerView.setAdapter(adapter);

        rv.setAdapter(adapter);

        frienddialog = builder.create();

        //dialog.show();


        /*RecyclerView.ItemDecoration divider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);


        friendPopup.setView(recyclerView);
        friendPopup.setTitle("Yeni arkadaş ekle");


        friendPopup.show();*/
    }
}

