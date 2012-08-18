package com.hanghang.codestore.annotation;

import java.lang.reflect.Method;

public class AnnotationTest {

	public static void main(String[] args) throws Exception {
		
		Class<?> cls = Class.forName("com.hanghang.codestore.annotation.AnnotationImpl");
		boolean flag = cls.isAnnotationPresent(ClassAnnotation.class);
		if (flag) {
			//判断类是annotation
			ClassAnnotation classAnnotation = cls.getAnnotation(ClassAnnotation.class);
			System.out.println(classAnnotation.value());
		}
		
		Method method = cls.getMethod("sayHello");
		flag = method.isAnnotationPresent(MethodAnnotation.class);
		if (flag) {
			//判断方法是annotation
			MethodAnnotation annotation2 = method.getAnnotation(MethodAnnotation.class);
			System.out.println(annotation2.description() + "\t" + annotation2.isAnnotation());
		}
	}
}
