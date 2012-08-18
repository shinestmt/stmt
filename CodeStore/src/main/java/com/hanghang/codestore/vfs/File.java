package com.hanghang.codestore.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件系统文件对象
 * @author mzk
 */
public interface File extends Node {

    /**
     * 获取文件流
     * @return 文件流
     * @throws java.io.IOException
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * 获取输出流
     * @return 输出流
     * @throws java.io.IOException
     */
    public OutputStream getOutputStream() throws IOException;
    
    /**
     * 获取文件bytes的大小和长度
     * @return 文件bytes的大小和长度
     * @throws java.io.IOException
     */
    public long getLength() throws IOException;

}
