package com.github.csec;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Sitemap implements Iterable<Element> {
	private static final String SITEMAP_SHOES = App.FOOTLOCKER_URL + "sitemap.shoes.xml";

	private Document sitemap;

	public Sitemap() {
		try {
			this.sitemap = Jsoup.connect(SITEMAP_SHOES).userAgent(App.USER_AGENT).get();
		} catch (IOException e) {
			System.err.printf("Couldn't get sitemap \"%s\": %s\n",
					SITEMAP_SHOES, e.getMessage());
			System.exit(3);
		}
	}

	public Shoelist findBrand(String brand) throws IllegalArgumentException {
		for (Element e : this) {
			String link = e.text();
			if (link.contains(brand)) {
				return new Shoelist(link);
			}
		}
		throw new IllegalArgumentException("No brands exist.");
	}

	@Override
	public Iterator<Element> iterator() {
		return sitemap
				.select("url")
				.select("loc")
				.iterator();
	}
}
