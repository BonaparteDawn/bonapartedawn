package github.bonapartedawn.common.abstructs;


import github.bonapartedawn.common.service.security.SecurityCoder;
import github.bonapartedawn.common.utils.IOUtils;

import java.io.*;

/**
 * 自定义加载父类
 * @author Fuzhong.Yan
 */
public abstract class MyClassLoader extends ClassLoader {
    /**
     * 类加载器的名字
     */
    private String name;
    /**
     * 加载类的路径
     */
    private String path;
    /**
     * .class文件扩展名
     */
    private final String fileType = ".class";
    /**
     * .class文件解密器
     */
    private SecurityCoder securityCoder;

    public MyClassLoader(String name) {
        super();//让系统加载器成为该类的加载器的父类加载器
        this.name = name;
    }

    public MyClassLoader(String name, String path) {
        super();//让系统加载器成为该类的加载器的父类加载器
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(SecurityCoder securityCoder, String name) {
        super(); // 让系统加载器成为该类的加载器的父类加载器
        this.name = name;
        this.securityCoder = securityCoder;
    }

    public MyClassLoader(SecurityCoder securityCoder, String name, String path) {
        super(); // 让系统加载器成为该类的加载器的父类加载器
        this.securityCoder = securityCoder;
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(ClassLoader parent, String name) {
        super(parent); // 显示指定该类加载器的父加载器
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent, String name, String path) {
        super(parent); // 显示指定该类加载器的父加载器
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(ClassLoader parent, SecurityCoder securityCoder, String name) {
        super(parent); // 显示指定该类加载器的父加载器
        this.name = name;
        this.securityCoder = securityCoder;
    }

    public MyClassLoader(ClassLoader parent, SecurityCoder securityCoder, String name, String path) {
        super(parent); // 显示指定该类加载器的父加载器
        this.name = name;
        this.securityCoder = securityCoder;
        this.path = path;
    }

    /**
     * JVM调用的加载器的方法
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SecurityCoder getSecurityCoder() {
        return securityCoder;
    }

    public void setSecurityCoder(SecurityCoder securityCoder) {
        this.securityCoder = securityCoder;
    }

    /**
     * 将数据读取出来
     *
     * @param path
     * @param fileName
     * @param fileTyep
     * @return
     * @throws FileNotFoundException
     */
    public abstract InputStream readResource(String path, String fileName, String fileTyep) throws FileNotFoundException;

    /**
     * 读取class文件作为二进制流放入到byte数组中去
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        InputStream in = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
            name = name.replace(".", File.separator);
            in = readResource(path, name, fileType);
            baos = new ByteArrayOutputStream();
            byte[] bytes = IOUtils.copy2Bytes(in);
            if (securityCoder != null) {
                bytes = securityCoder.decrypt(bytes);
            }
            baos.write(bytes);
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
