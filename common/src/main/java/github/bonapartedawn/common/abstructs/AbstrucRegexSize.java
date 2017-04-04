package github.bonapartedawn.common.abstructs;

import github.bonapartedawn.common.bean.regex.RegexBean;
import org.springframework.util.Assert;

/**
 * Created by Fuzhong.Yan on 17/1/28.
 */
public abstract class AbstrucRegexSize {
    /**
     * 数量等于
     * @param length
     * @return
     */
    public RegexBean EQ(int length){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        if (length < 1) {
            length = 1;
        }
        String temp = "("+getCurrentRegex()+")"+"{"+length+"}";
        return new RegexBean(temp);
    }
    /**
     * 数量大于等于
     * @param length
     * @return
     */
    public RegexBean GE(int length){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        if (length < 1) {
            length = 1;
        }
        String temp = "("+getCurrentRegex()+")"+"{"+length+",}";
        return new RegexBean(temp);
    }
    /**
     * 数量介于之间(且包括)
     * @return
     */
    public RegexBean between(int minLengh, int maxLength){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        if (minLengh < 1) {
            minLengh = 1;
        }
        if (maxLength < 1) {
            maxLength = 1;
        }
        if (maxLength < minLengh) {
            int temp = minLengh;
            minLengh = maxLength;
            maxLength = temp;
        }
        String temp = "("+getCurrentRegex()+")"+"{"+minLengh+","+maxLength+"}";
        return new RegexBean(temp);
    }
    /**
     * 数量小于等于1
     * @return
     */
    public RegexBean LEOne(){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        String temp ="("+getCurrentRegex()+")"+"?";
        return new RegexBean(temp);
    }
    /**
     * 数量大于等于1
     * @return
     */
    public RegexBean GEOne(){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        String temp ="("+getCurrentRegex()+")"+"+";
        return new RegexBean(temp);
    }
    /**
     * 数量大于等于0
     * @return
     */
    public RegexBean GEZero(){
        Assert.hasLength(getCurrentRegex(), "regex_len0");
        String temp ="("+getCurrentRegex()+")"+"*";
        return new RegexBean(temp);
    }
    public abstract String getCurrentRegex();
}
