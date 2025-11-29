package com.example.intern_bot.app;

import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class Cache {
    private final Set<Integer> cache = new HashSet<>();

    public boolean isNew(String url) {
        return cache.add(hash);
    }
}
