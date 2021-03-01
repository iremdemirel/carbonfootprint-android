package com.example.bil496;

import java.util.Date;

public class FoundationNews {
    private String title;
    private String content;
    private Date scrapeDate;
    //add publishDate in the future

    public FoundationNews(String title, String content, Date scrapeDate){
        this.title = title;
        this.content = content;
        this.scrapeDate = scrapeDate;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    //default constructor
    public FoundationNews(){

    }

}
