package com.example.bil496.foundations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.bil496.R;
import com.example.bil496.ui.dashboard.Callback;
import com.example.bil496.ui.foundations.FoundationPageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AfterDonationDialog extends AppCompatDialogFragment {
    private TextView after_donation_text;
    private String foundationName = "";
    int donationAmount = 0;
    static FirebaseDatabase database;
    public boolean flagDonation = false;


    public AfterDonationDialog(String name, int donationAmount){
        this.foundationName = name;
        this.donationAmount = donationAmount;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        flagDonation = true;
        System.out.println("onCreateDialog**");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.after_donation_dialog,null);
        after_donation_text = (TextView)view.findViewById(R.id.after_donation_text);
        String info = foundationName + " Vakfı'na " + donationAmount +
                " TL bağışladığınız için çevreye pozitif bir katkıda bulundunuz! " +
                "Bunun sonucunda karbon ayak iziniz "+ (double)donationAmount/10.0 +
                " birim azaldı. Daha fazla bağış yaparak karbon " +
                "ayak izinizi azaltmaya devam edebilirsiniz!";
        after_donation_text.setText(info);
        System.out.println(info);
        readData(new Callback(){
            @Override
            public void onCallback(String title, String content) {

            }
        });
        builder.setView(view).setTitle("\n")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }

                );

        return builder.create();
    }

    public void readData(final Callback callback){
        if (flagDonation){
            database = FirebaseDatabase.getInstance();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            final DatabaseReference dbRefQuoteRequestList = database.getReference("Users")
                    .child(firebaseAuth
                            .getCurrentUser()
                            .getUid())
                    .child("carbon").child("total");

            dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                    Double footprint = 0.0;
                    if(Long.class.isInstance(dataSnapshot.getValue())){
                        footprint = ((Long) dataSnapshot.getValue()).doubleValue();
                    }else{
                        footprint = (Double) dataSnapshot.getValue();
                    }
                    if (flagDonation) {
                        double currfootprint = footprint - ((double) donationAmount / 10.0);
                        dbRefQuoteRequestList.setValue(currfootprint);
                        System.out.println("Curr Footprint: " + currfootprint);
                    }
                    flagDonation = false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

}
