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

import com.bumptech.glide.Glide;
import com.example.bil496.LoginActivity;
import com.example.bil496.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageView imageView;
    TextView name, email, bio;
    FirebaseAuth mAuth;
    EditText editbio;
    AlertDialog dialog;
    Button editBioButton;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = (ImageView) root.findViewById(R.id.photo);
        name = (TextView) root.findViewById(R.id.name);
        email = (TextView) root.findViewById(R.id.email);
        bio = (TextView) root.findViewById(R.id.bio);

        initProfileInfo();

        return root;
    }

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
            Toast.makeText(getContext(), mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
            reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("bio").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "bio veritabanından getirilemedi", Toast.LENGTH_SHORT).show();
                    } else {
                        bio.setText(String.valueOf(task.getResult().getValue()));
                    }
                }
            });
        }
    }

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
}