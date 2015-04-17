package com.jbrown.webutils;

import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.web.util.WebUtils;

public class WebUtilsDemo {

	public static void main(String[] args) {
		System.out.println(imageSearch("Java"));
	}

	private static JSONObject imageSearch(String phrase) {
		System.out.println("Searching Google Image for \"" + phrase + "\"");
		try {
			// Convert spaces to +, etc. to make a valid URL
			String uePhrase = URLEncoder.encode("\"" + phrase + "\"", "UTF-8");
			String urlStr = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0"
					+ "&q=" + uePhrase + "&rsz=large" + // at most 8 results
					"&imgtype=photo" + // want photos
					"&imgc=color" + // in colour
					"&safe=images" + // Use moderate filtering
					"&imgsz=l"; // request large images

			String jsonStr = JBrownWebUtils.webGetString(urlStr);
			// get response as a JSON string
			return new JSONObject(jsonStr);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		return null;
	} // end of imageSearch()
}
