package com.example.intern_bot.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;

@Component
public class Scheduler {

    private final Scraper scraper;
    private final DiscordService bot;
    private final Cache cache;

    @Value("${Channel}")
    private String channelId;

    public Scheduler(Scraper scraper, DiscordService bot, Cache cache) {
        this.scraper = scraper;
        this.bot = bot;
        this.cache = cache;
    }

    @Scheduled(fixedRateString = 3600)
    public void run() {
        List<Job> jobs = scraper.fetchJobs();
        jobs.sort(Comparator.comparing(Job::getPostedDate).reversed());

        for (Job job : jobs) {
            if (cache.isNew(job.getUrl())) {
                bot.sendMessage(channelId, job.getTitle() + " - " + job.getCompany() + " - " + job.getUrl());
            }
        }
    }
}
