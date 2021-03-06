package com.example.bil496.forFirebase;

public class BlogText {
    private String header;
    private String text;

    public BlogText() {
    }

    public BlogText(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
