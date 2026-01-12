package com.spanprints.authservice.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicUtils {

	private BasicUtils() {
	}

	public static String maskEmail(String email) {
		String[] split = email.split("@");
		String part1 = split[0];
		return part1.substring(0, 3) + "****@" + split[1];
	}

	public static boolean isValidEmail(String email) {
		final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static Instant convertLocalDateToInstant(LocalDate localDate) {
		return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
	}

	public static String formatStringToTitle(String input) {
		if (input == null || input.trim().isEmpty()) {
			return "";
		}
		String[] words = input.trim().split("\\s+");
		StringBuilder result = new StringBuilder();
		for (String word : words) {
			if (!word.isEmpty()) {
				result.append(Character.toUpperCase(word.charAt(0)));

				if (word.length() > 1) {
					result.append(word.substring(1).toLowerCase());
				}

				result.append(" ");
			}
		}
		// Remove trailing space
		return result.toString().trim();
	}

	public static Integer parserStringToInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return -1;
	}

}
