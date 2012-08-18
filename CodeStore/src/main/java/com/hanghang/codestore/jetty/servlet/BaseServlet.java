package com.hanghang.codestore.jetty.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLDocument;

import com.hanghang.codestore.jetty.servlet.html.Html;

public abstract class BaseServlet extends HttpServlet {
	
	protected Map<String, String[]> params = new HashMap<String, String[]>();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		params = request.getParameterMap();
		init();
		request.setCharacterEncoding("gb2312");
		
		response.setContentType("text/html;charset=gb2312");
		response.setCharacterEncoding("gb2312");
		response.setStatus(HttpServletResponse.SC_OK);
		
		Writer out = response.getWriter();
		String resule = getResult();
		out.write(resule);
		System.out.println(resule);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
	
	private String getResult(){
		try {
			String result = getTemplateContent();
			String title = getTitle();
//			String content = getContent();
			result = result.replace("${title}", title);
			
			return result;
		} catch (Exception e) {
			return "ÏµÍ³´íÎó";
		}
	}
	
	private String getTemplateContent() throws IOException{
		File file = new File("src/com/fanhang/jetty/template.html");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder content = new StringBuilder();
		while ((line=reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		return content.toString();
	}
	
	protected final String getValue(String name) {
		String[] tmp = getValues(name);
		return tmp==null ? null : tmp[0];
	}
	
	protected final String[] getValues(String name) {
		return params.get(name);
	}
	
	public HTMLDocument getHtml() {
		return null;
	}
	
	public abstract void init();
	
	public abstract String getTitle();
	
	public abstract String getContent() throws Exception;
	
	public Document getBody() throws Exception {
		return null;
	}
	
	public Document getBodyContent() throws Exception {
		return null;
	}
	
	public String getBodyRemark() {
		return "";
	}
	
	
}
