package github.bonapartedawn.common.service;

import org.springframework.core.env.PropertyResolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fuzhong.Yan on 16/11/30.
 */
public class ENV implements PropertyResolver {
    /**
     * 属性
     */
    private Properties properties;

    /**
     * 是否包含某个属性
     */
    public boolean containsProperty(String s) {
        boolean res = false;
        String v = properties.getProperty(s);
        if ( v != null ){
            res = true;
        }
        return res;
    }

    /**
     * 获取属性值 如果找不到返回null
     */
    public String getProperty(String s) {
        if (!this.containsProperty(s)){
            return null;
        }
        return properties.getProperty(s);
    }

    /**
     * 获取属性值，如果找不到返回默认值
     * @param s
     * @param s1
     * @return
     */
    public String getProperty(String s, String s1) {
        if (!this.containsProperty(s)){
            return  s1;
        }
        return getProperty(s);
    }

    /**
     * 获取指定类型的属性值，找不到返回null
     */
    public <T> T getProperty(String s, Class<T> aClass) {
        return getProperty(s,aClass,null);
    }

    /**
     * 获取指定类型的属性值，找不到返回默认值
     * @param s
     * @param aClass
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getProperty(String s, Class<T> aClass, T t) {
        if (!this.containsProperty(s)){
            return t;
        }
        String v = this.getProperty(s);
        T tt = null;
        if (Short.class.equals(aClass)){
            tt = (T) Short.valueOf(v);
        }else if (Integer.class.equals(aClass)){
            tt = (T) Integer.valueOf(v);
        }else if (Float.class.equals(aClass)){
            tt = (T) Float.valueOf(v);
        }else if (Double.class.equals(aClass)){
            tt = (T) Double.valueOf(v);
        }else if ((Long.class.equals(aClass))){
            tt = (T) Long.valueOf(v);
        }else if (Byte.class.equals(aClass)){
            tt = (T) Byte.valueOf(v);
        }else if (BigDecimal.class.equals(aClass)){
            tt = (T) new BigDecimal(v);
        }else if (BigInteger.class.equals(aClass)){
            tt = (T) new BigInteger(v);
        }else if (String.class.equals(aClass)){
            tt = (T) v;
        }else{
            tt = t;
        }
        return  tt;
    }

    /**
     * 获取属性值为某个Class类型，找不到返回null，如果类型不兼容将抛出ConversionException
     * @param s
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Class<T> getPropertyAsClass(String s, Class<T> aClass) {
        return null;
    }

    /**
     * 获取属性值，找不到抛出异常IllegalStateException
     * @param s
     * @return
     * @throws IllegalStateException
     */
    public String getRequiredProperty(String s) throws IllegalStateException {
        return null;
    }

    /**
     * 获取指定类型的属性值，找不到抛出异常IllegalStateExceptio
     * @param s
     * @param aClass
     * @param <T>
     * @return
     * @throws IllegalStateException
     */
    public <T> T getRequiredProperty(String s, Class<T> aClass) throws IllegalStateException {
        return null;
    }

    /**
     * 替换文本中的占位符（${key}）到属性值，找不到不解析
     * @param s
     * @return
     */
    public String resolvePlaceholders(String s) {
        if (s == null){
            return  s;
        }
        String envPlaceholderRegex ="\\$\\{\\s*[\\w-]+\\s*\\}";
        String trimENVPlaceholderRegex = "[\\$\\{\\s\\}]";
        if (this.containsProperty("envPlaceholderRegex")){
            envPlaceholderRegex = this.getProperty("envPlaceholderRegex");//从外部获取占位符表达式
        }
        if (this.containsProperty("trimENVPlaceholderRegex")){
            trimENVPlaceholderRegex = this.getProperty("trimENVPlaceholderRegex");
        }
        Pattern pattern = Pattern.compile(envPlaceholderRegex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()){
            String group = matcher.group();
            String key = group.replaceAll(trimENVPlaceholderRegex,"");
            if (this.containsProperty(key)){
                s = s.replace(group,this.getProperty(key));
            }
        }
        return s;
    }

    /**
     * 替换文本中的占位符（${key}）到属性值，找不到抛出异常IllegalArgumentException
     * @param s
     * @return
     * @throws IllegalArgumentException
     */
    public String resolveRequiredPlaceholders(String s) throws IllegalArgumentException {
        if (s == null){
            return  s;
        }
        String envPlaceholderRegex ="\\$\\{\\s*[\\w-]+\\s*\\}";
        String trimENVPlaceholderRegex = "[\\$\\{\\s\\}]";
        if (this.containsProperty("envPlaceholderRegex")){
            envPlaceholderRegex = this.getProperty("envPlaceholderRegex");//从外部获取占位符表达式
        }
        if (this.containsProperty("trimENVPlaceholderRegex")){
            trimENVPlaceholderRegex = this.getProperty("trimENVPlaceholderRegex");
        }
        Pattern pattern = Pattern.compile(envPlaceholderRegex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()){
            String group = matcher.group();
            String key = group.replaceAll(trimENVPlaceholderRegex,"");
            if (!this.containsProperty(key)){
                throw  new IllegalArgumentException("NOT_EXISTED_PLACEHOLDER_KEY");
            }else{
                s = s.replace(group,this.getProperty(key));
            }
        }
        return s;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
