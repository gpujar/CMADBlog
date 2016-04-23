package com.cmad.blog.util;

public class StringUtils {

	
	/**
	 * Check whether the given String is empty.
	 * 
	 */
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
}
