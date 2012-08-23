package com.hanghang.codestore.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hanghang.codestore.util.print.Printer;

public class JsonUtil {
	
	public static String toJson(Object obj) {
		return JSON.toJSON(obj).toString();
	}
	
	public static Object[] parseArray(String json) {
		return JSON.parseArray(json).toArray();
	}
	
	public static <T> T[] parseArray(String json, Class<T> claz) {
		JSON.parseArray(json).toArray();
		
		List<T> list = JSON.parseArray(json, claz);
		list.toArray(null);
		
		
		System.out.println(Arrays.toString(JSON.parseArray(json).toArray()));
		return null;
	}
	
	
	
	public static void main(String[] args) {
		List<String> list = null;
		Printer.print(parseArray("[1,5,3]"));
	}

}
