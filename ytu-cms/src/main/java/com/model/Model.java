package com.model;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	public static String generateRandomNumber(int length) {
		String numerals = "0123456789abcdefghijklmnopqrstuvwxyz";
		String response = "";
		for (int i = 0; i < length; i++) {
			response += numerals.charAt((int) (Math.random() * numerals.length()));
		}
		return response + (Instant.now().toEpochMilli() % 1000000000);
	}

	public static String getSHA(String input) { // NOT THREAD SAFE
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

			BigInteger number = new BigInteger(1, hash);
			StringBuilder hexString = new StringBuilder(number.toString(16));

			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}

			return hexString.toString();
		}catch(NoSuchAlgorithmException e) {
			System.out.println("getSHA = "+e);
			return input;
		}
	
	}
}
