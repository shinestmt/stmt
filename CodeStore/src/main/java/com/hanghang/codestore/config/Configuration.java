package com.hanghang.codestore.config;

public class Configuration {

	private String driver;
	
	private String url;
	
	private String userName;
	
	private String passWord;
	
	private int start;
	
	private int end;
	
	private int interval;
	
	private static Configuration config = new Configuration();
	
	public static Configuration me(){
		return config;
	}
	
	public static Configuration create(){
		return new Configuration();
	}
	
	private Configuration(){
		
	}
	
	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(300);
		sb.append("driver : ").append(this.driver).append("\n")
		.append("url : ").append(this.url).append("\n")
		.append("userName : ").append(this.userName).append("\n")
		.append("passWord : ").append(this.passWord).append("\n\n")
		.append("start : ").append(this.start).append("\n")
		.append("end : ").append(this.end).append("\n")
		.append("interval : ").append(this.interval);
		//JSON.toJSONString(this, true)
		return sb.toString();
	}
	
	
}
