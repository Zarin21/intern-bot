package com.example.intern_bot.app;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordService {
    JDA jda;

    @Value("${DiscordToken}")
    String token;

    @PostConstruct
    void init() {
        this.jda = JDABuilder
                    .createDefault(token)
                    .build();
        jda.awaitReady();
    }

    public void sendMessage(String channelId, String message) {
        jda.getTextChannelById(channelId)
           .sendMessage(message)
           .queue();
    }
}
