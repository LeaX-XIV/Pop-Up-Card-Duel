package com.github.borione.util;

public class ByteUtils {
	
	/**
	 * Transforms an array of bytes in a hexadecimal string.<br>
	 * Useful with hash.
	 * @param arg The array of bytes to convert in string.
	 * @return The string representing the given array.
	 * @see StringUtils#toMD5(String)
	 */
	public static String toHexString(byte... arg) {
		StringBuilder sb = new StringBuilder(arg.length);
		for (byte b : arg) {
			sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}
