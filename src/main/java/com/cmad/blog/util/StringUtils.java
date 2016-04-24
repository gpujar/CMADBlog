package com.cmad.blog.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class StringUtils {

	
	/**
	 * Check whether the given String is empty.
	 * 
	 */
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	
	/**
	 * Return the decoded string 
	 * @param authString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDecodedBase64(final String authString) throws UnsupportedEncodingException{
		String encoded = authString.substring(6);
		byte[] contentInBytes = Base64.decodeBase64(encoded);
		String keyValue= new String(contentInBytes,"UTF-8");
		System.out.println("StringUtils.getDecodedBase64()  keyvalue: " +keyValue);
		return keyValue.substring(6);	
	}
	
	
}
