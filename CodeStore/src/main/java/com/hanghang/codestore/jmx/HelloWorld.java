package com.hanghang.codestore.jmx;

public class HelloWorld implements HelloWorldMBean {

	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void print() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hello, " + name + "!!");
	}
}
