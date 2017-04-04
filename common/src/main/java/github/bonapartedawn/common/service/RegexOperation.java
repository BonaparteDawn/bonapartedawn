package github.bonapartedawn.common.service;

import github.bonapartedawn.common.abstructs.AbstrucRegexSize;
import github.bonapartedawn.common.bean.regex.RegexBean;
import org.springframework.util.Assert;

/**
 * 正则表达式关系操作
 * @author Fuzhong.Yan
 *
 */
public class RegexOperation extends AbstrucRegexSize {

    private String tempRegex = "";
    public RegexOperation() {
    }
    public RegexOperation(String regex) {
        this.tempRegex = regex;
    }
    /**
     * 以指定的正则表达式开头
     * @param regex
     * @return
     */
    public RegexOperation startWith(String regex){
        Assert.hasLength(regex, "regex_LEN0");
        String temp = "^"+regex+tempRegex;
        return new RegexOperation(temp);
    }
    /**
     * 以指定的正则表达式开头
     * @param regex
     * @return
     */
    public RegexOperation startWith(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return startWith(regex.getRegex());
    }

    /**
     * 以指定的正则表达式结束
     * @param regex
     * @return
     */
    public RegexOperation endWith(String regex){
        Assert.hasLength(regex, "regex_LEN0");
        String temp = tempRegex+regex+"$";
        return new RegexOperation(temp);
    }
    /**
     * 以指定的正则表达式结束
     * @param regex
     * @return
     */
    public RegexOperation endWith(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return endWith(regex.getRegex());
    }
    /**
     * 与正则表达式取或
     * @param regex
     * @return
     */
    public RegexOperation or(String regex){
        Assert.hasLength(regex, "regex_LEN0");
        String temp = null;
        if (!"".equals(tempRegex)) {
            temp = "("+tempRegex+")|("+regex+")";
        }else {
            temp = "("+regex+")";
        }
        return new RegexOperation(temp);
    }
    /**
     * 与正则表达式取或
     * @param regex
     * @return
     */
    public RegexOperation or(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return or(regex.getRegex());
    }
    /**
     * 追加正则表达式，传入的参数取或关系
     * @param regexs
     * @return
     */
    public RegexOperation appendOr(String ...regexs){
        Assert.noNullElements(regexs,"regexs_null");
        String temp = "";
        for (String regex : regexs) {
            if (!"".equals(temp)) {
                temp = "("+temp+")"+"|"+"("+regex+")";
            }else {
                temp = "("+regex+")";
            }
        }
        if (!"".equals(tempRegex)) {
            temp = this.tempRegex + "("+temp+")";
        }
        return new RegexOperation("("+temp+")");
    }
    /**
     * 追加正则表达式，传入的参数取或关系
     * @param regexs
     * @return
     */
    public RegexOperation appendOr(RegexBean... regexs){
        Assert.notNull(regexs,"regex_null");
        String[] temp = new String[regexs.length];
        for (int i = 0 ; i < regexs.length ; i++) {
            temp[i] = regexs[i].getRegex();
        }
        return appendOr(temp);
    }
    /**
     * 或后者任意一个字符
     * @param chars
     * @return
     */
    public RegexOperation orAnyChar(String chars){
        Assert.hasLength(chars, "chars_LEN0");
        String temp = null;
        if (!"".equals(tempRegex)) {
            temp = "("+tempRegex+")|(["+chars+"])";
        }else {
            temp = "(["+chars+"])";
        }
        return new RegexOperation(temp);
    }
    /**
     * 或后者任意一个字符
     * @param regex
     * @return
     */
    public RegexOperation orAnyChar(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return orAnyChar(regex.getRegex());
    }
    /**
     * 紧跟一个正则表达式
     * @param regex
     * @return
     */
    public RegexOperation append(String regex){
        Assert.hasLength(regex, "regex_LEN0");
        String temp = null;
        if (!"".equals(tempRegex)) {
            temp = tempRegex+""+regex;
        }else {
            temp = regex;
        }
        return new RegexOperation(temp);
    }
    /**
     * 紧跟一个正则表达式
     * @param regex
     * @return
     */
    public RegexOperation append(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return append(regex.getRegex());
    }
    /**
     * 紧跟后者任意一个字符
     * @param chars
     * @return
     */
    public RegexOperation appendAnyChar(String chars){
        Assert.hasLength(chars, "chars_LEN0");
        String temp = null;
        if (!"".equals(tempRegex)) {
            temp = tempRegex+"["+chars+"]";
        }else {
            temp = "["+chars+"]";
        }
        return new RegexOperation(temp);
    }
    /**
     * 紧跟后者任意一个字符
     * @param regex
     * @return
     */
    public RegexOperation appendAnyChar(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return appendAnyChar(regex.getRegex());
    }
    /**
     * 追加且不包括指定字符串中的任意一个字符
     * @param chars
     * @return
     */
    public RegexOperation appendExceptAnyChar(String chars){
        Assert.hasLength(chars, "chars_LEN0");
        String temp = null;
        if (!"".equals(tempRegex)) {
            temp = tempRegex+"[^"+chars+"]";
        }else {
            temp = "[^"+chars+"]";
        }
        return new RegexOperation(temp);
    }
    /**
     * 追加且不包括指定字符串中的任意一个字符
     * @param regex
     * @return
     */
    public RegexOperation appendExceptAnyChar(RegexBean regex){
        Assert.notNull(regex,"regex_null");
        return appendExceptAnyChar(regex.getRegex());
    }
    public String toString() {
        return tempRegex;
    }
    public RegexBean toBean(){
        return new RegexBean(tempRegex);
    }
    public String getCurrentRegex() {
        return tempRegex;
    }
}