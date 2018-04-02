package com.lin.palmmz.util;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class CollectionUtil {

    /**
     * List集合转换为数组
     *
     * @param items  List数据
     * @param tClass 数据的类型class
     * @param <T>    Class
     * @return 转换完成后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> items, Class<T> tClass) {
        if (items == null || items.size() == 0)
            return null;
        int size = items.size();
        try {
            T[] array = (T[]) Array.newInstance(tClass, size);
            return items.toArray(array);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
