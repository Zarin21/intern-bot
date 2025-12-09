package com.example.intern_bot.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class Job {
    private String title;
    private String company;
    private String url;
    private Date postedDate;
}