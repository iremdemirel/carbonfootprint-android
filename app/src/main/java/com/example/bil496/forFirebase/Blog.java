package com.example.bil496.forFirebase;

public class Blog {
    private BlogText blogtext;
    private String date;

    public Blog() {
    }

    public Blog(BlogText blogtext, String date) {
        this.blogtext = blogtext;
        this.date = date;
    }

    public BlogText getBlogtext() {
        return blogtext;
    }

    public void setBlogtext(BlogText blogtext) {
        this.blogtext = blogtext;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
