package com.github.csec;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class App {
	// Define the user agent for this scraper, which is a mobile device using Chrome
	private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";

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
			String shoeName = e.selectFirst("span[class=ProductName-primary]").text();

			try {
				// If there's a sale on the item, execute this block
				Element sale = e.expectFirst("span[class=ProductPrice-sale]");
				String priceOriginal = sale.selectFirst("span[class=ProductPrice-original]").text();
				String priceFinal = sale.selectFirst("span[class=ProductPrice-final]").text();
				System.out.println(String.format("%-50s %15s (was %s)", shoeName, priceFinal, priceOriginal));
			} catch (IllegalArgumentException ex) {
				// If an exception is thrown from `expectFirst`, use this to get the price
				// instead.
				String price = e.selectFirst("span[class=ProductPrice]").firstElementChild().text();
				System.out.println(String.format("%-50s %15s", shoeName, price));
			}
		});
	}
}
