package com.hanghang.codestore.util;

public class Lang {
	
	public static <T> T[] array(T... ts){
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
