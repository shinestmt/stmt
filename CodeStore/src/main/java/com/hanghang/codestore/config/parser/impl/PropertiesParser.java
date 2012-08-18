package com.hanghang.codestore.config.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.hanghang.codestore.config.Configuration;
import com.hanghang.codestore.config.parser.ConfigParser;

public class PropertiesParser extends ConfigParser {

	private File file;
	
	public PropertiesParser(File file) {
		this.file = file;
	}
	
	public void parse() throws FileNotFoundException, IOException {
		Properties pops = new Properties();
		pops.load(new FileInputStream(file));
		
		Configuration config = Configuration.me();
		config.setDriver(pops.getProperty("jdbc.driver"));
		config.setUrl(pops.getProperty("jdbc.url"));
		config.setUserName(pops.getProperty("jdbc.username"));
		config.setPassWord(pops.getProperty("jdbc.password"));
		config.setStart(Integer.parseInt(pops.getProperty("task.start")));
		config.setEnd(Integer.parseInt(pops.getProperty("task.end")));
		config.setInterval(Integer.parseInt(pops.getProperty("task.interval")));
	}
	
}
