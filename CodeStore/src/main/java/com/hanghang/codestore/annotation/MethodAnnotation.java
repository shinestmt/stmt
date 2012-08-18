package com.hanghang.codestore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ������ʶ<br>
 * Company: �����п�����������޹�˾<br>
 * Copyright: Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author ����
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnnotation {

	String description();

	boolean isAnnotation();
}
