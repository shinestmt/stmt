package com.hanghang.codestore.memcache;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 使用memcached的缓存实用类.
 * @author 铁木箱子
 * 
 */
public class MemCached {
	/** 是否已与服务器连接 */
	public boolean isConnected = false;
	
	/** 全局唯一实例 */
	private static MemCached instance;
	
	/** MemCached客户端 */
	private static MemcachedClient cacheClient = null;
	
	private static InetSocketAddress socketAddress = null;

	/**
	 * 保护型构造方法，不允许实例化！
	 * 
	 */
	private MemCached() {
		
//		服务器列表和其权重
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
	 * 获取MemCached唯一实例.
	 * @return
	 */
	public static MemCached me() {
		if(instance==null){
			instance = new MemCached();
		}
		return instance;
	}
	
	/**
	 * 获取新建的MemCached实例.
	 * @return
	 */
	public static MemCached create() {
		return new MemCached();
	}

	public boolean isKeyExist(String key) throws Exception {
		return cacheClient.get(key) != null;
	}
	
	/**
	 * 添加一个指定的值到缓存中.
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean add(String key, Object value) throws Exception {
		return cacheClient.add(key, 0, value);
	}

	/**
	 * 添加一个指定的值到缓存中(指定失效时间).
	 * @param key 键
	 * @param value 值
	 * @param expiry 失效时间
	 * @return 
	 * @throws Exception 
	 */
	public boolean add(String key, Object value, Date expiry) throws Exception {
		return cacheClient.add(key, 0, value, expiry.getTime());
	}
	
	/**
	 * 添加一个指定的值到缓存中(指定失效时间).
	 * @param key 键
	 * @param value 值
	 * @param expiry 失效时间
	 * @return
	 */
	public boolean delete(String key) throws Exception {
		return cacheClient.delete(key);
	}
	
	/**
	 * 添加一个指定的值到缓存中(指定失效时间).
	 * @param key 键
	 * @param value 值
	 * @param expiry 失效时间
	 * @return
	 */
	public boolean delete(String key, Date expiry) throws Exception {
		return cacheClient.delete(key, expiry.getTime());
	}

	/**
	 * 替换缓存中的数据
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
	 * 根据指定的关键字获取对象.
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