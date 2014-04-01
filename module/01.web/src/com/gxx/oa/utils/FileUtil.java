package com.gxx.oa.utils;

import com.gxx.oa.interfaces.SymbolInterface;

import java.io.*;

/**
 * 文件工具类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:33
 */
public class FileUtil implements SymbolInterface {
    /**
     * 缓存大小
     */
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * 拷贝文件
     * @param src
     * @param dst
     */
    public static void copy(File src, File dst)
    {
        try
        {
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0)
                {
                    out.write(buffer);
                }
            } finally
            {
                if (null != in)
                {
                    in.close();
                }
                if (null != out)
                {
                    out.close();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
