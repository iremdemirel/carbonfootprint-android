package com.example.bil496.foundations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bil496.R;
import com.example.bil496.ui.dashboard.Callback;
import com.example.bil496.ui.foundations.FoundationPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DonationDialog extends AppCompatDialogFragment {
    private EditText donationAmount;
    private String foundationName = "";
    static FirebaseDatabase database;
    int donation = 0;
    boolean flagDonation = false;

    public DonationDialog(String name){
        foundationName = name;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreateDialog**");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.donation_dialog,null);
        builder.setView(view).setTitle("Bağış")
                .setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        })
                .setPositiveButton("Bağışla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flagDonation = true;
                        String d = donationAmount.getText().toString();
                        donation = Integer.parseInt(d);
                        System.out.println("Donation to " + foundationName + ": " + donation);

                        readData(new Callback(){
                            @Override
                            public void onCallback(String title, String content) {

                            }
                        });

                    }
        });
        donationAmount = view.findViewById(R.id.donation_amount);

        return builder.create();
    }
    public void readData(final Callback callback){
        if (flagDonation){
            database = FirebaseDatabase.getInstance();
            final DatabaseReference dbRefQuoteRequestList = database.getReference("Foundations").child(foundationName)
                    .child("totalDonation");


            dbRefQuoteRequestList.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                    Long totalD = (Long) dataSnapshot.getValue();
                    if (flagDonation){
                        int currDonation = Integer.parseInt(totalD.toString()) + donation;
                        dbRefQuoteRequestList.setValue(currDonation);
                        FoundationPageFragment.setDonationText(currDonation);
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
