package com.hanghang.codestore.json;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * <p>
 * ���ܣ���Java�������л���Json��ʽ�ַ��� Ŀǰ֧�ֶ������ͣ�
 * </p>
 * <li>1.���û����������� :int long float double boolean �� String</li> <li>2.List<?></li>
 * <li>3.Map<String,?></li> <li>4.JavaBean</li><li>�κ���������</li>
 * 
 * <p>
 * <b>ǿ�����㣺
 * <li>1.֧�ֶ�������Ƕ�ף���Ҫע�ⲻҪ���벻֧�ֵ� �������Ͳ����������������޵ݹ鵼�¶�ջ������󡣽��ǣ�</li>
 * <li>2.����֮������˴˶����жԷ������ã�ǧ��Ҫ����������ʵ������ ��Ӧ���ò��������л�������ȡnull����������޵ݹ���ɶ�ջ��������ǣ�</li>
 * </b>
 * </p>
 * 
 * @author CFuture.aw
 * 
 */
public class JsonConverter {
        /**
         * �������Java����ת��Ϊjson����������������
         * 
         * @param <T>
         * @param t
         * @return
         */
        public static <T> String convert(T t) {
                return JsonConverter.convertFromKeyValue(null, t);
        }

        /**
         * �������Java����ת��ΪJson������������Ϊkey����������
         * 
         * @param <T>
         * @param key
         * @param value
         * @return
         */
        public static <T> String convert(String key, T value) {
                return JsonConverter.convert(new String[] { key },
                                new Object[] { value });
        }

        /**
         * �������Java����ת��ΪJson������������Ϊkey��һ������ͬ���ͻ�ͬ���Ͷ���
         * 
         * @param <T>
         * @param keys
         * @param values
         * @return
         */
        public static <T> String convert(String[] keys, T[] values) {
                return JsonConverter.convertWithKey(keys, values);
        }

        /**
         * �������Java����ת��ΪJson��Ĭ�ϼ�������Ϊkey�������ͬ���Ͷ���
         * 
         * @param <T>
         * @param ts
         * @return
         */
        public static <T> String convertWithDefaultKey(T... ts) {
                String[] keys = null;
                if (ts != null) {
                        if (ts.length == 1) {
                                return JsonConverter.convert(ts[0]);
                        }
                        keys = new String[ts.length];
                        for (int i = 0; i < keys.length; ++i) {
                                keys[i] = ts[i].getClass().getSimpleName().toLowerCase();
                        }
                }

                return JsonConverter.convertWithKey(keys, ts);
        }

        /**
         * �������Java����ת��Ϊjson���������֣�֧�ֶ������
         * 
         * @param <T>
         * @param keys
         * @param ts
         * @return
         */
        private static <T> String convertWithKey(String[] keys, T[] ts) {
                Map<String, T> map = null;
                if (keys != null && ts != null && keys.length == ts.length) {
                        map = new Hashtable<String, T>();
                        for (int i = 0; i < keys.length; ++i) {
                                // System.out.println(Json2.convertFromKeyValue(keys[i],
                                // ts[i]));
                                map.put(keys[i], ts[i]);
                        }
                }
                return JsonConverter.convertFromMap(map);
        }

        /**
         * ��Map����ת��Ϊjson���������������������ԣ�
         * 
         * @param map
         * @return
         */
        private static String convertFromMap(Map<String, ?> map) {
                return JsonConverter.convertFromMap(null, map, false);
        }

        /**
         * ��Map����ת��Ϊjson���������֣������Ƿ������������Բ���ֵ��
         * 
         * @param name
         * @param map
         * @param isProperty
         * @return
         */
        private static String convertFromMap(String name, Map<?, ?> map,
                        boolean isProperty) {
                String json = null;
                if (map != null && !map.isEmpty()) {
                        String format = "{%s}";
                        StringBuilder sb = new StringBuilder();
                        for (Entry<?, ?> entrySet : map.entrySet()) {

                                if (sb.length() > 0) {
                                        sb.append(",");
                                }
                                json = JsonConverter.convertFromKeyValue(String
                                                .valueOf(entrySet.getKey()), entrySet.getValue());
                                if (json != null) {
                                        sb.append(json);
                                }
                        }

                        if (isProperty) {
                                format = "\"%s\":{%s}";
                                if (name == null || "".equals(name)) {
                                        name = "map";
                                }
                                json = String.format(format, name, sb.toString());
                        } else {
                                // json = String.format(format, sb.toString());
                                if (name != null && !"".equals(name)) {
                                        format = "\"%s\":{%s}";
                                        json = String.format(format, name, sb.toString());
                                } else {
                                        json = String.format(format, sb.toString());
                                }
                        }
                }
                return json;
        }

        /**
         * ��List����ת��Ϊjson���������֡������Ƿ���������������Բ���ֵ��
         * 
         * @param name
         * @param list
         * @param isProperty
         * @return
         */
        private static String convertFromList(String name, List<?> list,
                        boolean isProperty) {
                String json = null;
                if (list != null && !list.isEmpty()) {
                        String format = "[%s]";

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                                if (sb.length() > 0) {
                                        sb.append(",");
                                }
                                json = JsonConverter.convertFromKeyValue(null, list.get(i));
                                if (json != null) {
                                        sb.append(json);
                                }
                        }
                        if (isProperty) {
                                format = "\"%s\":[%s]";
                                if (name == null || "".equals(name)) {
                                        name = "list";
                                }
                                json = String.format(format, name, sb.toString());
                        } else {
                                if (name != null && !"".equals(name)) {
                                        format = "\"%s\":[%s]";
                                        json = String.format(format, name, sb.toString());
                                } else {
                                        json = String.format(format, sb.toString());
                                }
                        }

                }
                return json;
        }

        private static <T> String convertFromArray(String name, T[] array,
                        boolean isProperty) {
                String json = null;
                if (array != null && array.length > 0) {
                        String format = "[%s]";

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < array.length; i++) {
                                if (sb.length() > 0) {
                                        sb.append(",");
                                }
                                json = JsonConverter.convertFromKeyValue(null, array[i]);
                                if (json != null) {
                                        sb.append(json);
                                }
                        }
                        if (isProperty) {
                                format = "\"%s\":[%s]";
                                if (name == null || "".equals(name)) {
                                        name = "array";
                                }
                                json = String.format(format, name, sb.toString());
                        } else {
                                if (name != null && !"".equals(name)) {
                                        format = "\"%s\":[%s]";
                                        json = String.format(format, name, sb.toString());
                                } else {
                                        json = String.format(format, sb.toString());
                                }
                        }

                }
                return json;
        }

        /**
         * set
         * 
         * @param <T>
         * @param name
         * @param set
         * @param isProperty
         * @return
         */
        private static <T> String convertFromSet(String name, Set<?> set,
                        boolean isProperty) {
                String json = null;
                if (set != null && !set.isEmpty()) {
                        String format = "[%s]";

                        StringBuilder sb = new StringBuilder();
                        for (Iterator<?> it = set.iterator(); it.hasNext();) {
                                if (sb.length() > 0) {
                                        sb.append(",");
                                }
                                json = JsonConverter.convertFromKeyValue(null, it.next());
                                if (json != null) {
                                        sb.append(json);
                                }
                        }
                        if (isProperty) {
                                format = "\"%s\":[%s]";
                                if (name == null || "".equals(name)) {
                                        name = "set";
                                }
                                json = String.format(format, name, sb.toString());
                        } else {
                                if (name != null && !"".equals(name)) {
                                        format = "\"%s\":[%s]";
                                        json = String.format(format, name, sb.toString());
                                } else {
                                        json = String.format(format, sb.toString());
                                }
                        }

                }
                return json;
        }

        /**
         * ����ֵ��key-valueת����json���������������ԣ�
         * 
         * @param <T>
         * @param key
         * @param value
         * @return
         */
        private static <T> String convertFromKeyValue(String key, T value) {
                return JsonConverter.convertFromKeyValue(key, value, false);
        }

        /**
         * ����ֵ��key-valueת��Ϊjson�������Ƿ�Ϊ�����������ԣ�
         * 
         * @param <T>
         * @param key
         * @param value
         * @return
         */
        private static <T> String convertFromKeyValue(String key, T value,
                        boolean isProperty) {
                String json = null;
                if (value != null) {
                        Class<?> cls = value.getClass();
                        String format = String.class.isAssignableFrom(cls) ? "\"%s\":\"%s\""
                                        : "\"%s\":%s";
                        if (String.class.isAssignableFrom(cls)
                                        || Integer.class.isAssignableFrom(cls)
                                        || Long.class.isAssignableFrom(cls)
                                        || Float.class.isAssignableFrom(cls)
                                        || Double.class.isAssignableFrom(cls)
                                        || Boolean.class.isAssignableFrom(cls)
                                        || int.class.isAssignableFrom(cls)
                                        || long.class.isAssignableFrom(cls)
                                        || float.class.isAssignableFrom(cls)
                                        || double.class.isAssignableFrom(cls)
                                        || boolean.class.isAssignableFrom(cls)) {
                                // �����������ͣ�key-value
                                if (key == null || "".equals(key.trim())) {
                                        format = String.class.isAssignableFrom(cls) ? "\"%s\""
                                                        : "%s";
                                        json = String.format(format, String.valueOf(value));
                                } else {
                                        json = String.format(format, key, String.valueOf(value));
                                }
                        } else if (Object[].class.isAssignableFrom(cls)) {
                                // �������飬ʹ���±�����������
                                json = JsonConverter.convertFromArray(key, (Object[]) value,
                                                isProperty);
                        } else if (List.class.isAssignableFrom(cls)) {
                                // �������飬ʹ���±�����������
                                json = JsonConverter.convertFromList(key, (List<?>) value,
                                                isProperty);
                        } else if (Set.class.isAssignableFrom(cls)) {
                                // �������飬ʹ���±�����������
                                json = JsonConverter.convertFromSet(key, (Set<?>) value,
                                                isProperty);
                        } else if (Map.class.isAssignableFrom(cls)) {
                                json = JsonConverter.convertFromMap(key, (Map<?, ?>) value,
                                                isProperty);
                        } else {
                                // ��������������������ʱ����Ϊ���Զ�������
                                // �����ȡ���ԣ������Եݹ�
                                ReflectUtil ru = new ReflectUtil(value);
                                StringBuilder sb = new StringBuilder("{");
                                format = "\"%s\":%s";
                                for (String name : ru.getFieldsName()) {
                                        Method m = ru.getMethod("get"
                                                        + StringUtil.toUpCaseFirst(name));
                                        if (m != null) {
                                                try {
                                                        json = JsonConverter.convertFromKeyValue(name, m
                                                                        .invoke(value), true);
                                                        if (json != null) {
                                                                if (sb.length() > 1) {
                                                                        sb.append(",");
                                                                }
                                                                sb.append(json);
                                                        }
                                                } catch (Exception e) {
                                                        e.printStackTrace();
                                                }
                                        }
                                }
                                sb.append("}");
                                // ���keyû�и��������ֵ������Ϊ�����������Զ������ͣ�
                                if (key == null || "".equals(key)) {
                                        format = "%s";
                                        json = String.format(format, sb.toString());
                                } else {
                                        json = String.format(format, key, sb.toString());
                                }
                        }
                }

                return json;
        }
}