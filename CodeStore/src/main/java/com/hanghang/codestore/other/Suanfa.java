package com.hanghang.codestore.other;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.hanghang.codestore.loader.Taskable;
import com.hanghang.codestore.loader.common.TaskClassLoader;

public class Suanfa {

	/**
	 * Boolean≤‚ ‘
	 * @throws Exception
	 */
	@Test
	public void testBoolean() throws Exception {
//		TaskClassLoader loader = new TaskClassLoader();
//		loader.loadClass("com.hanghang.codestore.loader.task.CSDNTask");
//		loader.loadClass("com.hanghang.codestore.loader.Main");
//		Thread.currentThread().setContextClassLoader(loader);
//		
//		TaskClassLoader loader2 = (TaskClassLoader)Thread.currentThread().getContextClassLoader();
//		
//		List<Class<Taskable>> taskClassList = loader2.getTaskClassList();
//		for (Class<Taskable> claz : taskClassList) {
//			System.out.println(claz);
//		}
		
		
		
		Package pack = Package.getPackage("com.hanghang.codestore.other");
		Package[] packs = pack.getPackages();
		for (Package p : packs) {
			System.out.println(p);
		}
		
	}
	
}
