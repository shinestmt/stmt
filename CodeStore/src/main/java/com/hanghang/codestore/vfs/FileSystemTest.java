package com.hanghang.codestore.vfs;

import java.io.File;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

/**
 * Hello world!
 * 
 */
public class FileSystemTest {
	
	public static void main(String[] args) throws Exception {
		
		
		FileSystemManager manager = VFS.getManager();
		FileObject fileObject = manager.resolveFile(new File("F:/tmp").toString());
		
		
		
		FileSystem fs = null;
		
		if(manager.canCreateFileSystem(fileObject)){
			fs = fileObject.getFileSystem();
		}
//		System.out.println(fs.getRootName());
		
		
		
		System.out.println(fileObject.getType().hasChildren());
		
		for (FileObject obj : fileObject.getChildren()) {
			System.out.println(obj.getName());
		}
		
		System.out.println("Hello World!");
	}
	
}
