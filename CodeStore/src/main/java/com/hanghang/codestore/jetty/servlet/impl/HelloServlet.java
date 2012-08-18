package com.hanghang.codestore.jetty.servlet.impl;

import com.hanghang.codestore.jetty.servlet.BaseServlet;

public class HelloServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String email;
	
	@Override
	public void init() {
		this.name = getValue("name");
		this.email = getValue("email");
	}

	@Override
	public String getTitle() {
		return "ÄãºÃ: "+name + "you email is :" +email;
	}

	@Override
	public String getContent() throws Exception {
		
		return "";
	}

}
