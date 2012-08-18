package com.hanghang.codestore.vfs;

import java.io.FileNotFoundException;
import java.security.Principal;

/**
 * �ļ�ϵͳ�Ự����
 * @author mzk
 */
public interface FileSystemSession {
    
    /**
     * �ļ�ϵͳ�ĸ�Ŀ¼
     * @return ��Ŀ¼
     * @throws java.io.FileNotFoundException
     */
    public Directory getRoot() throws FileNotFoundException;
    
    public FileSystem getFileSystem();
    
    /**
     * �����ļ�ϵͳ�Ĵ�С
     * @return ϵͳ�ļ��Ĵ�С
     */
    public long getSize();
    
    /**
     * ��ȡ����Ŀ��ÿռ�
     * @return ����Ŀ��ÿռ��.�����֧��,����-1
     */
    public long getFreeSpace();
    
    /**
     * �ӿ�:��������ʾ�κ�ʵ������һ����¼id
     * @return 
     */
    public Principal getPrincipal();
    
    /**
     * ����·����ȡ�ڵ�
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public Node getNode(String path) throws FileNotFoundException;
    
}
