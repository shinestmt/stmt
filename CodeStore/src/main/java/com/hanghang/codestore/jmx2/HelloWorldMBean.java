package com.hanghang.codestore.jmx2;

import java.io.Serializable;

public interface HelloWorldMBean extends Serializable{
	void setName(String name);

    String getName();

    void sayHello();

    String getHelloString();

    int getId();

    String toString();

    int hashCode();

    boolean equals( Object obj);

    HelloWorldMBean getThis();
}

