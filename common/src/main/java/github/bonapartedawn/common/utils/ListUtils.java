package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.interfaces.BeanCopy;

import java.util.List;

/**
 * Created by Fuzhong.Yan on 16/11/9.
 */
public  class ListUtils<T,T1> {
    public void copy(List<T> source, List<T1> target, BeanCopy<T,T1> beanCopy) throws Exception{
        if (isNull(source)){
            throw new Exception("SOURCE_IS_NULL");
        }
        if (isNull(target)){
            throw new Exception("TARGET_IS_NULL");
        }
        if (ObjectUtils.isEmpty(beanCopy)){
            throw new Exception("BEAN_COPY_INTERFACE_EMPTY");
        }
        for (T t:source){
            T1 t1= beanCopy.copy(t);
            target.add(t1);
        }
    }
    public static boolean isNull(List temp){
        boolean res = false;
        if (temp == null){
            res = true;
        }
        return res;
    }
    public static boolean isNotNull(List temp){
        return !isNull(temp);
    }
    /**
     * 判断对象为空
     * @param temp
     * @return
     */
    public static boolean isEmpty(List temp){
        boolean res = false;
        if (temp == null || temp.size() ==0){
            res = true;
        }
        return res;
    }

    /**
     * 判断对象不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(List temp){
        return !isEmpty(temp);
    }

    public static boolean isNotEmpty(Object[] temp) {
        return !isEmpty(temp);
    }
    public static boolean isEmpty(Object[] temp) {
        boolean res = false;
        if (temp == null || temp.length == 0){
            res = true;
        }
        return res;
    }
}
