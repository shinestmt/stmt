package com.hanghang.codestore.jetty.servlet.html;

public class Html {
	
	protected StringBuilder sb = new StringBuilder();
	
	protected String lable = null;
	
	
	public final Html write(String str) {
		sb.append(str);
		return this;
	}
	
	public final Html write(Html html) {
		write(html.toHtml());
		return this;
	}
	
	public final String toHtml() {
		return sb.toString();
	}
	
	
	
	public Html start(){
		sb.append("<").append(lable).append(">");
		return this;
	}
	
	public Html start(String str){
		sb.append("<").append(lable).append(" ").append(str).append(">");
		return this;
	}
	
	public Html end(){
		sb.append("</").append(lable).append(">");
		return this;
	}
	
}
