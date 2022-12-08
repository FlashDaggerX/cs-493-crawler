package com.github.csec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/** SAFETY: Must only be single-threaded. */
public class ShoeCSV {
	public static final String CSV_HEADER = "URL,Title,Fit,Original Price,Sale Price,Colors,Sizes";

	public static ShoeCSV openCSVHandle(String name) {
		if (!name.endsWith(".csv")) {
			name = name.concat(".csv");
		}

		File csvFile = new File(name);
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(csvFile);

			if (!csvFile.createNewFile()) {
				writer.println(CSV_HEADER);
			}
		} catch (IOException e) {
			System.err.printf("Couldn't open the result file: \"%s\": %s",
					name, e.getMessage());
			System.exit(7);
		}

		return new ShoeCSV(writer);
	}

	private PrintWriter writer;

	private ShoeCSV(PrintWriter writer) {
		this.writer = writer;
	}

	public void write(Shoe shoe) {
		String colors = String.join("/", shoe.colors);
		String sizes = String.join("/", shoe.sizes);
		this.writer.printf("%s,%s,%s,%.2f,%.2f,%s,%s\n",
				shoe.url, shoe.title, shoe.alt,
				shoe.originalPrice, shoe.salePrice,
				colors, sizes);
	}

	public void close() {
		this.writer.close();
	}
}
