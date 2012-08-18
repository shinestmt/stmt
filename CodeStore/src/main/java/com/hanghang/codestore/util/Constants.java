package com.hanghang.codestore.util;

import java.io.File;

public class Constants {

	public static String userDir = System.getProperty("user.dir");
	
	public static String getRootPath() {
		
		return getPath(Constants.class, "/");
	}
	
	public static String getPath(String path) {
		return getRootPath() + path;
	}
	
	public static String getPath(Class claz) {
		return claz.getResource("").getPath();
	}
	
	public static String getPath(Class claz, String fileName) {
		return getPath(claz) + fileName;
	}
	
	public static File getDirectory(Class claz) {
		return new File(getPath(claz));
	}
	
	public static File getFile(Class claz, String fileName) {
		return new File(getPath(claz, fileName));
	}
	
	public static void main(String[] args) {
		
		System.out.println(Constants.getPath(SQL.class));
		System.out.println(Constants.getDirectory(SQL.class));
		
	}
	
}
