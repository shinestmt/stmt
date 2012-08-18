package com.hanghang.codestore.util.operation;

import java.util.Arrays;
import java.util.MissingFormatArgumentException;

public class Operation {
	
	private static ExpressionParser parser = new ExpressionParser();
	
	/**
	 * ���ݱ��ʽ�Ͳ�����ֵ
	 * @param exp
	 * @param strs
	 * @return
	 * @throws Exception
	 */
	public static String evaluate(String exp, Object... strs) throws Exception {
		try {
			return parser.evaluate(String.format(exp, strs).replaceAll("mod", "%"));
		} catch (MissingFormatArgumentException e) {
			throw new Exception(String.format("����%s���ڱ��ʽ %s ������", Arrays.toString(strs), exp));
		} catch (Exception e) {
			throw new Exception(String.format("���ʽ %s ����", exp));
		} 
	}
	
	protected static String format(String exp, Object... strs){
		StringBuffer result = new StringBuffer(exp);
		for (int i = 0; i < strs.length; i++) {
			result.delete(0, result.length()).append(result.toString().replaceFirst("%s", (String)strs[i]));
		}
		System.out.println(result);
		return result.toString();
	}
	
	
	
	
	
	
	
	
	
}
