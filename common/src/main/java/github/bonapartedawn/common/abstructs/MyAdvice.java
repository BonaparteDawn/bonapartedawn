package github.bonapartedawn.common.abstructs;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Advice
 * @author Fuzhong.Yan
 */
public abstract class MyAdvice<E> implements InvocationHandler,MethodBeforeAdvice,AfterReturningAdvice,ThrowsAdvice{
    protected E target;
    public MyAdvice() {
        super();
    }
    public MyAdvice(E target) {
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Assert.notNull(target,"myProxy_invoke_target_null");
        Object result = null;
        try {
            before(method,args,target);
            result = method.invoke(target, args);
            afterReturning(result,method,args,target);
        }catch (Exception e){
            afterThrowing(method,args,target,e);
            throw e;
        }
        return result;
    }
    public abstract void afterThrowing(Method m, Object[] args, Object target, Throwable subclass);
}
