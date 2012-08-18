package com.hanghang.codestore.other;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlTest {
	
	public static void main(String[] args) {
		try {
			getHTML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getHTML() throws Exception {
		URL url = new URL("http://www.baidu.com");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line=br.readLine())!=null) {
			System.out.println(line);
		}
	}

}
