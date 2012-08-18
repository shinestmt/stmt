package com.hanghang.codestore.util.operation;

import java.util.Arrays;
import java.util.MissingFormatArgumentException;

public class Operation {
	
	private static ExpressionParser parser = new ExpressionParser();
	
	/**
	 * 根据表达式和参数求值
	 * @param exp
	 * @param strs
	 * @return
	 * @throws Exception
	 */
	public static String evaluate(String exp, Object... strs) throws Exception {
		try {
			return parser.evaluate(String.format(exp, strs).replaceAll("mod", "%"));
		} catch (MissingFormatArgumentException e) {
			throw new Exception(String.format("参数%s对于表达式 %s 不适用", Arrays.toString(strs), exp));
		} catch (Exception e) {
			throw new Exception(String.format("表达式 %s 错误", exp));
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
