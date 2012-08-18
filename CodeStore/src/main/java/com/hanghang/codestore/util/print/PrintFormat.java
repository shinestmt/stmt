package com.hanghang.codestore.util.print;

/**
 * 打印格式化
 * @author fanhang
 *
 * @param <T>
 */
public class PrintFormat<T> {

	/**
	 * 打印中括号括住的对象的toString()的值，并换行。
	 * @param obj 要打印的对象
	 */
	public String format(T t) {
		return t.toString();
	}

}
