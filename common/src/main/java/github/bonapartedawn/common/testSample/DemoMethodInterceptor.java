package github.bonapartedawn.common.testSample;

import github.bonapartedawn.common.abstructs.MyAdvice;

import java.lang.reflect.Method;

/**
 * Created by Fuzhong.Yan on 17/2/25.
 */
public class  DemoMethodInterceptor <E> extends MyAdvice<E> {

    @Override
    public void afterThrowing(Method m, Object[] args, Object target, Throwable subclass) {
        System.out.println("异常");
    }

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("之后");
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("之前");
    }
}