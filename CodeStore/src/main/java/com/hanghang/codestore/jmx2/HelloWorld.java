package com.hanghang.codestore.jmx2;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


public class HelloWorld implements HelloWorldMBean, Serializable{

	static AtomicInteger count = new AtomicInteger();
    private static final long serialVersionUID = 1627976932729278650L;
    int id = 0;
    String name;

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void sayHello() {
        System.out.println(getHelloString());
    }

    public synchronized String getHelloString() {
//    	System.out.println("1111");
        return "Hello, " + name;
    }

    public synchronized int getId() {
        return id;
    }

    public HelloWorldMBean getThis() {
        return this;
    }

}

