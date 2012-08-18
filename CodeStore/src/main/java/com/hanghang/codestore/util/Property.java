package com.hanghang.codestore.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ���Ա��ʽ������
 * @version 1.0.0
 * [2012-04-27]
 * ֧�ֶ༶����
 * ֧�������������������
 * ֧�ַ���
 * ֧���ַ����Զ��������Ը�ֵ
 * ֧���ַ��������Ը�ֵ
 * @author bsmith@qq.com
 */
@SuppressWarnings("unchecked")
public class Property
{
	/**
	 * ����Ĭ�ϸ�ʽ����ʽ
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * ʱ��Ĭ�ϸ�ʽ����ʽ
	 */
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	/**
	 * ��������ʱ���ʽ����ʽ
	 */
	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final int TYPE_BOOL = 1;
	public static final int TYPE_BYTE = 2;
	public static final int TYPE_CHAR = 3;
	public static final int TYPE_SHORT = 4;
	public static final int TYPE_INT = 5;
	public static final int TYPE_LONG = 6;
	public static final int TYPE_FLOAT = 7;
	public static final int TYPE_DOUBLE = 8;
	public static final int TYPE_BIGDECIMAL = 9;
	public static final int TYPE_STRING = 10;
	public static final int TYPE_TIME = 11;
	public static final int TYPE_DATE = 12;
	public static final int TYPE_TIMESTAMP = 13;
	public static final int TYPE_BYTE_ARRAY = 14;
	
	/**
	 * ����ģʽ,MODE_OBJECT��ʾ����ԭʼ����ģʽ
	 */
	public static final int MODE_OBJECT = 1;
	/**
	 * ����ģʽ,MODE_VALUE��ʾʹ���ַ���ֵ���ö���ģʽ,�ַ������Զ����ݶ������ͽ���ת��
	 */
	public static final int MODE_VALUE = 2;
	
	/**
	 * ��ȡ�������ԡ�
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @return ���Ա��ʽ������Ķ������ԣ�δ�ҵ�����null��
	 * @throws SecurityException
	 * @throws NoSuchMethodException ����δʵ��getter/setter
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static Object getProperty(Object bean, String name) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException
	{
		Class<?> cls = bean.getClass();
		int N = name.length();
		char c;
		for (int i = 0;i < N;i ++)
		{
			c = name.charAt(i);
			if (c == '.')
			{
				String prop = name.substring(0, i);
				String rName = toReadMethodName(prop);
				Object obj = null;
				Method read = cls.getMethod(rName);
				obj = read.invoke(bean);
				if (null == obj)
				{
					return null;
				}
				return getProperty(obj, name.substring(i+1, N));
			}
			else if (c == '[')
			{
				String key = null;
				int j = i+1;
				while (j < N)
				{
					c = name.charAt(j++);
					if (c == ']')
					{
						key = name.substring(i+1, j-1);
						break;
					}
				}
				Object last = null;
				Type type = null;
				if (null != key)
				{
					String prop = name.substring(0, i);
					String rName = toReadMethodName(prop);
					Method read = cls.getMethod(rName);
					last = read.invoke(bean);
					type = read.getGenericReturnType();
					if (null == last)
					{
						return null;
					}
				}
				while (null != key)
				{
					Class<?> rTCls = type.getClass();
					if (Class.class.isAssignableFrom(rTCls))
					{
						Class<?> rCls = (Class<?>)type;
						if (rCls.isArray())
						{
							int idx = Integer.parseInt(key);
							if (idx < 0 || idx >= Array.getLength(last))
							{
								return null;
							}
							last = Array.get(last, idx);
							type = rCls.getComponentType();
						}
						else
						{
							if (List.class.isAssignableFrom(rCls))
							{
								List<?> cl = (List<?>)last;
								int idx = Integer.parseInt(key);
								if (idx < 0 || cl.size() <= idx)
								{
									return null;
								}
								last = cl.get(idx);
								if (null != last)
								{
									type = last.getClass();
								}
							}
							else if (Iterable.class.isAssignableFrom(rCls))
							{
								Iterable<?> iter = (Iterable<?>)last;
								int idx = Integer.parseInt(key);
								int ei = 0;
								Object elem = null;
								for (Object e : iter)
								{
									if (ei == idx)
									{
										elem = e;
										break;
									}
									ei ++;
								}
								if (null == elem)
								{
									return null;
								}
								last = elem;
								type = last.getClass();
							}
							else
							{
								return null;
							}
						}
					}
					else if (ParameterizedType.class.isAssignableFrom(rTCls))
					{
						ParameterizedType pType = (ParameterizedType)type;
						Class<?> rCls = (Class<?>)pType.getRawType();
						if (List.class.isAssignableFrom(rCls))
						{
							List<?> cl = (List<?>)last;
							int idx = Integer.parseInt(key);
							if (idx < 0 || cl.size() <= idx)
							{
								return null;
							}
							last = cl.get(idx);
							if (null == last)
							{
								return null;
							}
							type = pType.getActualTypeArguments()[0];
						}
						else if (Map.class.isAssignableFrom(rCls))
						{
							Type[] gTypes = pType.getActualTypeArguments();
							Class<?> kCls = (Class<?>)gTypes[0];
							Map<?, ?> mp = (Map<?, ?>)last;
							Object gKey = null;
							gKey = convert(kCls, key);
							if (null == gKey)
							{
								return null;
							}
							last = mp.get(gKey);
							type = gTypes[1];
						}
						else if (Iterable.class.isAssignableFrom(rCls))
						{
							Iterable<?> iter = (Iterable<?>)last;
							int idx = Integer.parseInt(key);
							int ei = 0;
							Object elem = null;
							for (Object e : iter)
							{
								if (ei == idx)
								{
									elem = e;
									break;
								}
								ei ++;
							}
							if (null == elem)
							{
								return null;
							}
							last = elem;
							type = pType.getActualTypeArguments()[0];
						}
						else
						{
							return null;
						}
					}
					else if (GenericArrayType.class.isAssignableFrom(rTCls))
					{
						GenericArrayType gType = (GenericArrayType)type;
						int idx = Integer.parseInt(key);
						if (idx < 0 || Array.getLength(last) <= idx)
						{
							return null;
						}
						last = Array.get(last, idx);
						type = gType.getGenericComponentType();
					}
					else
					{
						return null;
					}
					if (j == N)
					{
						return last;
					}
					if (null == last)
					{
						return null;
					}
					key = null;
					c = name.charAt(j++);
					if (c == '.')
					{
						return getProperty(last, name.substring(j, N));
					}
					else if (c == '[')
					{
						i = j;
						while (j < N)
						{
							c = name.charAt(j++);
							if (c == ']')
							{
								key = name.substring(i, j-1);
								break;
							}
						}
					}
				}
				return null;
			}
		}
		String rName = toReadMethodName(name);
		Object obj = null;
		try
		{
			Method read = cls.getMethod(rName);
			obj = read.invoke(bean);
		}
		catch(Throwable e0)
		{
			rName = toBoolReadMethodName(name);
			Method read = cls.getMethod(rName);
			obj = read.invoke(bean);
		}
		return obj;
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @return ��ȡ������ֵ�����Բ����ڻ����쳣����null���÷������׳��쳣����Ҫ�����쳣��Ҫ����getProperty����
	 * @see #getProperty(Object, String)
	 */
	public static Object getObject(Object bean, String name)
	{
		try
		{
			return getProperty(bean, name);
		}
		catch(Throwable err){}
		return null;
	}
	
	/**
	 * ���ö�������ֵ
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @param value ����ֵ
	 * @throws SecurityException
	 * @throws NoSuchMethodException δ�ҵ����Ե�getter/setter����
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static void setProperty(Object bean, String name, Object value) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		setProperty(bean, name, value, MODE_OBJECT);
	}
	
	/**
	 * ���ö�������ֵ
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @param value ����ֵ
	 * @param mode ����ģʽ
	 * MODE_OBJECT - ʹ�ö���ֱ�Ӹ�ֵģʽ,�����ж�������ת����
	 * MODE_VALUE - ʹ������ת��ģʽ,value����ΪString[]����,������Ա��ʽָ�������Ϊ�������,�����ת��(String[])value�����ԱΪ���������Ա����������,Ȼ��ת��������鸳ֵ��ָ������.���ָ������ΪList����Iterable,������ո�List��Iterable,Ȼ��(String[])value��Ա���ת��ΪList��IterableԪ������,�������ӵ���List��Iterable.���ָ������Ϊ��ͨ��������,��ȡ((String[])value)[0],������ת��Ϊ������������,����ֵ��ָ������.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static void setProperty(Object bean, String name, Object value, int mode) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		Class<?> cls = bean.getClass();
		int N = name.length();
		char c;
		for (int i = 0;i < N;i ++)
		{
			c = name.charAt(i);
			if (c == '.')
			{
				String prop = name.substring(0, i);
				String rName = toReadMethodName(prop);
				Method read = cls.getMethod(rName);
				Object obj = read.invoke(bean);
				if (null == obj)
				{
					Class<?> pCls = read.getReturnType();
					obj = pCls.newInstance();
					String wName = toWriteMethodName(prop);
					Method write = cls.getMethod(wName, pCls);
					write.invoke(bean, obj);
				}
				String sub = name.substring(i+1, N);
				setProperty(obj, sub, value, mode);
				return;
			}
			else if (c == '[')
			{
				String key = null;
				int j = i+1;
				while (j < N)
				{
					c = name.charAt(j++);
					if (c == ']')
					{
						key = name.substring(i+1, j-1);
						break;
					}
				}
				Object root = bean;
				Method write = null;
				Object pkey = null;
				int index = 0;
				Object last = null;
				Type type = null;
				if (null != key)
				{
					String prop = name.substring(0, i);
					String rName = toReadMethodName(prop);
					Method read = cls.getMethod(rName);
					last = read.invoke(bean);
					Class<?> rCls = read.getReturnType();
					if (rCls.isArray())
					{
						if (null == last)
						{
							String wName = toWriteMethodName(prop);
							write = cls.getMethod(wName, rCls);
						}
						else
						{
							int idx = Integer.parseInt(key);
							int len = Array.getLength(last);
							if (len < idx+1)
							{
								String wName = toWriteMethodName(prop);
								write = cls.getMethod(wName, rCls);
							}
						}
					}
					else
					{
						if (null == last)
						{
							last = rCls.newInstance();
							String wName = toWriteMethodName(prop);
							write = cls.getMethod(wName, rCls);
							write.invoke(bean, last);
						}
					}
					type = read.getGenericReturnType();
				}
				else
				{
					return;
				}
				while (null != key)
				{
					boolean exists = (j != N);
					Class<?> rTCls = type.getClass();
					if (Class.class.isAssignableFrom(rTCls))
					{
						Class<?> rCls = (Class<?>)type;
						Class<?> eCls = null;
						if (rCls.isArray())
						{
							int idx = Integer.parseInt(key);
							if (null == last || idx > Array.getLength(last)-1)
							{
								Object src = last;
								eCls = rCls.getComponentType();
								last = Array.newInstance(eCls, idx+1);
								if (null != src)
								{
									System.arraycopy(src, 0, last, 0, Array.getLength(src));
								}
								if (null != write)
								{
									write.invoke(root, last);
								}
								else
								{
									Array.set(root, index, last);
								}
							}
							if (exists)
							{
								Object elem = Array.get(last, idx);
								if (null == elem)
								{
									eCls = rCls.getComponentType();
									if (!eCls.isArray())
									{
										elem = eCls.newInstance();
										Array.set(last, idx, elem);
									}
								}
								root = last;
								index = idx;
								last = elem;
								type = rCls.getComponentType();
							}
							else
							{
								if (MODE_OBJECT == mode)
								{
									Array.set(last, idx, value);
								}
								else if (MODE_VALUE == mode)
								{
									if (null == eCls)
									{
										eCls = rCls.getComponentType();
									}
									Object arr = convert(eCls, (String[])value);
									Array.set(last, idx, arr);
								}
							}
						}
					}
					else if (ParameterizedType.class.isAssignableFrom(rTCls))
					{
						ParameterizedType pType = (ParameterizedType)type;
						Class<?> rCls = (Class<?>)pType.getRawType();
						if (List.class.isAssignableFrom(rCls))
						{
							List cl = (List)last;
							Type vType = null;
							int idx = Integer.parseInt(key);
							if (exists)
							{
								Type[] gTypes = pType.getActualTypeArguments();
								vType = gTypes[0];
								Object gValue = null;
								if (cl.size() > idx)
								{
									gValue = cl.get(idx);
								}
								if (null == gValue)
								{
									gValue = newPlainObject(vType);
									setListItem(cl, idx, gValue);
								}
								root = last;
								index = idx;
								last = gValue;
								type = vType;
							}
							else
							{
								if (MODE_OBJECT == mode)
								{
									setListItem(cl, idx, value);
								}
								else if (MODE_VALUE == mode)
								{
									if (null == vType)
									{
										Type[] gTypes = pType.getActualTypeArguments();
										vType = gTypes[0];
										Object val = convert(vType, (String[])value);
										setListItem(cl, idx, val);
									}
								}
							}
						}
						else if (Map.class.isAssignableFrom(rCls))
						{
							Map mp = (Map)last;
							Type[] gTypes = pType.getActualTypeArguments();
							Class<?> kCls = (Class<?>)gTypes[0];
							Object gKey = convert(kCls, key);
							if (exists)
							{
								Object gValue = mp.get(gKey);
								Type vType = gTypes[1];
								if (null == gValue)
								{
									gValue = newPlainObject(vType);
									if (null != gValue)
									{
										mp.put(gKey, gValue);
									}
								}
								root = last;
								pkey = gKey;
								last = gValue;
								type = vType;
							}
							else
							{
								if (MODE_OBJECT == mode)
								{
									mp.put(gKey, value);
								}
								else if (MODE_VALUE == mode)
								{
									Type vType = gTypes[1];
									Object val = convert(vType, (String[])value);
									mp.put(gKey, val);
								}
							}
						}
					}
					else if (GenericArrayType.class.isAssignableFrom(rTCls))
					{
						GenericArrayType gType = (GenericArrayType)type;
						Class<?> eCls = null;
						int dim = 1;
						Type eType = gType.getGenericComponentType();
						int idx = Integer.parseInt(key);
						if (null == last || Array.getLength(last) < idx+1)
						{
							Type eRType = eType;
							while (true)
							{
								Class<?> eRTCls = eRType.getClass();
								if (Class.class.isAssignableFrom(eRTCls))
								{
									eCls = (Class<?>)eRType;
									break;
								}
								if (GenericArrayType.class.isAssignableFrom(eRTCls))
								{
									eRType = ((GenericArrayType)eRType).getGenericComponentType();
									dim ++;
								}
								else if (ParameterizedType.class.isAssignableFrom(eRTCls))
								{
									eCls = (Class<?>)((ParameterizedType)eRType).getRawType();
									break;
								}
							}
							Object src = last;
							int[] d = new int[dim];
							d[0] = idx+1;
							last = Array.newInstance(eCls, d);
							if (null != src)
							{
								System.arraycopy(src, 0, last, 0, Array.getLength(src));
							}
							if (null != write)
							{
								write.invoke(root, last);
							}
							else
							{
								Class<?> rcls = root.getClass();
								if (rcls.isArray())
								{
									Array.set(root, index, last);
								}
								else if (List.class.isAssignableFrom(rcls))
								{
									List cl = (List)root;
									cl.set(index, last);
								}
								else if (Map.class.isAssignableFrom(rcls))
								{
									Map mp = (Map)root;
									mp.put(pkey, last);
								}
							}
						}
						if (exists)
						{
							Object elem = Array.get(last, idx);
							if (null == elem)
							{
								if (dim == 1)
								{
									elem = eCls.newInstance();
									Array.set(last, idx, elem);
								}
							}
							root = last;
							index = idx;
							last = elem;
							type = eType;
						}
						else
						{
							if (MODE_OBJECT == mode)
							{
								Array.set(last, idx, value);
							}
							else if (MODE_VALUE == mode)
							{
								Object val = convert(eType, (String[])value);
								Array.set(last, idx, val);
							}
						}
					}
					if (!exists)
					{
						return;
					}
					key = null;
					write = null;
					c = name.charAt(j++);
					if (c == '.')
					{
						setProperty(last, name.substring(j, N), value, mode);
						return;
					}
					else if (c == '[')
					{
						i = j;
						while (j < N)
						{
							c = name.charAt(j++);
							if (c == ']')
							{
								key = name.substring(i, j-1);
								break;
							}
						}
					}
				}
				return;
			}
		}
		if (MODE_OBJECT == mode)
		{
			String wName = toWriteMethodName(name);
			Method write = null;
			Class<?> wCls = null;
			if (null != value)
			{
				wCls = value.getClass();
				try
				{
					write = cls.getMethod(wName, wCls);
				}
				catch(Throwable err){}
				if (null == write)
				{
					Class<?> wOCls = toOutOfBoxClass(wCls);
					if (wOCls != wCls)
					{
						try
						{
							write = cls.getMethod(wName, wOCls);
						}
						catch(Throwable err){}
					}
				}
				if (null == write)
				{
					write = getSuperWriteMethod(cls, wName, wCls);
				}
			}
			else
			{
				String rName = toReadMethodName(name);
				Method read = null;
				try
				{
					read = cls.getMethod(rName);
				}
				catch(Throwable err)
				{
					rName = toBoolReadMethodName(name);
					read = cls.getMethod(rName);
				}
				wCls = read.getReturnType();
				write = cls.getMethod(wName, wCls);
			}
			write.invoke(bean, value);
		}
		else if (MODE_VALUE == mode)
		{
			String rName = toReadMethodName(name);
			Method read = null;
			try
			{
				read = cls.getMethod(rName);
			}
			catch(Throwable err){}
			if (null == read)
			{
				try
				{
					rName = toBoolReadMethodName(name);
					read = cls.getMethod(rName);
				}
				catch(Throwable err){}
			}
			Class<?> wCls = read.getReturnType();
			if (!Collection.class.isAssignableFrom(wCls))
			{
				value = convert(wCls, (String[])value);
			}
			else
			{
				Type wType = read.getGenericReturnType();
				value = convert(wType, (String[])value);
			}
			String wName = toWriteMethodName(name);
			Method write = cls.getMethod(wName, wCls);
			write.invoke(bean, value);
		}
	}
	
	/**
	 * ���ö�������ֵ�����׳��쳣��Ҫ�����쳣��Ҫ����setProperty����.
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @param value ��������ֵ
	 * @see #setProperty(Object, String, Object)
	 */
	public static void setObject(Object bean, String name, Object value)
	{
		try
		{
			setProperty(bean, name, value);
		}
		catch(Throwable err){}
	}
	
	/**
	 * ���ö�������ֵ�����׳��쳣��Ҫ�����쳣��Ҫ����setProperty����
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @param value ��������ֵ�����ݶ����������������Զ����ַ���ת��Ϊָ������
	 * @see #setProperty(Object, String, Object, int)
	 */
	public static void setValue(Object bean, String name, String value)
	{
		try
		{
			setProperty(bean, name, new String[]{value}, MODE_VALUE);
		}
		catch(Throwable err){}
	}
	
	/**
	 * ���ö�������ֵ
	 * @param bean ��ǰ��������
	 * @param name ���Ա��ʽ
	 * @param values �ַ������֣�������Ա��ʽָ��������Ϊ����������࣬���������values��ֵ�����Ա��������Ա��ʽָ��������Ϊ�����������ͣ������values[0]Ϊ����ֵ��
	 * @see #setProperty(Object, String, Object, int)
	 */
	public static void setValue(Object bean, String name, String[] values)
	{
		try
		{
			setProperty(bean, name, values, MODE_VALUE);
		}
		catch(Throwable err){}
	}

	/**
	 * ��ʽ���ַ�������ת��Ϊָ�����Ͷ���
	 * @param type ָ�����ͣ����typeΪParameterizedType�������������ΪCollection�����࣬���������valuesΪCollection�ĳ�Ա�����typeΪGenericArrayType������ȡGenericArrayType�Ļ���Ԫ�أ�����values��һ����Ϊ���飻���typeΪClass����ʹ��convert(Class, String[])������
	 * @param values �ַ�������
	 * @return ת����Ķ���
	 * @throws ParseException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @see #convert(Class, String[])
	 */
	public static Object convert(Type type, String[] values) throws ParseException, InstantiationException, IllegalAccessException
	{
		Class<?> tCls = type.getClass();
		Class<?> cls = null;
		if (Class.class.isAssignableFrom(tCls))
		{
			cls = (Class<?>)type;
		}
		else if (ParameterizedType.class.isAssignableFrom(tCls))
		{
			ParameterizedType gType = (ParameterizedType)type;
			cls = (Class<?>)gType.getRawType();
			if (Collection.class.isAssignableFrom(cls))
			{
				Type[] gTypes = gType.getActualTypeArguments();
				Class<?> eCls = (Class<?>)gTypes[0];
				Collection cl = (Collection)cls.newInstance();
				for (int i = 0;i < values.length;i ++)
				{
					cl.add(convert(eCls, values[i]));
				}
				return cl;
			}
			return null;
		}
		else if (GenericArrayType.class.isAssignableFrom(tCls))
		{
			GenericArrayType gType = (GenericArrayType)type;
			Type eType = gType.getGenericComponentType();
			Class<?> eCls = (Class<?>)eType;
			Object arr = Array.newInstance(eCls, new int[1]);
			cls = arr.getClass();
		}
		return convert(cls, values);
	}
	
	/**
	 * ��ָ���ַ�������ת��Ϊָ�����Ͷ���
	 * @param cls ָ��������
	 * @param values �ַ������飬���clsΪ�������ͣ���values�ĳ�Ա�����ת��Ϊcls�ĳ�Ա���ͣ����cls�������飬��values[0]ת��Ϊcls���͡�
	 * @return ת�����cls���͵Ķ���
	 * @throws ParseException ��ʽ�������ʹ���
	 * @see #convert(Class, String)
	 */
	public static Object convert(Class<?> cls, String[] values) throws ParseException
	{
		if (cls.isArray())
		{
			Class<?> eCls = cls.getComponentType();
			if (eCls == String.class)
			{
				return values;
			}
			else
			{
				Object arr = Array.newInstance(eCls, values.length);
				for (int i = 0;i < values.length;i ++)
				{
					try
					{
						Array.set(arr, i, convert(eCls, values[i]));
					}
					catch(Throwable err){}
				}
				return arr;
			}
		}
		else
		{
			if (values.length == 0)
			{
				return null;
			}
			Object obj = convert(cls, values[0]);
			return obj;
		}
	}
	
	/**
	 * ��ȡ���л������Ե���
	 * @param cls ��ǰ������
	 * @param name �������Ա��ʽ��ָֻ��"."�ָ�����Ա��ʽ����֧��ʹ��[]��ʽָ�����������ʽ����Ϊ����Iterable��List��Map�����������ʹ���������ʽ[]ָ��������Ϊnullʱ���޷�������ȡ�������͡�
	 * @return �������Ա��ʽ����
	 */
	public static Class<?> getPlainType(Class<?> cls, String name)
	{
		try
		{
			int N = name.length();
			char c;
			for (int i = 0;i < N;i ++)
			{
				c = name.charAt(i);
				if (c == '.')
				{
					String prop = name.substring(0, i);
					String rName = toReadMethodName(prop);
					Method read = null;
					try
					{
						read = cls.getMethod(rName);
					}
					catch(Throwable err){}
					if (null == read)
					{
						try
						{
							rName = toBoolReadMethodName(prop);
							read = cls.getMethod(rName);
						}
						catch(Throwable err){}
					}
					Class<?> rCls = read.getReturnType();
					return getPlainType(rCls, name.substring(i+1));
				}
			}
			String rName = toReadMethodName(name);
			Method read = null;
			try
			{
				read = cls.getMethod(rName);
			}
			catch(Throwable err){}
			if (null == read)
			{
				try
				{
					rName = toBoolReadMethodName(name);
					read = cls.getMethod(rName);
				}
				catch(Throwable err){}
			}
			Class<?> rCls = read.getReturnType();
			return rCls;
		}
		catch(Throwable err){}
		return null;
	}
	
	/**
	 * ��ʽ�Ľ��ַ���ת��Ϊָ����������
	 * @param cls ָ���������͵��࣬��ǰ֧�ֵ������У�
	 * int.class,
	 * Integer.class, 
	 * String.class,
	 * boolean.class(1, true, yes����true�������ִ�Сд����������false��Boolean.class��ͬ),
	 * Boolean.class,
	 * double.class,
	 * Double.class,
	 * byte.class,
	 * Byte.class,
	 * char.class,
	 * Character.class,
	 * short.class,
	 * Short.class,
	 * long.class,
	 * Long.class,
	 * float.class,
	 * Float.class,
	 * java.sql.Time.class(HH:mm:ss),
	 * java.sql.Date.class(yyyy-MM-dd),
	 * java.util.Date.class(yyyy-MM-dd),
	 * java.sql.Timestamp.class(yyyy-MM-dd HH:mm:ss),
	 * byte[].class,
	 * char[].class,
	 * java.math.BigDecimal.class
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Object convert(Class<?> cls, String value) throws ParseException
	{
		if (int.class == cls || Integer.class == cls)
		{
			if (null == value)
	    	{
	    		return (int)0;
	    	}
	    	return Integer.parseInt(value);
		}
		else if (String.class == cls)
		{
			return value;
		}
		else if (boolean.class == cls || Boolean.class == cls)
		{
			if (null == value)
			{
				return false;
			}
			if (value.equalsIgnoreCase("1") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"))
			{
				return true;
			}
			return false;
		}
		else if (double.class == cls || Double.class == cls)
		{
			if (null == value)
			{
				return (double)0.0;
			}
			return Double.parseDouble(value);
		}
		else if (byte.class == cls || Byte.class == cls)
		{
			if (null == value)
			{
				return (byte)0x00;
			}
			return Byte.parseByte(value);
		}
		else if (char.class == cls || Character.class == cls)
		{
			if (null == value || value.length() == 0)
		    {
		    	return (char)'\0';
		    }
		    return value.charAt(0);
		}
		else if (short.class == cls || Short.class == cls)
		{
			if (null == value)
		    {
		    	return (short)0;
		    }
		    return Short.parseShort(value);
		}
		else if (long.class == cls || Long.class == cls)
		{
			if (null == value)
		    {
		    	return (long)0;
		    }
		    return Long.parseLong(value);
		}
		else if (float.class == cls || Float.class == cls)
		{
			if (null == value)
			{
				return (double)0.0;
			}
			return Float.parseFloat(value);
		}
		else if (null != value)
		{
			if (java.util.Date.class == cls)
			{
				return DATE_FORMAT.parse(value);
			}
			else if (java.sql.Time.class == cls)
			{
				return new java.sql.Time(TIME_FORMAT.parse(value).getTime());
			}
			else if (java.sql.Date.class == cls)
			{
				return new java.sql.Date(DATE_FORMAT.parse(value).getTime());
			}
			else if (java.sql.Timestamp.class == cls)
			{
				return new java.sql.Timestamp(TIMESTAMP_FORMAT.parse(value).getTime());
			}
			else if (byte[].class == cls)
			{
				return value.getBytes();
			}
			else if (char[].class == cls)
			{
				return value.toCharArray();
			}
			else if (java.math.BigDecimal.class == cls)
			{
				return new java.math.BigDecimal(value);
			}
		}
		return null;
	}
	
	public static java.sql.Time toTime(String arg)
	{
		try
		{
			java.util.Date dt = TIME_FORMAT.parse(arg);
			java.sql.Time time = new java.sql.Time(dt.getTime());
			return time;
		}
		catch(Throwable err){}
		return null;
	}
	
	public static java.sql.Date toDate(String arg)
	{
		try
		{
			java.util.Date dt = DATE_FORMAT.parse(arg);
			java.sql.Date time = new java.sql.Date(dt.getTime());
			return time;
		}
		catch(Throwable err){}
		return null;
	}
	
	public static java.sql.Timestamp toTimestamp(String arg)
	{
		try
		{
			java.util.Date dt = TIMESTAMP_FORMAT.parse(arg);
			java.sql.Timestamp time = new java.sql.Timestamp(dt.getTime());
			return time;
		}
		catch(Throwable err){}
		return null;
	}

	protected static Object newPlainObject(Type type) throws InstantiationException, IllegalAccessException
	{
		Class<?> vTCls = type.getClass();
		if (Class.class.isAssignableFrom(vTCls))
		{
			Class<?> vCls = (Class<?>)type;
			if (!vCls.isArray())
			{
				return vCls.newInstance();
			}
		}
		else if (ParameterizedType.class.isAssignableFrom(vTCls))
		{
			ParameterizedType vPType = (ParameterizedType)type;
			Class<?> vCls = (Class<?>)vPType.getRawType();
			return vCls.newInstance();
		}
		return null;
	}
	
	protected static void setListItem(List cl, int idx, Object item)
	{
		if (cl.size() < idx+1)
		{
			while (cl.size() < idx)
			{
				cl.add(null);
			}
			cl.add(item);
		}
		else
		{
			cl.set(idx, item);
		}
	}
	
	/**
	 * �������������͵����ڷ����ͻ�������������֮���໥ת��
	 * @param cls �������߻���������
	 * @return ����ת�������,���clsΪ����������������int.class,�򷵻�Integer.class,��֮���clsΪInteger.class,�򷵻�int.class,���Ϊ�ǻ���������������Date.class,�򷵻�cls����
	 */
	protected static Class<?> toOutOfBoxClass(Class<?> cls)
	{
		if (cls == Boolean.class)
		{
			cls = boolean.class;
		}
		else if (cls == boolean.class)
		{
			cls = Boolean.class;
		}
		else if (cls == Byte.class)
		{
			cls = byte.class;
		}
		else if (cls == byte.class)
		{
			cls = Byte.class;
		}
		else if (cls == Character.class)
		{
			cls = char.class;
		}
		else if (cls == char.class)
		{
			cls = Character.class;
		}
		else if (cls == Short.class)
		{
			cls = short.class;
		}
		else if (cls == short.class)
		{
			cls = Short.class;
		}
		else if (cls == Integer.class)
		{
			cls = int.class;
		}
		else if (cls == int.class)
		{
			cls = Integer.class;
		}
		else if (cls == Long.class)
		{
			cls = long.class;
		}
		else if (cls == long.class)
		{
			cls = Long.class;
		}
		else if (cls == Float.class)
		{
			cls = float.class;
		}
		else if (cls == float.class)
		{
			cls = Float.class;
		}
		else if (cls == Double.class)
		{
			cls = double.class;
		}
		else if (cls == double.class)
		{
			cls = Double.class;
		}
		return cls;
	}
	
	/**
	 * ���Զ��������ת��Ϊ�����������͵���,��Integer.class��int.class
	 * @param cls �����
	 * @return �����������͵���
	 */
	protected static Class<?> toPrimitiveClass(Class<?> cls)
	{
		if (cls == Boolean.class)
		{
			cls = boolean.class;
		}
		else if (cls == Byte.class)
		{
			cls = byte.class;
		}
		else if (cls == Character.class)
		{
			cls = char.class;
		}
		else if (cls == Short.class)
		{
			cls = short.class;
		}
		else if (cls == Integer.class)
		{
			cls = int.class;
		}
		else if (cls == Long.class)
		{
			cls = long.class;
		}
		else if (cls == Float.class)
		{
			cls = float.class;
		}
		else if (cls == Double.class)
		{
			cls = double.class;
		}
		return cls;
	}
	
	/**
	 * ��bool������ת��Ϊ��is��ͷ�ķ�����
	 * @param name ������
	 * @return bool���͵�getter������
	 */
	protected static String toBoolReadMethodName(String name)
	{
		int N = name.length();
		char[] cs = new char[2+N];
		cs[0] = 'i';
		cs[1] = 's';
		cs[2] = Character.toUpperCase(name.charAt(0));
		name.getChars(1, N, cs, 3);
		name = new String(cs);
		return name;
	}
	
	/**
	 * ��������ת��Ϊget��ͷ�ķ�����,�÷���ֻת��Ϊget��ͷ�ķ���,��ת��Ϊbool���͵�is��ͷ�ķ���
	 * @param name ������
	 * @return ת�����get������
	 */
	protected static String toReadMethodName(String name)
	{
		int N = name.length();
		char[] cs = new char[3+N];
		cs[0] = 'g';
		cs[1] = 'e';
		cs[2] = 't';
		cs[3] = Character.toUpperCase(name.charAt(0));
		name.getChars(1, N, cs, 4);
		name = new String(cs);
		return name;
	}
	
	/**
	 * ��������ת��Ϊsetter������
	 * @param name ����������
	 * @return setter������
	 */
	protected static String toWriteMethodName(String name)
	{
		int N = name.length();
		char[] cs = new char[3+N];
		cs[0] = 's';
		cs[1] = 'e';
		cs[2] = 't';
		cs[3] = Character.toUpperCase(name.charAt(0));
		name.getChars(1, N, cs, 4);
		name = new String(cs);
		return name;
	}
	
	/**
	 * ��ȡ��cls�Ĳ������ͼ���wCls�ĸ�����߸��ӿ����͵�setter����
	 * @param cls ��ǰ��
	 * @param wName setter��������
	 * @param wCls ���ݵ�����
	 * @return ����setter���������δ�ҵ�������null
	 */
	protected static Method getSuperWriteMethod(Class<?> cls, String wName, Class<?> wCls)
	{
		Class<?> wSCls = wCls;
		while (null != wSCls)
		{
			wSCls = wSCls.getSuperclass();
			if (null != wSCls)
			{
				try
				{
					Method write = cls.getMethod(wName, wSCls);
					return write;
				}
				catch(Throwable err){}
			}
		}
		return getInterfaceWriteMethod(cls, wName, wCls);
	}
	
	/**
	 * ��ȡ��cls�Ĳ�������Ϊ����wCls�ĸ��ӿ����͵�setter����
	 * @param cls ��ǰ��
	 * @param wName setter��������
	 * @param wCls ���ݵ�����
	 * @return ����setter���������δ�ҵ�������null
	 */
	protected static Method getInterfaceWriteMethod(Class<?> cls, String wName, Class<?> wCls)
	{
		Class<?>[] cs = wCls.getInterfaces();
		for (int i = 0;i < cs.length;i ++)
		{
			try
			{
				Method write = cls.getMethod(wName, cs[i]);
				return write;
			}
			catch(Throwable err){}
		}
		for (int i = 0;i < cs.length;i ++)
		{
			Method write = getInterfaceWriteMethod(cls, wName, cs[i]);
			if (null != write)
			{
				return write;
			}
		}
		return null;
	}
}
