package com.hanghang.codestore.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * �ļ�ϵͳ�ļ�����
 * @author mzk
 */
public interface File extends Node {

    /**
     * ��ȡ�ļ���
     * @return �ļ���
     * @throws java.io.IOException
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * ��ȡ�����
     * @return �����
     * @throws java.io.IOException
     */
    public OutputStream getOutputStream() throws IOException;
    
    /**
     * ��ȡ�ļ�bytes�Ĵ�С�ͳ���
     * @return �ļ�bytes�Ĵ�С�ͳ���
     * @throws java.io.IOException
     */
    public long getLength() throws IOException;

}
