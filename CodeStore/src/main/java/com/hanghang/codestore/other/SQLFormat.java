package com.hanghang.codestore.other;

import java.util.StringTokenizer;

public class SQLFormat {
	public static void main(String[] args) {
		
		System.out.println(format(" \t\t "," SELECT * FROM TB WHERE A=A AND B=B ORDER BY C "));
	}

	public static String format(String table, String sql) {
		if (sql == null)
			return " null ";
		String text = new SQLFormatter().setText(sql).format().getText();
		String result = "";
		// String s="sdf\nsdf\n";
		StringTokenizer st = new StringTokenizer(text, " \n ");
		while (st.hasMoreTokens()) {
			String row = st.nextToken();
			row = table + row;
			result += row;
		}

		// return result;
		return "";
	}

}
