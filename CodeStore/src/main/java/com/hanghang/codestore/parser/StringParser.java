package com.hanghang.codestore.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Test;

public class StringParser {
	private String str;
	
	private String tokenIndex; 
	/** 解析器当前处理的标记类型 */
	private int tokenType; 
	
	private static Map<String, String> cst = new HashMap<String, String>();
	static {
		cst.put("year", "2011");
		cst.put("month", "10");
		cst.put("day", "24");
	}
	
	@Test
	public void testParser() {
		String path = "parser.txt";
		try {
			parser(new File(path));
			System.out.println(this.str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parser(String str){
		System.out.println(str);
		this.str = str;
		parser();
	}
	
	
	public void parser(){
		System.out.println(str);
		int start = 0, end = 0;
		for (int i = 0; i < str.length(); ) {
			char c = str.charAt(i);
			if(c == '{'){
				start = i;
			}
			if(c == '}'){
				end = i+1;
				String exp = str.substring(start, end);
				String value = parserExp(exp);
				str = str.replace(exp, value);
				i = 0;
			}
			i++;
		}
	}
	
	private String parserExp(String exp){
		String context = exp.substring(1, exp.length()-1);
		StringTokenizer token = new StringTokenizer(context, "[\\+]|[\\-]", true);
		while (token.hasMoreTokens()) {
			String tmp = token.nextToken();
			if(cst.containsKey(tmp)){
				context = context.replaceFirst(tmp, cst.get(tmp));
			}
		}
		return context;
	}
	
	public void parser(InputStream in) throws Exception {
		StringBuilder str = new StringBuilder();
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((line=reader.readLine()) != null) {
			str.append(line).append(" ");
		}
		parser(str.toString());
	}
	
	public void parser(File file) throws Exception {
		parser(new FileInputStream(file));
	}
}
