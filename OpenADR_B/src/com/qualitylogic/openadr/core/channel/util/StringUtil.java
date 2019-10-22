package com.qualitylogic.openadr.core.channel.util;

public final class StringUtil {
	private StringUtil() {
	}
	
	public static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static String join(String[] array, char separator) {
		StringBuilder builder = new StringBuilder();
		for (String item : array) {
			if (builder.length() > 0) {
				builder.append(separator);
			}
			
			builder.append(item);
		}
		return builder.toString();
	}
}
