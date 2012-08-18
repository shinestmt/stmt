package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * �ļ�ϵͳĿ¼����
 * @author mzk
 */
public interface Directory extends Node {
	/**
	 * �������ƴ�����Ŀ¼
	 * @param name ��Ŀ¼����
	 * @return Returns �µ�Ŀ¼����
	 * @throws java.io.IOException
	 */
	public Directory createDirectory(String name) throws IOException;

	/**
	 * �����µ��ļ�
	 * @param name ���ļ�����
	 * @return Returns ���ļ�����
	 * @throws java.io.IOException
	 */
	public File createFile(String name) throws IOException;

	/**
	 * �Ƿ����ļ�ϵͳ�ĸ�
	 * @return True/False
	 */
	public boolean isRoot();

	/**
	 * �Ƿ����ָ�����Ƶ����ļ�����Ŀ¼
	 * @param name ���ļ�������Ŀ¼��
	 * @return True/False
	 */
	public boolean hasChild(String name);

	/**
	 * �Ƿ����ָ�����Ƶ��ļ�
	 * @param name �ļ�����
	 * @return True/False
	 */
	public boolean hasFile(String name);

	/**
	 * �Ƿ����ָ�����Ƶ�Ŀ¼
	 * @param name Ŀ¼����
	 * @return True/False 
	 */
	public boolean hasDirectory(String name);

	/**
	 * ��ȡָ�����Ƶ��ļ���Ŀ¼����
	 * @param name �ļ�����Ŀ¼��
	 * @return �ļ���Ŀ¼�Ľڵ����
	 * @throws java.io.FileNotFoundException
	 */
	public Node getChild(String name) throws FileNotFoundException;

	/**
	 * ���ݸ��������ƻ�ȡ�ļ�����
	 * @param name �ļ�����
	 * @return �ļ�����
	 * @throws java.io.FileNotFoundException
	 */
	public File getFile(String name) throws FileNotFoundException;

	/**
	 * ���ݸ��������ƻ�ȡĿ¼����
	 * @param name Ŀ¼����
	 * @return Ŀ¼����
	 * @throws java.io.FileNotFoundException
	 */
	public Directory getDirectory(String name) throws FileNotFoundException;

	/**
	 * ����ָ�����ƻ�ȡ�ļ�(�������򴴽��ļ�)
	 * @param name �ļ�����
	 * @param createIfNeeded �������򴴽�
	 * @return �ļ�����
	 * @throws java.io.FileNotFoundException 
	 * @throws java.io.IOException
	 */
	public File getFile(String name, boolean createIfNeeded) throws FileNotFoundException, IOException;

	/**
	 * ����ָ�����ƻ�ȡĿ¼(�������򴴽�Ŀ¼)
	 * @param name Ŀ¼����
	 * @param createIfNeeded �������򴴽�
	 * @return Ŀ¼����
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public Directory getDirectory(String name, boolean createIfNeeded) throws FileNotFoundException, IOException;

	/**
	 * ��ȡĿ¼���ӽڵ��б�(�ļ���Ŀ¼)
	 * @return Ŀ¼���ӽڵ��б�
	 */
	public List<Node> getChildren();

	/**
	 * ��ȡĿ¼��Ŀ¼�б�
	 * @return Ŀ¼��Ŀ¼�б�
	 */
	public List<Directory> getDirectories();

	/**
	 * ��ȡĿ¼���ļ��б�
	 * @return Ŀ¼���ļ��б�
	 */
	public List<File> getFiles();

	/**
	 * ��ȡĿ¼���ӽڵ��б�(�ļ���Ŀ¼)
	 * @param filer ����
	 * @return Ŀ¼���ӽڵ��б�
	 */
	public List<Node> getChildren(NodeFilter filter);

	/**
	 * ��ȡĿ¼��Ŀ¼�б�
	 * @param filer ����
	 *            directories.
	 * @return Ŀ¼��Ŀ¼�б�
	 */
	public List<Directory> getDirectories(NodeFilter filter);

	/**
	 * ��ȡĿ¼���ļ��б�
	 * @param filer ����
	 * @return Ŀ¼���ļ��б�
	 */
	public List<File> getFiles(NodeFilter filter);

	/**
	 * ɾ����ǰĿ¼
	 * @param recursive �Ƿ�ݹ�ɾ��
	 * @throws java.io.IOException 
	 */
	public void delete(boolean recursive) throws IOException;

	/**
	 * �Ƿ���һ��bundle
	 * @return True/False
	 */
	public boolean isBundle();

}
