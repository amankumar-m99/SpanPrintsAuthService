package com.spanprints.authservice.util;

public class BasicUtils {

	private BasicUtils() {}

	public static String maskEmail(String email) {
		String[] split = email.split("@");
		String part1 = split[0];
		return part1.substring(0, 3) + "* * * @" + split[1];
	}

}
