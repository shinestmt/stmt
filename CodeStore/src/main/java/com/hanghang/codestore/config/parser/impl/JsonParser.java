package com.hanghang.codestore.config.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.hanghang.codestore.config.Configuration;
import com.hanghang.codestore.config.parser.ConfigParser;

public class JsonParser extends ConfigParser {

	private File file;
	
	public JsonParser(File file) {
		this.file = file;
	}
	
	@SuppressWarnings("unchecked")
	public void parse() throws Exception {
		StringBuilder content = new StringBuilder();
		if(file.exists() && file.canRead()){
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		Map rootMap = (Map)JSON.parse(content.toString());
		if(rootMap==null || rootMap.isEmpty()){
			throw new Exception("config is empty");
		}
		
		Map<String, String> jdbcMap = (Map<String, String>)rootMap.get("jdbc");
		Map<String, Integer> taskMap = (Map<String, Integer>)rootMap.get("task");
		
		Configuration config = Configuration.me();
		if(jdbcMap!=null && !jdbcMap.isEmpty()){
			config.setDriver(jdbcMap.get("driver"));
			config.setUrl(jdbcMap.get("url"));
			config.setUserName(jdbcMap.get("userName"));
			config.setPassWord(jdbcMap.get("passWord"));
		}
		if(taskMap!=null && !taskMap.isEmpty()){
			config.setStart(taskMap.get("start"));
			config.setEnd(taskMap.get("end"));
			config.setInterval(taskMap.get("interval"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("src/com/fanhang/config/config.json");
		ConfigParser parser = new JsonParser(file);
		parser.parse();
		System.out.println(Configuration.me().toString());
		
	}
	
}
