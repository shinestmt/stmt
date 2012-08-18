package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.security.Principal;

/**
 * 文件系统会话对象
 * @author mzk
 */
public interface FileSystemSession {
    
    /**
     * 文件系统的根目录
     * @return 根目录
     * @throws java.io.FileNotFoundException
     */
    public Directory getRoot() throws FileNotFoundException;
    
    public FileSystem getFileSystem();
    
    /**
     * 检索文件系统的大小
     * @return 系统文件的大小
     */
    public long getSize();
    
    /**
     * 获取空余的可用空间
     * @return 空余的可用空间或.如果不支持,返回-1
     */
    public long getFreeSpace();
    
    /**
     * 接口:可用来表示任何实体对象和一个登录id
     * @return 
     */
    public Principal getPrincipal();
    
    /**
     * 根据路径获取节点
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public Node getNode(String path) throws FileNotFoundException;
    
}
