package github.bonapartedawn.common.testSample;


import github.bonapartedawn.common.abstructs.MyAdvice;

import java.lang.reflect.Method;

/**
 * Created by Fuzhong.Yan on 17/2/6.
 */
public class DemoAdvice extends MyAdvice<DemoPeople> {

    public DemoAdvice(DemoPeople target) {
        super(target);
    }

    @Override
    public void afterThrowing(Method m, Object[] args, Object target, Throwable subclass) {
        System.out.println("抛出了异常:"+subclass.getMessage());
    }
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("之后。。。");
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("之前。。。");
    }
}