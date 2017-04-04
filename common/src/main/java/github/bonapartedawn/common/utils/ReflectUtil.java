package github.bonapartedawn.common.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * 反射工具类
 * Created by Fuzhong.Yan on 17/2/5.
 */
public class ReflectUtil {
    /**
     * 根据有序参数和类生成该类的实体
     * @param e_class 实体类
     * @param args 有序参数
     * @param <E>
     * @return
     * @throws Exception
     */
    public  static <E> E newInstance(Class<?> e_class,Object ...args) throws Exception{
        Assert.notNull(e_class,"reflectUtil_class_null");
        Assert.notEmpty(args,"reflectUtil_args_empty");
        Class<?>[] classes = new Class<?>[args.length];
        for (int i= 0 ; i < args.length; i++){
            classes[i] = args[i].getClass();
        }
        Constructor<?> constructor = e_class.getDeclaredConstructor(classes);
        return (E) constructor.newInstance(args);
    }
    /**
     * 生成该类的实体
     * @param e_class 实体类
     * @param <E>
     * @return
     * @throws Exception
     */
    public  static <E> E newInstance(Class<?> e_class) throws Exception{
        Assert.notNull(e_class,"reflectUtil_class_null");
        Constructor<?> constructor = e_class.getDeclaredConstructor();
        return (E) constructor.newInstance();
    }
    /**
     * 使用指定加载器生成一个指定的对象
     * @param classLoader 加载器
     * @param className 类名
     * @param args 构造函数的参数
     * @return
     * @throws ClassNotFoundException
     */
    public static <E> E newInstance(ClassLoader classLoader,String className,Object ...args) throws Exception{
        classLoader.loadClass(className);
        Class<E> temp = (Class<E>) Class.forName(className, true, classLoader);
        return ReflectUtil.newInstance(temp,args);
    }
    /**
     * 使用指定加载器生成一个指定的对象
     * @param classLoader 加载器
     * @param className 类
     * @return
     * @throws ClassNotFoundException
     */
    public static <E> E newInstance(ClassLoader classLoader,String className) throws Exception{
        classLoader.loadClass(className);
        Class<E> temp = (Class<E>) Class.forName(className, true, classLoader);
        return ReflectUtil.newInstance(temp);
    }
}
