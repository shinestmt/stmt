package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 文件系统目录对象
 * @author mzk
 */
public interface Directory extends Node {
	/**
	 * 根据名称创建新目录
	 * @param name 新目录名称
	 * @return Returns 新的目录对象
	 * @throws java.io.IOException
	 */
	public Directory createDirectory(String name) throws IOException;

	/**
	 * 创建新的文件
	 * @param name 新文件名称
	 * @return Returns 新文件对象
	 * @throws java.io.IOException
	 */
	public File createFile(String name) throws IOException;

	/**
	 * 是否是文件系统的根
	 * @return True/False
	 */
	public boolean isRoot();

	/**
	 * 是否包含指定名称的子文件或子目录
	 * @param name 子文件名或子目录名
	 * @return True/False
	 */
	public boolean hasChild(String name);

	/**
	 * 是否包含指定名称的文件
	 * @param name 文件名称
	 * @return True/False
	 */
	public boolean hasFile(String name);

	/**
	 * 是否包含指定名称的目录
	 * @param name 目录名称
	 * @return True/False 
	 */
	public boolean hasDirectory(String name);

	/**
	 * 获取指定名称的文件或目录对象
	 * @param name 文件名或目录名
	 * @return 文件或目录的节点对象
	 * @throws java.io.FileNotFoundException
	 */
	public Node getChild(String name) throws FileNotFoundException;

	/**
	 * 根据给定的名称获取文件对象
	 * @param name 文件名称
	 * @return 文件对象
	 * @throws java.io.FileNotFoundException
	 */
	public File getFile(String name) throws FileNotFoundException;

	/**
	 * 根据给定的名称获取目录对象
	 * @param name 目录名称
	 * @return 目录对象
	 * @throws java.io.FileNotFoundException
	 */
	public Directory getDirectory(String name) throws FileNotFoundException;

	/**
	 * 根据指定名称获取文件(不存在则创建文件)
	 * @param name 文件名称
	 * @param createIfNeeded 不存在则创建
	 * @return 文件对象
	 * @throws java.io.FileNotFoundException 
	 * @throws java.io.IOException
	 */
	public File getFile(String name, boolean createIfNeeded) throws FileNotFoundException, IOException;

	/**
	 * 根据指定名称获取目录(不存在则创建目录)
	 * @param name 目录名称
	 * @param createIfNeeded 不存在则创建
	 * @return 目录对象
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public Directory getDirectory(String name, boolean createIfNeeded) throws FileNotFoundException, IOException;

	/**
	 * 获取目录下子节点列表(文件和目录)
	 * @return 目录下子节点列表
	 */
	public List<Node> getChildren();

	/**
	 * 获取目录下目录列表
	 * @return 目录下目录列表
	 */
	public List<Directory> getDirectories();

	/**
	 * 获取目录下文件列表
	 * @return 目录下文件列表
	 */
	public List<File> getFiles();

	/**
	 * 获取目录下子节点列表(文件和目录)
	 * @param filer 过滤
	 * @return 目录下子节点列表
	 */
	public List<Node> getChildren(NodeFilter filter);

	/**
	 * 获取目录下目录列表
	 * @param filer 过滤
	 *            directories.
	 * @return 目录下目录列表
	 */
	public List<Directory> getDirectories(NodeFilter filter);

	/**
	 * 获取目录下文件列表
	 * @param filer 过滤
	 * @return 目录下文件列表
	 */
	public List<File> getFiles(NodeFilter filter);

	/**
	 * 删除当前目录
	 * @param recursive 是否递归删除
	 * @throws java.io.IOException 
	 */
	public void delete(boolean recursive) throws IOException;

	/**
	 * 是否是一个bundle
	 * @return True/False
	 */
	public boolean isBundle();

}
