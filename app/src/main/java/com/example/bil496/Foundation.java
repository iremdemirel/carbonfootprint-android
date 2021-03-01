package com.example.bil496;

import java.util.ArrayList;

public class Foundation {
    private String name;
    private String url;
    private ArrayList<FoundationNews> bulletin;
    private double totalDonation;

    public Foundation(){
        name = "";
        url = "";
        bulletin = new ArrayList<>();
        totalDonation = 0;
    }

    public Foundation(String name, String url){
        name = "";
        url = "";
        bulletin = new ArrayList<>();
        totalDonation = 0;
    }

    public void addNews(FoundationNews news){
        bulletin.add(news);
    }

    public ArrayList<FoundationNews> getBulletin(){
        return bulletin;
    }

    public double getTotalDonation() {
        return totalDonation;
    }
}
