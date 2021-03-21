package com.example.bil496.ui.foundations;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bil496.R;
import com.example.bil496.foundations.DonationDialog;
import com.example.bil496.foundations.FoundationItemAdapter;
import com.example.bil496.ui.carboncalculation.add_menus.add_car_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoundationPageFragment extends Fragment {
    private String description;
    private ImageView logo;
    public FoundationPageFragment(String description, ImageView logo) {
        this.description = description;
        this.logo = logo;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foundationpage, container, false);


        TextView desc = (TextView) view.findViewById(R.id.foundation_page_description);
        desc.setText(this.description);

        ImageView contentView = (ImageView) view.findViewById(R.id.foundation_logo);
        contentView.setImageDrawable(logo.getDrawable());

        Button button = (Button) view.findViewById(R.id.donation_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DonationDialog donationDialog = new DonationDialog();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();

                donationDialog.show(activity.getSupportFragmentManager(),"dialog");
                System.out.println("button clicked donation");
            }
        });

        return view;
    }

}
