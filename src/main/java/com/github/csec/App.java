package com.github.csec;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App {
	// Define the user agent for this scraper, which is a mobile device using Chrome
	private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";
	private static final String URL = "https://footlocker.com/search";

	public static void main(String[] args) {
		String query = parseQueryArgument(args);

		Document soup = makeQuery(query);

		// Get the header tags and print out the inner text on each one
		soup.select("span[class=ProductName-primary]").forEach((e) -> System.out.println(e.text()));
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
