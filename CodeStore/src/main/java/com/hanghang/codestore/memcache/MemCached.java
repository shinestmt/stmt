package com.hanghang.codestore.memcache;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * ʹ��memcached�Ļ���ʵ����.
 * @author ��ľ����
 * 
 */
public class MemCached {
	/** �Ƿ�������������� */
	public boolean isConnected = false;
	
	/** ȫ��Ψһʵ�� */
	private static MemCached instance;
	
	/** MemCached�ͻ��� */
	private static MemcachedClient cacheClient = null;
	
	private static InetSocketAddress socketAddress = null;

	/**
	 * �����͹��췽����������ʵ������
	 * 
	 */
	private MemCached() {
		
//		�������б����Ȩ��
//		String[] servers = { "127.0.0.1:11211" };
//		Integer[] weights = { 3 };
		
		try {
			socketAddress = new InetSocketAddress("127.0.0.1", 11211);
			cacheClient = new XMemcachedClient(socketAddress, 3);
			isConnected = instance != null;
		} catch (Exception e) {
			isConnected = false;
		}
	}

	/**
	 * ��ȡMemCachedΨһʵ��.
	 * @return
	 */
	public static MemCached me() {
		if(instance==null){
			instance = new MemCached();
		}
		return instance;
	}
	
	/**
	 * ��ȡ�½���MemCachedʵ��.
	 * @return
	 */
	public static MemCached create() {
		return new MemCached();
	}

	public boolean isKeyExist(String key) throws Exception {
		return cacheClient.get(key) != null;
	}
	
	/**
	 * ���һ��ָ����ֵ��������.
	 * @param key ��
	 * @param value ֵ
	 * @return
	 */
	public boolean add(String key, Object value) throws Exception {
		return cacheClient.add(key, 0, value);
	}

	/**
	 * ���һ��ָ����ֵ��������(ָ��ʧЧʱ��).
	 * @param key ��
	 * @param value ֵ
	 * @param expiry ʧЧʱ��
	 * @return 
	 * @throws Exception 
	 */
	public boolean add(String key, Object value, Date expiry) throws Exception {
		return cacheClient.add(key, 0, value, expiry.getTime());
	}
	
	/**
	 * ���һ��ָ����ֵ��������(ָ��ʧЧʱ��).
	 * @param key ��
	 * @param value ֵ
	 * @param expiry ʧЧʱ��
	 * @return
	 */
	public boolean delete(String key) throws Exception {
		return cacheClient.delete(key);
	}
	
	/**
	 * ���һ��ָ����ֵ��������(ָ��ʧЧʱ��).
	 * @param key ��
	 * @param value ֵ
	 * @param expiry ʧЧʱ��
	 * @return
	 */
	public boolean delete(String key, Date expiry) throws Exception {
		return cacheClient.delete(key, expiry.getTime());
	}

	/**
	 * �滻�����е�����
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean replace(String key, Object value) throws Exception {
		return cacheClient.replace(key, 0, value);
	}

	public boolean replace(String key, Object value, Date expiry) throws Exception {
		return cacheClient.replace(key, 0, value, expiry.getTime());
	}
	
	/**
	 * ����ָ���Ĺؼ��ֻ�ȡ����.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) throws Exception {
		return cacheClient.get(key);
	}
	
	public boolean flushAll(){
		try {
			cacheClient.flushAll();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Map<String, String> statsItems() throws Exception {
		return cacheClient.stats(socketAddress);
	}
	
	public void statsCacheDump(){
		
	}
}