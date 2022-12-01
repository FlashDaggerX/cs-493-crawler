package com.github.csec;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class App {
	// Define the user agent for this scraper, which is a mobile device using Chrome
	private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";
	private static final String URL = "https://footlocker.com/search";

	public static void main(String[] args) {
		String query = parseQueryArgument(args);

		Document soup = makeQuery(query);

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

	public static String parseQueryArgument(String[] args) {
		try {
			return args[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Specify a search query for shoes!");
			System.exit(1);
		}
		return null;
	}

	/** Attempt to get the document and error otherwise. */
	public static Document makeQuery(String query) {
		try {
			return Jsoup.connect(URL).userAgent(USER_AGENT).data("query", query).get();
		} catch (IOException e) {
			System.err.println("Couldn't connect to Footlocker: " + e.getMessage());
			System.exit(2);
		}
		return null;
	}
}
