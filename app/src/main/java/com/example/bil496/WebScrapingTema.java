package com.example.bil496;

import android.os.Bundle;
import android.os.PersistableBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebScrapingTema {
    public static void scrape () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String baseUrl = "https://www.tema.org.tr";
                    Document doc = Jsoup.connect(baseUrl+"/basin-odasi/basin-bultenleri").get();
                    String title = doc.title();

                    //   System.out.printf("Title: " + title);

                    ArrayList<String> newsList = new ArrayList<>();
                    for (Element row : doc.select("div.news a")){
                        String newsUrl = row.attributes().get("href");
                        Document news = Jsoup.connect(baseUrl + newsUrl).get();
                        String newsTitle = news.select("h1.title.font").text();
                        Elements text = news.select("div.font");
                        for (Element e : text.get(0).getAllElements()){
                            if(e.text().length()>20){ //if text length is less then 20, skip it.
                                                        // It is probably title or just one sentence.
                                System.out.println(e.text());
                            }
                        }
                    }
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();


    }

}
