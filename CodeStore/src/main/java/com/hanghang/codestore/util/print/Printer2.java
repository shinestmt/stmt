package com.hanghang.codestore.util.print;

import java.util.List;

import com.hanghang.codestore.util.Log;

public class Printer2 {

	/**
     * TAB��ֵ, ���з�
     */
    private static final String KEY_TAB = "\t";
    
    /**
     * ��ӡ����ͻ��з�
     * @param obj Object
     */
    public static <T> void println(T obj)
    {
    	Log.print(obj.toString());
    }
    
    /**
     * ��ӡ����ͻ��з�
     * @param obj Object
     */
    public static <T> void println(T obj, PrintFormat<? super T> formatter)
    {
    	Log.print(formatter.format(obj));
    }

    /**
     * ��ӡ���������ֵ
     * @param os ��������
     */
    public static <T> void println(T[] objs)
    {
        for (int i = 0; i < objs.length; i++)
        {
        	Log.print(objs[i]);
        }
    }
    
    /**
     * ��ӡ���������ֵ��ÿ���ռһ�С�
     * @param obj Object
     */
    public static <T> void println(T[] objs, PrintFormat<? super T> formatter)
    {
    	for (int i = 0; i < objs.length; i++)
        {
    		Log.print(formatter.format(objs[i]));
        }
    }

    /**
     * ��ӡ�б������
     * @param list ��ͨ������ά�������������б�
     */
    public static void print(List list)
    {
    	for (Object o : list) {
    		if (o instanceof Object[]) {
    			for (Object obj : (Object[])o) {
    				println(obj);
				}
    		} else {
    			println(o);
    		}
		}
    }
}
