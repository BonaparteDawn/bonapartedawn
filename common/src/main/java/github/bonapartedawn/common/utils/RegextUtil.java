package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.bean.regex.RegexBean;
import github.bonapartedawn.common.service.RegexOperation;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegextUtil {
    public static RegexOperation operation(){
        return new RegexOperation();
    }
    public static RegexOperation operation(String regex){
        Assert.notNull(regex, "regex_null");
        return new RegexOperation(regex);
    }
    public static RegexOperation operation(RegexBean regex){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(regex.getRegex(), "regex_string_null");
        return new RegexOperation(regex.getRegex());
    }
    /**
     * 替换掉所有满足正则表达式规则地方的字符串
     * @param regex
     * @param input
     * @param replacement
     * @return
     */
    public static String replaceAll(String regex,String input,String replacement){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Assert.notNull(replacement, "replacement_null");
        return input.replaceAll(regex, replacement);
    }
    /**
     * 替换掉所有满足正则表达式规则地方的字符串
     * @param regex
     * @param input
     * @param replacement
     * @return
     */
    public static String repalaceAll(RegexBean regex, String input, String replacement){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Assert.notNull(replacement, "replacement_null");
        return replaceAll(regex.getRegex(),input, replacement);
    }
    /**
     * 替换掉第一次出现正则表达式规则地方的字符串
     * @param regex
     * @param input
     * @param replacement
     * @return
     */
    public static String replaceFirst(String regex,String input,String replacement){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Assert.notNull(replacement, "replacement_null");
        return input.replaceFirst(regex, replacement);
    }
    /**
     * 替换掉第一次出现正则表达式规则地方的字符串
     * @param regex
     * @param input
     * @param replacement
     * @return
     */
    public static String replaceFirst(RegexBean regex, String input, String replacement){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Assert.notNull(replacement, "replacement_null");
        return replaceFirst(regex.getRegex(),input, replacement);
    }
    /**
     * 在input是否找到满足指定正则表达式的字符串
     * @param regex
     * @param input
     * @return
     */
    public static boolean find(String regex,String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(input);
        return m.find();
    }
    /**
     * 在input是否找到满足指定正则表达式的字符串
     * @param regex
     * @param input
     * @return
     */
    public static boolean find(RegexBean regex, String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        return find(regex.getRegex(), input);
    }
    /**
     * 在input找到满足指定正则表达式的字符串的数量
     * @param regex
     * @param input
     * @return
     */
    public static boolean findSize(String regex,String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(input);
        return m.find();
    }
    /**
     * 在input找到满足指定正则表达式的字符串的数量
     * @param regex
     * @param input
     * @return
     */
    public static boolean findSize(RegexBean regex, String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        return findSize(regex.getRegex(),input);
    }
    /**
     * 在input找到满足指定正则表达式的字符串
     * @param regex
     * @param input
     * @return
     */
    public static List<String> findGroup(String regex,String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        Pattern pattern = Pattern.compile(regex);
        List<String> res = new ArrayList<String>();
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            res.add(m.group());
        }
        return res;
    }
    /**
     * 在input找到满足指定正则表达式的字符串
     * @param regex
     * @param input
     * @return
     */
    public static List<String> findGroup(RegexBean regex, String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        return findGroup(regex.getRegex(), input);
    }
    /**
     * 匹配input是否满足正则表达式所规定的格式
     * @param regex
     * @param input
     * @return
     */
    public static boolean match(String regex,String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        if (regex.charAt(0) != '^') {
            regex = "^"+regex;
        }
        if (regex.charAt(regex.length()-1) != '$') {
            regex = regex +"$";
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(input);
        return m.find();
    }
    /**
     * 匹配input是否满足正则表达式所规定的格式
     * @param regex
     * @param input
     * @return
     */
    public static boolean match(RegexBean regex, String input){
        Assert.notNull(regex, "regex_null");
        Assert.notNull(input, "input_null");
        return match(regex.getRegex(), input);
    }
}