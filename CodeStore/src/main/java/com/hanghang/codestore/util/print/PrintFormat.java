package com.hanghang.codestore.util.print;

/**
 * ��ӡ��ʽ��
 * @author fanhang
 *
 * @param <T>
 */
public class PrintFormat<T> {

	/**
	 * ��ӡ��������ס�Ķ����toString()��ֵ�������С�
	 * @param obj Ҫ��ӡ�Ķ���
	 */
	public String format(T t) {
		return t.toString();
	}

}
