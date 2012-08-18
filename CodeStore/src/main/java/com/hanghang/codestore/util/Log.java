package com.hanghang.codestore.util;

import org.apache.log4j.Logger;

public class Log {
	
	private static Logger log = Logger.getLogger("log");
	
	private static Logger err = Logger.getLogger("err");
	
	private static Logger prt = Logger.getLogger("prt");
	
	/**
	 * 打印info信息到控制台
	 * @param message
	 */
	public static void info(String message) {
		log.info(message==null ? null : message.toString());
	}
	
	/**
	 * 打印object到控制台
	 * @param message
	 */
	public static void print(Object obj) {
		prt.info(obj==null ? null : obj.toString());
	}
	
	/**
	 * 打印debug信息到控制台
	 * @param message
	 */
	public static void debug(String message) {
		log.debug(message);
	}
	
	/**
	 * 打印debug信息到控制台
	 * @param message
	 */
	public static void debug(String message, Throwable t) {
		log.debug(message, t);
	}
	
	/**
	 * 记录warn信息到log文件
	 * @param message
	 */
	public static void warn(String message) {
		err.warn(message);
	}
	
	/**
	 * 记录error信息到log文件
	 * @param message
	 */
	public static void error(String message) {
		err.error(message);
	}
	
	/**
	 * 记录error信息到log文件
	 * @param message
	 */
	public static void error(String message, Throwable t) {
		err.error(message, t);
	}
}
