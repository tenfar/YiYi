package com.tenfar.yiyi.util;

import com.tenfar.yiyi.common.enums.BaseEnum;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tenfar
 */
public class ClassUtil {
    /**
     * 获取当前类的所有实现子类
     *
     * @param superClass
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getAllAssignedClass(Class<?> superClass) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> c : getClasses(superClass)) {
            if (superClass.isAssignableFrom(c) && !superClass.equals(c)) {
                classes.add(c);
            }
        }
        return classes;
    }

    public static List<Class<?>> getClasses(Class<?> cls) throws ClassNotFoundException {
        String pk = cls.getPackage().getName();
        String path = pk.replace(".", "/");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        if (url != null) {
            return getClasses(new File(url.getFile()), pk);
        }
        return null;
    }

    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!dir.exists()) {
            return classes;
        }
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(getClasses(file, pk + "." + file.getName()));
            }
            String fileName = file.getName();
            if (fileName.endsWith(".class")) {
                classes.add(Class.forName(pk + "." + fileName.substring(0, fileName.length() - 6)));
            }
        }
        return classes;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        for (Class<?> c : getAllAssignedClass(BaseEnum.class)) {
            System.out.println(c);
        }
    }
}
