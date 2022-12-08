package com.github.csec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Shoelist implements Iterable<Element> {
	private Document shoelist;

	public Shoelist(String url) {
		try {
			this.shoelist = Jsoup.connect(url).userAgent(App.USER_AGENT).get();
		} catch (IOException e) {
			System.err.printf("Couldn't get shoes for \"%s\": %s\n",
					url, e.getMessage());
			System.exit(4);
		}
	}

	public ArrayList<Shoe> shoes(long delayMillis) {
		ArrayList<Shoe> shoes = new ArrayList<>();
		for (Element e : this) {
			shoes.add(new Shoe(App.FOOTLOCKER_URL + e.attr("href")));

			// Delay querying each shoe page.
			try {
				Thread.sleep(delayMillis);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return shoes;
	}

	public ArrayList<Shoe> shoes() {
		ArrayList<Shoe> shoes = new ArrayList<>();
		for (Element e : this) {
			shoes.add(new Shoe(App.FOOTLOCKER_URL + e.attr("href")));
		}
		return shoes;
	}

	@Override
	public Iterator<Element> iterator() {
		return shoelist
				.select("a[class=ProductCard-link ProductCard-content]")
				.iterator();
	}
}
