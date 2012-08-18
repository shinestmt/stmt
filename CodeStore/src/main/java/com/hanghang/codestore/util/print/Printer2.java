package com.hanghang.codestore.util.print;

import java.util.List;

import com.hanghang.codestore.util.Log;

public class Printer2 {

	/**
     * TAB键值, 换行符
     */
    private static final String KEY_TAB = "\t";
    
    /**
     * 打印对象和换行符
     * @param obj Object
     */
    public static <T> void println(T obj)
    {
    	Log.print(obj.toString());
    }
    
    /**
     * 打印对象和换行符
     * @param obj Object
     */
    public static <T> void println(T obj, PrintFormat<? super T> formatter)
    {
    	Log.print(formatter.format(obj));
    }

    /**
     * 打印对象数组的值
     * @param os 对象数组
     */
    public static <T> void println(T[] objs)
    {
        for (int i = 0; i < objs.length; i++)
        {
        	Log.print(objs[i]);
        }
    }
    
    /**
     * 打印对象数组的值，每项独占一行。
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
     * 打印列表的内容
     * @param list 普通对象或二维对象数组对象的列表
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
