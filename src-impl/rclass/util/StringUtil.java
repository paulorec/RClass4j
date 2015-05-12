package rclass.util;

import java.util.Arrays;
import java.util.regex.Pattern;

public class StringUtil {

	static final String keywords[] = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
			"class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally",
			"float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
			"new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
			"super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void",
			"volatile", "while" };

	public static boolean isJavaKeyword(String keyword) {
		return (Arrays.binarySearch(keywords, keyword) >= 0);
	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public static String getJavaSafeKey(String key) {

		if (Character.isDigit(key.charAt(0)))
			key = "_" + key;

		if (StringUtil.isJavaKeyword(key))
			key = key + "_";

		return key.replaceAll("\\W", "_").toUpperCase();
	}

	public static String unicodeEscaped(String targetString) {
		
		StringBuilder sb = new StringBuilder();
		
		for(int k =0;k<targetString.length();k++) {
			
			sb.append(unicodeEscaped(targetString.charAt(k)));
		}
		
		return sb.toString();
	}
	public static String unicodeEscaped(char ch) {
		
		if(Character.isWhitespace(ch))
			return String.valueOf(ch);
		
		if(ch >= 65 && ch <= 122)
			return String.valueOf(ch);
		
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}
}
 