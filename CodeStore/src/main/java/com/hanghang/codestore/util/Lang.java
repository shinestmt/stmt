package com.hanghang.codestore.util;

import java.lang.reflect.Array;

public class Lang {
	
	public static <T> T[] array(T... ts){
		return ts;
	}
	
	public static <T> T[] megraArray(T[] arrays1, T[] arrays2) throws Exception {
		if(arrays1 == null || arrays1.length == 0){
			handException("Empty Array : arrays1");
		}
		if(arrays2 == null || arrays2.length == 0){
			handException("Empty Array : arrays2");
		}
		int len1 = arrays1.length, len2 = arrays2.length;
		
		T[] ts = (T[])Array.newInstance(arrays1[0].getClass(), len1 + len2);
		System.arraycopy(arrays1, 0, ts, 0, len1);
		System.arraycopy(arrays2, 0, ts, len1, len2);
		return ts;
	}
	
	public static <T> T[] getArray(T[] arrays, int start) throws Exception {
		return getArray(arrays, start, arrays.length);
	}
	
	public static <T> T[] getArray(T[] arrays, int start, int end) throws Exception {
		if(arrays == null || arrays.length == 0){
			handException("Empty Array : arrays");
		}
		T[] ts = (T[])Array.newInstance(arrays[0].getClass(), end-start);
		System.arraycopy(arrays, start, ts, 0, end-start);
		return ts;
	}

	public static Exception handException(String message) {
		return new Exception(message);
	}
	
	public static Exception handException(String exp, String... args) {
		String message = null;
		try {
			message = String.format(exp, (Object[])args);
		} catch (Exception e) {
			
		}
		return handException(message);
	}
	
	public static Exception handException(Throwable e, String message) {
		return new Exception(message, e);
	}
	
	public static Exception handException(Throwable e, String exp, String... args) {
		String message = null;
		try {
			message = String.format(exp, (Object[])args);
			
		} catch (Exception ex) {
			
		}
		return handException(e, message);
	}
	
}
