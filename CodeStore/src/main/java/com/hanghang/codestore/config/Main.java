package com.hanghang.codestore.config;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.junit.Test;

import com.hanghang.codestore.config.parser.ConfigParser;
import com.hanghang.codestore.config.parser.impl.JsonParser;
import com.hanghang.codestore.util.Constants;
import com.hanghang.codestore.util.Property;

public class Main {

	@Test
	public void testConfig() throws Exception {
		File file = null;
		file = Constants.getFile(this.getClass(), "config.json");
		ConfigParser parser = new JsonParser(file);
		parser.parse();
		System.out.println(Configuration.me().toString());
		
		Configuration config = Configuration.me();
		Property.setProperty(config, "passWord", "123");
		System.out.println(Configuration.me().toString());
		
		System.out.println(Property.getPlainType(Configuration.class, "interval"));
		
	}
	
	public void testURL() throws Exception {
		URL url = new URL("http://www.baidu.com");
		System.out.println(url.getContent());
		URI uri = URI.create("http://www.baidu.com");
		uri.notify();
		System.out.println(uri.getHost());
		
	}
	
}
