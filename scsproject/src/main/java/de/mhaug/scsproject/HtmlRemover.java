package de.mhaug.scsproject;

import org.jsoup.Jsoup;

/**
 * Utility that creates plain text from html code. Needed because the static
 * methods from Jsoup cant be used in the templates directly.
 */
public class HtmlRemover {
	public String extractPlainText(String html) {
		return Jsoup.parse(html).text();
	}
}
