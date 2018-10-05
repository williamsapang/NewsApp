package com.example.samsophias.newsapp;

public class MyDataNews {
    private String title;
    private String type;
    private String date;
    private String section;
    private String url;

    public MyDataNews(String title, String type, String date, String section, String url) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.section = section;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}