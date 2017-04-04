package github.bonapartedawn.common.utils;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * 对象工具类
 * Created by Fuzhong.Yan on 16/11/9.
 */
public class ObjectUtils {
    /**
     * 判断对象为空
     * @param temp
     * @return
     */
    public static boolean isEmpty(Object temp){
        boolean res = false;
        if (temp == null){
            res = true;
        }
        if (temp instanceof String){
            if ("".equals(temp)){
                return true;
            }
        }else if (temp instanceof List){
            List list = (List) temp;
            if (list.size()==0){
                return true;
            }
        }else if (temp instanceof Map){
            Map map  = (Map) temp;
            if (map.size()==0){
                return true;
            }
        }
        return res;
    }

    /**
     * 判断对象不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(Object temp){
        return !isEmpty(temp);
    }


    /**
     * 判断对象均不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(Object ...temp){
        if (temp == null){
            return false;
        }
        boolean res = false;
        for (Object o : temp){
            if (isEmpty(o)){
                res = false;
                break;
            }
            res = true;
        }
        return res;
    }

    /**
     * 序列化对象到文件
     * @param object
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean writeObject(Object object,String path) throws  Exception{
        if (isEmpty(object) || isEmpty(path)){
            return false;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
        return true;
    }

    /**
     * 反序列化对象
     * @param path
     * @return
     * @throws Exception
     */
    public static Object readObject(String path) throws  Exception{
        if (isEmpty(path)){
            return false;
        }
        Object res = null;
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        res = objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return res;
    }
    public static <T> T setValue(T value,T defaultValue){
        T t = value;
        if (ObjectUtils.isEmpty(value)){
            t = defaultValue;
        }
        return t;
    }
    public static  Object[] append(Object temp,Object[] temp1){
        if (temp == null){
            return temp1;
        }
        if (temp1 == null){
            return new Object[]{temp};
        }
        Object[] res = new Object[temp1.length+1];
        res[0] = temp;
        System.arraycopy(temp1,0,res,1,temp1.length);
        return temp1;
    }
    /**
     * 获得模型的值
     * @param model
     * @return
     * @throws
     * @throws Exception
     */
    public static <Model> List<Entry<String, Object>> queryModelProperties(Model model) throws Exception {
        List<Entry<String, Object>> res = new ArrayList<Entry<String,Object>>();
        if (model instanceof Collection<?>) {
            new IllegalArgumentException("objectUtil_model_type_exception");
        }else if (model instanceof Map<?, ?>) {
            List<Entry<String, Object>> temp = convert2Entry((Map<String, Object>) model);
            res.addAll(temp);
        }else {
            List<Entry<String, Object>> temp = queryObjectProperties(model);
            res.addAll(temp);
        }
        return res;
    }
    /**
     * 获得对象的所有的属性键值对
     * @param object
     * @return
     * @throws Exception
     */
    public static List<Entry<String, Object>> queryObjectProperties(Object object) throws Exception {
        List<Entry<String, Object>> res = new ArrayList<Entry<String,Object>>();
        if (object != null) {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                    Object fieldValue = descriptor.getReadMethod().invoke(object);
                    if (fieldValue != null) {
                        Entry<String, Object> temp = convert2Entry(field.getName(), fieldValue);
                        res.add(temp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return res;
    }
    public static <KEY,VALUE> Entry<KEY, VALUE> convert2Entry(KEY key,VALUE value){
        return new AbstractMap.SimpleEntry<KEY, VALUE>(key, value);
    }
    public static <KEY,VALUE> List<Entry<KEY, VALUE>> convert2Entry(Map<KEY, VALUE> map){
        List<Entry<KEY, VALUE>> res = new ArrayList<Entry<KEY,VALUE>>();
        for (Entry<KEY, VALUE> entry : map.entrySet()) {
            res.add(entry);
        }
        return res;
    }

    /**
     * 获得某个对象的某个方法上某个注解
     * @param object
     * @param method
     * @param annotationClass
     * @return
     * @throws NoSuchMethodException
     */
    public static <E> E annotation(Object object, Method method, Class<? extends Annotation> annotationClass){
        E res = null;
        try {
            Method originalMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (originalMethod.isAnnotationPresent(annotationClass)){
                res = (E) originalMethod.getAnnotation(annotationClass);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res;
    }
}
