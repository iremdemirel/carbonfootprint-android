package com.example.bil496.forFirebase;

import java.util.HashMap;

public class Users {
    private String name;
    private String email;
    private String photoURL;
    private String bio;
    private HashMap<String, Float> carbon;
    //private ArrayList<Blog> blog;


    public Users(String name, String email, String photoURL, String bio, HashMap<String, Float> carbon) {
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;
        this.bio = bio;
        this.carbon = carbon;
    }

    public Users() {
    }

    public HashMap<String, Float> getCarbon() {
        return carbon;
    }

    public void setCarbon(HashMap<String, Float> carbon) {
        this.carbon = carbon;
    }

    public Users(String name, String email, String photoURL) {
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public ArrayList<Blog> getBlog() {
        return blog;
    }

    public void setBlog(ArrayList<Blog> blog) {
        this.blog = blog;
    }*/
}
