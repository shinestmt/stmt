package com.hanghang.codestore.util.operation;

import com.hanghang.codestore.util.StringUtil;

public class Test {
	
	public static void main(String[] args) throws Exception {
		String result = Operation.evaluate("(%s+%s) * %s", "30", "87", "9");
		System.out.println(result);
		
		result = StringUtil.format("({0}+{1}) * {1}", "11");
		System.out.println(result);
	}
}
