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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DonationDialog extends AppCompatDialogFragment {
    private EditText donationAmount;
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
                        String d = donationAmount.getText().toString();
                        int donation = Integer.parseInt(d);
                    }
        })
                .setPositiveButton("Bağışla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String d = donationAmount.getText().toString();
                        int donation = Integer.parseInt(d);
                        System.out.println("Donation: " + donation);
                    }
        });
        donationAmount = view.findViewById(R.id.donation_amount);

        return builder.create();
    }

}
