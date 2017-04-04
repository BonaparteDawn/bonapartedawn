package github.bonapartedawn.common.abstructs;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Aspect父类
 * Created by Fuzhong.Yan on 16/11/27.
 */
public abstract class Aspect<E> {
    public abstract  Object run(ProceedingJoinPoint proceedingJoinPoint, E e);
}