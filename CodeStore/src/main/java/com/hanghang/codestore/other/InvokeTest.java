package com.hanghang.codestore.other;

import java.lang.reflect.Method;

public class InvokeTest {
	
	public void sayHello(){
		System.out.println("Hello, Invoke!");
	}
	
	public static void main(String[] args) {
		
		Method[] methods = InvokeTest.class.getMethods();
		try {
			for (int i = 0; i < methods.length; i++) {
				
				String name = methods[i].getName();
				System.out.println(name);
//				methods[i].invoke(InvorkTest.class, new Object[]{});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
