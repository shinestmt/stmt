package com.hanghang.codestore.memcache;

public class Test {
	
	public static void main(String[] args) throws Exception {
		MemCached mem = MemCached.create();
		System.out.println(mem.isKeyExist("test"));
		mem.add("test", "ceshi");
		System.out.println(mem.isKeyExist("test"));
		
		Object obj = mem.get("test");
		
		System.out.println(obj);
		
	}
	
}
