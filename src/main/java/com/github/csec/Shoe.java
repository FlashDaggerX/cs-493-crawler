package com.github.csec;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Shoe {
	public String title, alt, url;
	public double salePrice, originalPrice;
	public String[] colors;
	public List<String> sizes;

	public Shoe(String url) {
		this.url = url;
		Element context = null;
		try {
			Document document = Jsoup.connect(url).userAgent(App.USER_AGENT).get();
			// The right sided column of a shoe info form.
			context = document.selectFirst("main");
		} catch (IOException e) {
			System.err.printf("Couldn't get shoe information \"%s\": %s\n",
					url, e.getMessage());
			System.exit(4);
		}

		try {
			Element titleContext = context.selectFirst("h1[id=pageTitle]");
			this.title = titleContext.selectFirst("span[class=ProductName-primary]").text();
			this.alt = titleContext.selectFirst("span[class=ProductName-alt]").text();
		} catch (NullPointerException e) {
			System.err.printf("There is no titleContext: \"%s\" %s\n",
					url, e.getMessage());
			System.exit(5);
		}

		// // Nested inside nine divs...
		// Element ratingContext = context
		// .selectFirst("div[id=BVRRSummaryContainer]")
		// .selectFirst("div[class=bv-summary-bar bv-summary-bar-minimalist
		// bv-summary-bar-minimalist-horizontal ]");

		try {
			Element formContext = context.selectFirst("form[id=ProductDetails]");

			try {
				// If there's a sale on the item, execute this block
				Element sale = formContext.expectFirst("span[class=ProductPrice-sale]");
				String priceOriginal = sale.selectFirst("span[class=ProductPrice-original]").text();
				String priceSale = sale.selectFirst("span[class=ProductPrice-final]").text();

				this.originalPrice = Double.parseDouble(priceOriginal.substring(1));
				this.salePrice = Double.parseDouble(priceSale.substring(1));
			} catch (IllegalArgumentException ex) {
				// If an exception is thrown from `expectFirst`, use this to get the price
				// instead.
				String price = formContext.selectFirst("span[class=ProductPrice]").firstElementChild().text();
				this.originalPrice = Double.parseDouble(price.substring(1));
				this.salePrice = -1;
			}

			this.colors = formContext.selectFirst("p[class=ProductDetails-form__selectedStyle]").text().split("/");
			this.sizes = formContext
					.selectFirst("div[id=tabPanel-undefined]")
					.select("button[class=Button SizeSelector-button-newDesign]")
					.select("span")
					.eachText();
		} catch (NullPointerException e) {
			System.err.printf("There is no formContext: \"%s\": %s\n",
					url, e.getMessage());
			System.exit(6);
		}
	}
}
