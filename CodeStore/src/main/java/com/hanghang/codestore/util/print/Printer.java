package com.hanghang.codestore.util.print;

import java.util.List;

public class Printer {

	/**
     * TAB��ֵ
     */
    private static final String KEY_TAB = "\t";

    /**
     * ��������
     */
    private static final String KEY_LBRACKET = "";

    /**
     * ��������
     */
    private static final String KEY_RBRACKET = "\t";

    /**
     * ��ӡ����
     * @param obj Object
     */
    public static void print(Object obj)
    {
        System.out.print(obj);
    }

    /**
     * ��ӡ����
     */
    public static void println()
    {
        System.out.println();
    }

    /**
     * ��ӡ����ͻ��з�
     * @param obj Object
     */
    public static void println(Object obj)
    {
        System.out.println(obj);
    }
    
    /**
     * ��ӡ����ͻ��з�
     * @param obj Object
     */
    public static <T> void println(T obj, PrintFormat<? super T> formatter)
    {
        System.out.println(formatter.format(obj));
    }

    /**
     * ��ӡ���������ֵ
     * @param os ��������
     */
    public static void print(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printWithBracket(os[i]);
        }
    }
    
    /**
     * ��ӡ���������ֵ
     * @param os ��������
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
     * ��ӡ���������ֵ��ÿ���ռһ�С�
     * @param os ��������
     */
    public static void println(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printlnWithBracket(os[i]);
        }
    }
    
    /**
     * ��ӡ���������ֵ��ÿ���ռһ�С�
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
     * ��ӡ��ά�������������
     * @param objs ��ά�������������
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
     * ��ӡ���������ֵ��ÿ���ռһ�С�
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
     * ��ӡ�б������
     * @param list ��ͨ������ά�������������б�
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
     * ��ӡ��������ס�Ķ����toString()��ֵ
     * @param obj Ҫ��ӡ�Ķ���
     */
    private static void printWithBracket(Object obj)
    {
        System.out.print(KEY_LBRACKET);
        System.out.print(obj);
        System.out.print(KEY_RBRACKET);
    }

    /**
     * ��ӡ��������ס�Ķ����toString()��ֵ�������С�
     * @param obj Ҫ��ӡ�Ķ���
     */
    private static void printlnWithBracket(Object obj)
    {
        System.out.print(KEY_LBRACKET);
        System.out.print(obj);
        System.out.println(KEY_RBRACKET);
    }
	
}
