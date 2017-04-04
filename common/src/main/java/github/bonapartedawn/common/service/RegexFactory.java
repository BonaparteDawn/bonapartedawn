package github.bonapartedawn.common.service;

import github.bonapartedawn.common.bean.regex.RegexBean;
import org.springframework.util.Assert;

/**
 * 正则表达式基本元素工厂
 * @author Fuzhong.Yan
 */
public class RegexFactory {
    /**任意*/
    public static final RegexBean ANY =new RegexBean(".");
    /**数字;[0-9]*/
    public static final RegexBean NUMBER =new RegexBean("\\d");
    /**数字除外;[^0-9]*/
    public static final RegexBean NUMBER_EXCLUDE =new RegexBean("\\D");
    /**空白字符;[\t\n\xOB\f\r]*/
    public static final RegexBean SPACE =new RegexBean("\\s");
    /**空白除外*/
    public static final RegexBean SPACE_EXCLUDE =new RegexBean("\\S");
    /**中文字符*/
    public static final RegexBean CHINESE = new RegexBean("[\u4e00-\u9fa5]");
    /**字符;[a-zA-Z0-9]*/
    public static final RegexBean CHAR = new RegexBean("\\w");
    /**字符除外*/
    public static final RegexBean CHAR_EXCLUDE = new RegexBean("\\W");
    /**小写字母;[a-z]*/
    public static final RegexBean LOWERCASE = new RegexBean("[a-z]");
    /**大写字母;[A-Z]*/
    public static final RegexBean UPPERCASE = new RegexBean("[A-Z]");
    /**换行符*/
    public static final RegexBean LINEBREAK= new RegexBean("\\n");
    /**字边界*/
    public static final RegexBean WORDBOUNDARY= new RegexBean("\\b");
    /**非字边界*/
    public static final RegexBean WORDBOUNDARY_EXCLUDE= new RegexBean("\\B");
    /**自定义正则表达式元素Bean*/
    public static final RegexBean selfdefine(String regex){
        Assert.notNull(regex, "regex_null");
        return new RegexBean(regex);
    }
}