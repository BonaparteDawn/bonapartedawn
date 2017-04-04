package github.bonapartedawn.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载器工具类
 * @author Fuzhong.Yan
 */
public class ClassLoaderUtil {

    /**
     * 使用指定类加载器加载指定类（如果已经加载就不需要再加载）
     * @param classLoader
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static void loadClass(ClassLoader classLoader,String className) throws ClassNotFoundException{
        classLoader.loadClass(className);
    }
    /**
     * 使用指定类加载器加载指定路径下的所有的类
     * @param urlClassLoaderClass
     * @param jarPath
     * @return
     * @throws Exception
     */
    public static URLClassLoader loadJarClass(Class<URLClassLoader> urlClassLoaderClass ,String jarPath) throws Exception{
        File file = new File(jarPath);
        URLClassLoader urlClassLoader = null;
        if (file.exists()) {
            URL jarUrl = file.toURI().toURL();
            URL[] urls = new URL[1];
            urls[0] = jarUrl;
            urlClassLoader = ReflectUtil.newInstance(urlClassLoaderClass, (Object)urls);
        }
        return urlClassLoader;
    }
    /**
     * 获jar包里面的所有的类文件
     * @param jarPath
     * @return
     * @throws IOException
     */
    public static List<String> loadJarClass(String jarPath) throws IOException{
        List<String> res = new ArrayList<String>();
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry jarEntry = e.nextElement();
            if (jarEntry.getName().endsWith(".class")) {
                res.add(jarEntry.getName());
            }
        }
        jarFile.close();
        return res;
    }
}
