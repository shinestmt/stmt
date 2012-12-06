package com.hanghang.codestore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	private static Logger log = LoggerFactory.getLogger("log");
	
	private static Logger err = LoggerFactory.getLogger("err");
	
	private static Logger prt = LoggerFactory.getLogger("prt");
	
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
		try {
			prt.info(obj.toString());
		} catch (NullPointerException e) {
			prt.info(null);
		}
		
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
