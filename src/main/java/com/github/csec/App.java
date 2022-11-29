package com.github.csec;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App {
    // Define the user agent for this scraper, which is a mobile device using Chrome
    private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";

    public static void main(String[] args) {
        Document soup = null;

        // Attempt to get the document and error otherwise.
        try {
            soup = Jsoup.connect("https://footlocker.com").userAgent(USER_AGENT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the header tags and print out the inner text on each one
        soup.select("h1").forEach((e) -> System.out.println(e.text()));
    }
}
