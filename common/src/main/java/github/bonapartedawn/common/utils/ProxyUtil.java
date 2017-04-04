package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.abstructs.MyAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理工具类
 * @author Fuzhong.Yan
 */
public class ProxyUtil {
    private static ClassLoader CLASS_LOADER = EnvironmentUtil.getClassLoader();
    public static <E> E newProxyInstance1(Object object,InvocationHandler handler) {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        return (E) Proxy.newProxyInstance(CLASS_LOADER, interfaces, handler);
    }
    public static <E> E newMyProxyInstance(Object object,Class<? extends MyAdvice> myProxyClass) throws Exception{
        MyAdvice handler = ReflectUtil.newInstance(myProxyClass, object);
        E res = newProxyInstance1(object,handler);
        return res;
    }
    public static <E,I> I newProxyInstance2(E e, Advice ...advices){
        if (advices == null){
            return (I) e;
        }
        I res = null;
        try {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setProxyInterfaces(e.getClass().getInterfaces());
            //使用cglib动态代理
            proxyFactoryBean.setProxyTargetClass(true);
            proxyFactoryBean.setTarget(e);
            for (Advice advice : advices){
                if (ObjectUtils.isNotEmpty(advice)){
                    proxyFactoryBean.addAdvice(advice);
                }
            }
            res = (I) proxyFactoryBean.getObject();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return  res;
    }
}