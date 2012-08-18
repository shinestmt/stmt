package com.hanghang.codestore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类标识<br>
 * Company: 深圳市康索特软件有限公司<br>
 * Copyright: Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author 樊航
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassAnnotation {

	String value();
}
