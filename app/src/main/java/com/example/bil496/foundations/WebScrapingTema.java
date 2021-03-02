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
import java.util.ArrayList;
import java.util.Date;

public class WebScrapingTema {
    static String baseUrl = "https://www.tema.org.tr";
    static Foundation foundation = new Foundation("TEMA",baseUrl);
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static void scrape () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.connect(baseUrl+"/basin-odasi/basin-bultenleri").get();
                    String title = doc.title();

                    for (Element row : doc.select("div.news a")){
                        String newsUrl = row.attributes().get("href");
                        Document news = Jsoup.connect(baseUrl + newsUrl).get();
                        String newsTitle = news.select("h1.title.font").text();
                        System.out.println("****Scraping: " + newsTitle);

                        Elements text = news.select("div.font");
                        String content = "";
                        for (Element e : text.get(0).getAllElements()){
                            if(e.text().length()>15){ //if text length is less then 15, skip it.
                                                        // It is probably title or just one sentence.
                                content += e.text() +" ";
                            }
                        }
                        FoundationNews newsObj = new FoundationNews(newsTitle, content, new Date());
                        foundation.addNews(newsObj);
                    }
                    System.out.println("**Scraping is done");

                    DatabaseReference dbRef = database.getReference("Foundations/Tema");
                    dbRef.setValue(foundation);
/* to see and check the content, for logs
                    ArrayList<FoundationNews> finalbulletin = foundation.getBulletin();
                    for(int i = 0; i<finalbulletin.size(); i++){
                        System.out.println(finalbulletin.get(i).getContent());
                    }

 */
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();


    }

}