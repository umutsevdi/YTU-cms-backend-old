package com.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

public class Model {

	public static boolean isURL(String link) {
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException malformedURLException) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;

	}

	public static String generatePublicId(int length) {
		String numerals = "0123456789abcdefghijklmnopqrstuvwxyz";
		String response = "";
		for (int i = 0; i < length; i++) {
			response += numerals.charAt((int) (Math.random() * numerals.length()));
		}
		return response + (Instant.now().toEpochMilli() % 1000000000);
	}

}
