package com.example.intern_bot.app;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scraper {

    @Value("${app.query}")
    private String query;

    @Value("${app.location}")
    private String location;

    private final OkHttpClient client = new OkHttpClient();

    public List<Job> fetchJobs() {
        List<Job> jobs = new ArrayList<>();
        
        for (int offset = 0; offset < 100; offset += 25) {
            String url = String.format("https://www.linkedin.com/jobs/search?keywords=%s&location=%s&start=%d", 
                    query, location, offset);

            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String html = response.body().string();
                    Document doc = Jsoup.parse(html);
                    
                    Elements jobCards = doc.select("div.base-card");

                    for (Element card : jobCards) {
                        try {
                            String title = card.select("h3.base-search-card__title").text();
                            String company = card.select("h4.base-search-card__subtitle").text();
                            String jobUrl = card.select("a.base-card__full-link").attr("href");
                            
                            if (jobUrl.contains("?")) {
                                jobUrl = jobUrl.substring(0, jobUrl.indexOf("?"));
                            }

                            String dateStr = card.select("time").attr("datetime");
                            Date postedDate;
                            if (!dateStr.isEmpty()) {
                                postedDate = Date.valueOf(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            } else {
                                postedDate = new Date(System.currentTimeMillis());
                            }

                            jobs.add(new Job(title, company, jobUrl, postedDate));
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    Thread.sleep(2000); 
                } 
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jobs;
    }
}