package com.hanghang.codestore.util.print;

import java.util.List;

public class Printer {

	/**
     * TAB键值
     */
    private static final String KEY_TAB = "\t";

    /**
     * 左中括号
     */
    private static final String KEY_LBRACKET = "";

    /**
     * 右中括号
     */
    private static final String KEY_RBRACKET = "\t";

    /**
     * 打印对象
     * @param obj Object
     */
    public static void print(Object obj)
    {
        System.out.print(obj);
    }

    /**
     * 打印换行
     */
    public static void println()
    {
        System.out.println();
    }

    /**
     * 打印对象和换行符
     * @param obj Object
     */
    public static void println(Object obj)
    {
        System.out.println(obj);
    }
    
    /**
     * 打印对象和换行符
     * @param obj Object
     */
    public static <T> void println(T obj, PrintFormat<? super T> formatter)
    {
        System.out.println(formatter.format(obj));
    }

    /**
     * 打印对象数组的值
     * @param os 对象数组
     */
    public static void print(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printWithBracket(os[i]);
        }
    }
    
    /**
     * 打印对象数组的值
     * @param os 对象数组
     */
    public static void print(int[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
        	print(os[i]+"\t");
        }
        System.out.println();
    }

    /**
     * 打印对象数组的值，每项独占一行。
     * @param os 对象数组
     */
    public static void println(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printlnWithBracket(os[i]);
        }
    }
    
    /**
     * 打印对象数组的值，每项独占一行。
     * @param obj Object
     */
    public static <T> void println(T[] os, PrintFormat<? super T> formatter)
    {
    	for (int i = 0; i < os.length; i++)
        {
    		printWithBracket(formatter.format(os[i]));
        }
    }

    /**
     * 打印二维对象数组的内容
     * @param objs 二维对象数组的内容
     */
    public static void print(Object[][] objs)
    {
        for (int i = 0; i < objs.length; i++)
        {
            for (int j = 0; j < objs[i].length; j++)
            {
                printWithBracket(objs[i][j]);
                print(KEY_TAB);
            }
            println();
        }
    }
    
    /**
     * 打印对象数组的值，每项独占一行。
     * @param obj Object
     */
    public static <T> void println(T[][] objs, PrintFormat<? super T> formatter)
    {
    	for (int i = 0; i < objs.length; i++)
        {
            for (int j = 0; j < objs[i].length; j++)
            {
                printWithBracket(formatter.format(objs[i][j]));
                print(KEY_TAB);
            }
            println();
        }
    }

    /**
     * 打印列表的内容
     * @param list 普通对象或二维对象数组对象的列表
     */
    public static void print(List list)
    {
        for (int i = 0, il = list.size(); i < il; i++)
        {
            Object o = list.get(i);
            if (o instanceof Object[])
            {
                print((Object[]) o);
                println();
            }
            else
            {
                println(o);
            }
        }
    }

    /**
     * 打印中括号括住的对象的toString()的值
     * @param obj 要打印的对象
     */
    private static void printWithBracket(Object obj)
    {
        System.out.print(KEY_LBRACKET);
        System.out.print(obj);
        System.out.print(KEY_RBRACKET);
    }

    /**
     * 打印中括号括住的对象的toString()的值，并换行。
     * @param obj 要打印的对象
     */
    private static void printlnWithBracket(Object obj)
    {
        System.out.print(KEY_LBRACKET);
        System.out.print(obj);
        System.out.println(KEY_RBRACKET);
    }
	
}
