package com.hanghang.codestore.util.operation;

import java.util.List;
import java.util.ArrayList;

public class MyCalc {
	public static void main(String[] args) {
		String expr = "1+3*(5-(4-3))+5";
		List<String> items = parseExpr(expr);
		print("Init",items);
		Object[] obj = items.toArray();
		System.out.println(obj);
		System.out.println("result:"+calcSubExpr(items));
	}
	
	//caculate every sub expresion
	private static double calcSubExpr(List<String> items){
		print("Current items",items);
		if(items.contains("(")){
			int from=items.indexOf("(");
			int to=items.lastIndexOf(")");
			List<String> subList=items.subList(from+1, to);
			double result=calcSubExpr(subList);
			remove(items,from-1,from+3);
			items.add(from-1, ""+result);
		}	
		doMultiplyAndDivide(items);
		doPlusAndNegate(items);
		return Double.parseDouble(items.remove(0));
	}
	
	//remove item from items[from,to]
	private static void remove(List<String> items, int from, int to) {
		// TODO Auto-generated method stub
		for(int i=from;i<to;i++){
			items.remove(from);
		}
	}
	
	//do plus or Negate 
	private static void doPlusAndNegate(List<String> items) {
		// TODO Auto-generated method stub
		print("Before Plus/Negate",items);
		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);
			if (item.equals("+") || item.equals("-")) {
				double last = Double.parseDouble(items.remove(i - 1));
				items.remove(i - 1);
				double next = Double.parseDouble(items.remove(i - 1));
				double result = calc(last, next, item);
				items.add(i - 1, "" + result);
				i = i - 1;
			}
		}
		print("After Plus/Negate",items);
	}
	//do muilty or devide
	private static void doMultiplyAndDivide(List<String> items) {
		// TODO Auto-generated method stub
		print("Before Multiply/Divide",items);
		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);
			if (item.equals("*") || item.equals("/")) {
				double last = Double.parseDouble(items.remove(i - 1));
				items.remove(i - 1);
				double next = Double.parseDouble(items.remove(i - 1));
				double result = calc(last, next, item);
				items.add(i - 1, "" + result);
				i = i - 1;
			}
		}
		print("After Multiply/Divide",items);
	}
	private static double calc(double last, double next, String c) {
		// TODO Auto-generated method stub
		System.out.println("Calc() parameters:" + last + " " + next + " " + c);
		char sign = c.charAt(0);
		switch (sign) {
		case '*':
			return last * next;
		case '/':
			return last / next;
		case '+':
			return last + next;
		case '-':
			return last - next;
		}
		return 0;
	}

	private static List<String> parseExpr(String expr) {
		// TODO Auto-generated method stub
		List<String> items = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if (isDelim(c)) {
				items.add(sb.toString());
				items.add(""+c);
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
			if (i == expr.length()-1)
				items.add(sb.toString());
		}
		return items;
	}

	private static boolean isDelim(char c) {
		if (("+-*/%^=()".indexOf(c) != -1))
			return true;
		return false;
	}
	public static void print(String info,List<String> vecotr) {
//		System.out.println("Info:"+info);
//		System.out.println("Size:" + vecotr.size());
//		System.out.println("Content:" + vecotr);
	}

}
