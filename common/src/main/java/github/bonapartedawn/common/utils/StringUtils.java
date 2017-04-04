package github.bonapartedawn.common.utils;

/**
 * Created by Fuzhong.Yan on 16/11/9.
 */
public class StringUtils{
    /**
     * 判断字符串是空
     * @param temp
     * @return
     */
    public static boolean isEmpty(String temp){
        boolean res = false;
        if (temp == null || "".equals(temp)){
            res = true;
        }
        return res;
    }

    /**
     * 判断字符串不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(String temp){
        return !isEmpty(temp);
    }

    /**
     * 截取指定长度的字符串
     * @return
     */
    public static String subString(String content,int start,int end){
        if (isEmpty(content)){
            return content;
        }
        if (content.length() < end){
            end = content.length();
        }
        if (start > end){
            start = end;
        }
        return  content.substring(start,end);
    }

}
