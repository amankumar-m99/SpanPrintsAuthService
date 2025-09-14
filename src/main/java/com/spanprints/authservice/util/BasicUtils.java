package com.spanprints.authservice.util;

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

}
