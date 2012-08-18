package com.hanghang.codestore.util;

import org.apache.log4j.Logger;

public class Log {
	
	private static Logger log = Logger.getLogger("log");
	
	private static Logger err = Logger.getLogger("err");
	
	private static Logger prt = Logger.getLogger("prt");
	
	/**
	 * ��ӡinfo��Ϣ������̨
	 * @param message
	 */
	public static void info(String message) {
		log.info(message==null ? null : message.toString());
	}
	
	/**
	 * ��ӡobject������̨
	 * @param message
	 */
	public static void print(Object obj) {
		prt.info(obj==null ? null : obj.toString());
	}
	
	/**
	 * ��ӡdebug��Ϣ������̨
	 * @param message
	 */
	public static void debug(String message) {
		log.debug(message);
	}
	
	/**
	 * ��ӡdebug��Ϣ������̨
	 * @param message
	 */
	public static void debug(String message, Throwable t) {
		log.debug(message, t);
	}
	
	/**
	 * ��¼warn��Ϣ��log�ļ�
	 * @param message
	 */
	public static void warn(String message) {
		err.warn(message);
	}
	
	/**
	 * ��¼error��Ϣ��log�ļ�
	 * @param message
	 */
	public static void error(String message) {
		err.error(message);
	}
	
	/**
	 * ��¼error��Ϣ��log�ļ�
	 * @param message
	 */
	public static void error(String message, Throwable t) {
		err.error(message, t);
	}
}
