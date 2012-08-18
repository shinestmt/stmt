package com.hanghang.codestore.other;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.sql.Connection;

import org.junit.Test;

import com.hanghang.codestore.util.Constants;

public class FileTest {

	
	public static void main(String[] args) {
		String path = Thread.currentThread().getClass().getResource("/").getPath();
		path = path + "jetty/template/template.html";
		File file = new File(path);
		System.out.println(file.exists());
		
		
		
	}
	
	@Test
	public void testFiles() throws Exception {
		
		File dir = new File("D:\\yule\\video\\study\\传智播客_Java网上在线支付实战视频");
		nextFile(dir);
		
	}
	
	private void nextFile(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
//					if(pathname.isDirectory()){
//						return true;
//					}
					return pathname.isDirectory() || pathname.getName().endsWith("avi");
				}
			});
			
			for (File nextFile : files) {
				if(nextFile.isDirectory()){
					nextFile(nextFile);
				} else {
					String filePath = nextFile.getAbsolutePath();
					int start = filePath.indexOf("实战视频")+5;
//					int end = filePath.lastIndexOf("\\");
//					String fileName = filePath.substring(start, end);
					System.out.println(String.format("rename %s %s.avi", filePath, ""));
				}
			}
		}
		throw new ExceptionInInitializerError("");

	}
	
}
