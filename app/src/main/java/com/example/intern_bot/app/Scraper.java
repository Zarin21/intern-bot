package com.example.intern_bot.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Scraper {

    @Value("${app.query}")
    private String query;

    @Value("${app.location}")
    private String location;

    public List<Job> fetchJobs() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(), new Date());
        return jobs;
    }
}
