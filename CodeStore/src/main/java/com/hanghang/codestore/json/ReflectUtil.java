package com.hanghang.codestore.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ���乤�ߣ��Ը�����Ч
 * @author weiwei
 *
 */
public class ReflectUtil {
        private Object object;

        public void setObject(Object object) {
                this.object = object;
        }

        public Object getObject() {
                return this.object;
        }

        public ReflectUtil(Object object) {
                this.object = object;
        }

        public ReflectUtil() {
        }

        public ReflectUtil(Class<?> clazz) throws InstantiationException,
                        IllegalAccessException {
                this.object = clazz.newInstance();
        }

        public String getSimpleName() {
                return this.object.getClass().getSimpleName();
        }

        public String getName() {
                return this.object.getClass().getName();
        }

        /**
         * ��������
         * @return
         */
        public String[] getFieldsName() {
                Field[] fields = this.getFields();
                String[] result = null;
                if (fields != null) {
                        result = new String[fields.length];
                        for (int i = 0; i < result.length; ++i) {
                                result[i] = fields[i].getName();
                        }
                }

                return result == null ? new String[]{""} : result;
        }

        /**
         * ȡ�����������������԰��������
         * 
         * @return
         */
        public Field[] getFields() {
                List<Field> list = new ArrayList<Field>();
                Class<?> clazz = object.getClass();
                for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                        try {
                                list.addAll(Arrays.asList(clazz.getDeclaredFields()));
                        } catch (Exception e) {
                                // ������ô����Ҫ��������������쳣��������д�������׳�ȥ��
                                // ���������쳣��ӡ���������ף���Ͳ���ִ��clazz =
                                // clazz.getSuperclass(),���Ͳ�����뵽��������
                        }
                }

                return list.toArray(new Field[] {});
        }

        /**
         * ȡ�ø�������������ķ���ʵ������������
         * 
         * @param name
         * @param t
         * @return
         */
        public Method getMethod(String name) {
                Method result = null;
                try {
                        Method[] ms = this.getMethods();
                        for (Method m : ms) {
                                if (name.equals(m.getName())) {
                                        result = m;
                                        break;
                                }
                        }
                } catch (SecurityException e) {
                        e.printStackTrace();
                }

                return result;
        }

        /**
         * ��������
         * @param name
         * @return
         */
        public Method[] getMethods(String name) {
                List<Method> list = new ArrayList<Method>();
                try {
                        for (Method m : this.getMethods()) {
                                if (m.getName().equals(name)) {
                                        list.add(m);
                                }
                        }

                } catch (SecurityException e) {
                        e.printStackTrace();
                }

                return list.toArray(new Method[] {});
        }

        /**
         * ��������
         * @param name
         * @return
         */
        public Method getGetter(String name) {
                return this.getMethod("get" + StringUtil.toUpCaseFirst(name));
        }

        /**
         * ��������
         * @param name
         * @return
         */
        public Method getSetter(String name) {
                return this.getMethod("set" + StringUtil.toUpCaseFirst(name));
        }

        /**
         * ȡ�ø������������������ʵ��
         * 
         * @param name
         * @param t
         * @return
         */
        public Field getField(String name) {
                Field result = null;
                try {
                        Field[] fs = this.getFields();
                        for (Field f : fs) {
                                if (name.equals(f.getName())) {
                                        result = f;
                                        break;
                                }
                        }
                } catch (SecurityException e) {
                        e.printStackTrace();
                }

                return result;
        }

        /**
         * ȡ�������������ķ������������
         * 
         * @return
         */
        public Method[] getMethods() {
                List<Method> list = new ArrayList<Method>();
                Class<?> clazz = object.getClass();
                for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                        try {
                                list.addAll(Arrays.asList(clazz.getDeclaredMethods()));
                        } catch (Exception e) {
                                // ������ô����Ҫ��������������쳣��������д�������׳�ȥ��
                                // ���������쳣��ӡ���������ף���Ͳ���ִ��clazz =
                                // clazz.getSuperclass(),���Ͳ�����뵽��������
                        }
                }

                return list.size() > 0 ? list.toArray(new Method[] {}) : null;
        }
}