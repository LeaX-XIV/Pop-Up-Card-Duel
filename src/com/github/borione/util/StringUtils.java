package com.github.borione.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author LeaX_XIV
 *
 */
public class StringUtils {
	
	/**
	 * Returns the string with capital letters.<br>
	 * <br>
	 * Example<br>
	 * str = "Hello, world!"<br>
	 * return  = "HELLO, WORLD!"
	 * @param str A string
	 * @return The string with capital letters.
	 */
	public static String toUpper(String str) {
		return str.toUpperCase();
	}
	
	/**
	 * Returns the string with small letters.<br>
	 * <br>
	 * Example<br>
	 * str = "Hello, World!"<br>
	 * return  = "hello, world!"
	 * @param str A string
	 * @return The string with small letters.
	 */
	public static String toLower(String str) {
		return str.toLowerCase();
	}
	
	/**
	 * Crowns the string in <code>html</code> tags, and replaces breaklines with <code>br</code> tags.
	 * @param str The string to be <code>HTML</code>ized.
	 * @return The string in <code>HTML</code> format.
	 */
	public static String toHTML(String str) {
		return "<html>".concat(str.replace(System.getProperty("line.separator"), "<br>").concat("</html>"));
	}
	
	/**
	 * Checks if the string can be parsed as a number.
	 * @param str The string to be checked.
	 * @return <code>true</code> if the argument is a number, <code>false</code> otherwise.
	 */
	public static boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the string is the value of a boolean.
	 * @param str The string to be checked.
	 * @return <code>true</code> if the string equals <code>"true"</code> or <code>"false"</code>, <code>false</code> otherwise.
	 */
	public static boolean isBoolean(String str) {
		if(str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
	
	/**
	 * If the argument can be casted as a boolean, it returns the boolean.
	 * @param str The argument to be casted.
	 * @return code>true</code> if the string equals <code>"true"</code>, <code>false</code> otherwise.
	 * @throws IllegalArgumentException When the string is not a boolean.
	 * @see #isBoolean(String)
	 */
	public static boolean toBoolean(String str) throws IllegalArgumentException {
		if(isBoolean(str)) {
			return str.equalsIgnoreCase("true");
		}
		else {
			throw new IllegalArgumentException(str + " is not a boolean.");
		}
	}
	
	/**
	 * Extract the message digest from a string with the MD5 algorithm.
	 * @param str The string from which get the digest.
	 * @return A string indicating the digest (128 bits - 32 chars)
	 * @throws RuntimeException Why should this even be thrown?
	 */
	public static String toMD5(String str) throws RuntimeException {
		byte[] bytesStr;
		try {
			bytesStr = str.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytesDigest = md.digest(bytesStr);
			return ByteUtils.toHexString(bytesDigest);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
		}

		throw new RuntimeException("Couldn't extract digest from " + str + ".");
	}

}
