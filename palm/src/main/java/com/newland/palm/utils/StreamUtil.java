package com.newland.palm.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author lin
 * @version 2018/5/29 0029
 */
public class StreamUtil {

    /**
     * 关闭流
     * @param closeables
     */
    public static void close(Closeable... closeables){
        if (closeables == null || closeables.length == 0)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
