package com.example.bil496.foundations;

import com.example.bil496.foundations.Foundation;
import com.example.bil496.foundations.FoundationNews;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class WebScrapingGreenPeace {
    static String baseUrl = "https://www.greenpeace.org/turkey";
    static Foundation foundation = new Foundation("Greenpeace",baseUrl);
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static void scrape () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.connect(baseUrl+"/basin-bultenleri/").get();

                    Elements deneme = doc.select("div.row div.col-lg-8.multiple-search-result ul.list-unstyled").get(0).children();
                    for (int i = 1; i< deneme.size(); i++){
                        Element element = deneme.get(i);
                        Element info = element.children().get(1);
                        String url = info.children().get(1).attributes().get("href");

                        Document newsDoc = Jsoup.connect(url).get();
                        String newsTitle = newsDoc.title().substring(0,newsDoc.title().indexOf("-")); //to remove "Greenpeace Akdeniz Turkiye" string.
                        System.out.println("****Scraping: " + newsTitle);
                        Elements news = newsDoc.select("div.container div.post-content div.post-content-lead article.post-details.clearfix").get(0).children();
                        String content = news.text();
                        FoundationNews newsObj = new FoundationNews(newsTitle, content, new Date());
                        foundation.addNews(newsObj);
                    }
                    System.out.println("**Scraping is done");

                    DatabaseReference dbRef = database.getReference("Foundations/Greenpeace");
                    dbRef.setValue(foundation);


                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();


    }

}
