package com.example.bil496.foundations;

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
        name = name;
        url = url;
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

    @Override
    public String toString() {
        return "Foundation{" +
                "bulletin=" + bulletin +
                ", totalDonation=" + totalDonation +
                '}';
    }
}
