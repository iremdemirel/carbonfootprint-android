package com.example.bil496.foundations;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setScrapeDate(Date scrapeDate) {
        this.scrapeDate = scrapeDate;
    }

    //default constructor
    public FoundationNews(){

    }

    @Override
    public String toString() {
        return "FoundationNews{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
