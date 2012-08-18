package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * 文件系统对象
 * @author michael
 */
public interface FileSystem {

	/** 文件系统名称 */
	public static final String FSInfo_Name = "FSInfo_Name";
	
	/** 文件系统描述 */
	public static final String FSInfo_Description = "FSInfo_Description";
	
	/** 文件系统版本 */
	public static final String FSInfo_Version = "FSInfo_Version";
	
	/** 文件系统空间大小 */
	public static final String FSInfo_HasSizeInformation = "FSInfo_HasSizeInformation";
	
	/** 文件系统占用空间大小 */
	public static final String FSInfo_HasFreeSpaceInformation = "FSInfo_HasFreeSpaceInformation";

	/**
	 * 获取文件系统名字
	 * @return 文件系统名字
	 */
	public String getName();
	

	// public FileSystemSession createSession(Principal principal);

	/**
	 * 获取有关文件系统的信息列表
	 * @return 文件系统的信息列表Map
	 */
	public Map getInfo();

	/**
	 * Retrieves the security used by the filesystem.
	 * 
	 * @return The Security object.
	 */
	// public Security getSecurity();
	/**
	 * Sets the security object used by the filesystem.
	 * 
	 * @param security
	 *            The new security to use for the filesystem.
	 */
	// public void setSecurity(Security security);
	
	/**
	 * 获取文件系统根目录
	 * @return 文件系统根目录
	 * @throws java.io.FileNotFoundException
	 */
	public Directory getRoot() throws FileNotFoundException;
	
	/**
	 * 获取文件系统大小(byte)
	 * @return 文件系统大小或-1
	 */
	public long getSize();

	/**
	 * 获取文件系统剩余空间(byte)
	 * @return 文件系统剩余空间或-1
	 */
	public long getFreeSpace();

	/**
	 * 根据路径获取节点
	 * @param path 路径
	 * @return 节点
	 * @throws FileNotFoundException
	 */
	public Node getNode(String path) throws FileNotFoundException;

	/**
	 * 关闭
	 * @throws IOException
	 */
	public void close() throws IOException;
}
