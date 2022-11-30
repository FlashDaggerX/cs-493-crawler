package com.github.csec;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class App {
  // Define the user agent for this scraper, which is a mobile device using Chrome
  private static final String USER_AGENT =
      "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";

  public static void main(String[] args) {
    Document soup = null;
    final String url = "https://footlocker.com/search";
    // Attempt to get the document and error otherwise.
    try {
      soup = Jsoup.connect(url).userAgent(USER_AGENT).data("query", "blue").get();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Get the header tags and print out the inner text on each one
    soup.select("div[class=ProductCard hasVariants]").forEach((e) -> {
      String shoeName = e.selectFirst("span[class=ProductSale-primary]").text();
      String price = "";
      try {
        Element sale = e.expectFirst(url)
      } catch (IllegalArgumentException e) {
        
      }
      String priceOriginal = e.selectFirst("span[class=ProductPrice]").firstElementChild().text();
      String priceFinal = e.selectFirst("span[class=ProductPrice]").firstElementChild().text();
      
      System.out.println(shoeName + " " + price);
    });
    
    
  }
}
