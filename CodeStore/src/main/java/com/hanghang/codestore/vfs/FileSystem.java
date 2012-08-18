package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * �ļ�ϵͳ����
 * @author michael
 */
public interface FileSystem {

	/** �ļ�ϵͳ���� */
	public static final String FSInfo_Name = "FSInfo_Name";
	
	/** �ļ�ϵͳ���� */
	public static final String FSInfo_Description = "FSInfo_Description";
	
	/** �ļ�ϵͳ�汾 */
	public static final String FSInfo_Version = "FSInfo_Version";
	
	/** �ļ�ϵͳ�ռ��С */
	public static final String FSInfo_HasSizeInformation = "FSInfo_HasSizeInformation";
	
	/** �ļ�ϵͳռ�ÿռ��С */
	public static final String FSInfo_HasFreeSpaceInformation = "FSInfo_HasFreeSpaceInformation";

	/**
	 * ��ȡ�ļ�ϵͳ����
	 * @return �ļ�ϵͳ����
	 */
	public String getName();
	

	// public FileSystemSession createSession(Principal principal);

	/**
	 * ��ȡ�й��ļ�ϵͳ����Ϣ�б�
	 * @return �ļ�ϵͳ����Ϣ�б�Map
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
	 * ��ȡ�ļ�ϵͳ��Ŀ¼
	 * @return �ļ�ϵͳ��Ŀ¼
	 * @throws java.io.FileNotFoundException
	 */
	public Directory getRoot() throws FileNotFoundException;
	
	/**
	 * ��ȡ�ļ�ϵͳ��С(byte)
	 * @return �ļ�ϵͳ��С��-1
	 */
	public long getSize();

	/**
	 * ��ȡ�ļ�ϵͳʣ��ռ�(byte)
	 * @return �ļ�ϵͳʣ��ռ��-1
	 */
	public long getFreeSpace();

	/**
	 * ����·����ȡ�ڵ�
	 * @param path ·��
	 * @return �ڵ�
	 * @throws FileNotFoundException
	 */
	public Node getNode(String path) throws FileNotFoundException;

	/**
	 * �ر�
	 * @throws IOException
	 */
	public void close() throws IOException;
}
