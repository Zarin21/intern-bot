package com.example.intern_bot.app;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordService {
    JDA jda;

    @Value("${discord.token}")
    String token;

    @PostConstruct
    void init() {
        try {
            this.jda = JDABuilder.createDefault(token).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String channelId, String message) {
        if (jda != null) {
            jda.getTextChannelById(channelId)
               .sendMessage(message)
               .queue();
        }
    }
}