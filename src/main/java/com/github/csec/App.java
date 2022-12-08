package com.github.csec;

public class App {
	// Define the user agent for this scraper, which is a mobile device using Chrome
	public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36";
	public static final String FOOTLOCKER_URL = "https://www.footlocker.com/";

	public static void main(String[] args) {
		String query = parseQueryArgument(args);

		ShoeCSV handle = ShoeCSV.openCSVHandle(query);
		new Sitemap().findBrand(query)
			.shoes(200)
			.forEach(handle::write);
		handle.close();
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
}
