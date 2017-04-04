package github.bonapartedawn.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by Fuzhong.Yan on 17/1/18.
 */
public class RandomUtil {
    /**
     * 创建一个32位的UUID值
     * @return
     */
    public static String generate32UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    public static SecureRandom newInstance(){
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secureRandom;
    }
    public static int rand(){
//        Random random = new Random();
//        return random.nextInt();
        return newInstance().nextInt();
    }
    /**
     * 生成闭区间的整数[0 max)
     * @param max
     * @return
     * @throws Exception
     */
    public static int rand(int max){
//        Random random = new Random();
//        return random.nextInt(max);
        return newInstance().nextInt(max);
    }
    /**
     * 生成闭区间的整数[min max]
     * @param min
     * @param max
     * @return
     * @throws Exception
     */
    public static int rand(int min,int max){
        if (min < 0){
            min = 0;
        }
        if (max < 0) {
            throw new IllegalArgumentException("max_illegal");
        }
        if (min > max){
            int temp = min;
            min = max;
            max = temp;
        }
        return min + rand(max - min+1);
    }
    /**
     * 指定长度的汉语字符串
     * @param length
     * @return
     * @throws Exception
     */
    public static String randChinese(int length) throws Exception {
        if (length <=0){
            length = 4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < length ; i ++){
            byte[] b = new byte[2];
            int h = rand(176,176+39);//获取高位值
            int l = rand(161,161+93);//获取低位值
            b[0] = new Integer(h).byteValue();
            b[1] = new Integer(l).byteValue();
            stringBuffer.append(new String(b, "GBk"));
        }
        return stringBuffer.toString();
    }
    /**
     * 指定长度的大写英文字母
     * @param length
     * @return
     * @throws Exception
     */
    public static String randUpperCaseEnglish(int length) throws Exception {
        if (length <=0){
            length = 4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < length ; i ++){
            byte[] b = new byte[1];
            int h = rand(65,90);
            b[0] = new Integer(h).byteValue();
            stringBuffer.append(new String(b, "GBk"));
        }
        return stringBuffer.toString();
    }

    /**
     * 指定长度的小写英文字母
     * @param length
     * @return
     * @throws Exception
     */
    public static String randLowerCaseEnglish(int length) throws Exception {
        if (length <=0){
            length = 4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < length ; i ++){
            byte[] b = new byte[1];
            int h = rand(97,122);
            b[0] = new Integer(h).byteValue();
            stringBuffer.append(new String(b, "GBk"));
        }
        return stringBuffer.toString();
    }
    /**
     * 指定长度的英文字母
     * @param length
     * @return
     * @throws Exception
     */
    public static String randEnglish(int length) throws Exception {
        if (length <=0){
            length = 4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < length ; i ++){
            byte[] b = new byte[1];
            int h = 0;
            if (randBool()){
                h = rand(97,122);//小写字母
            }else {
                h = rand(65,90);//大写字母
            }
            b[0] = new Integer(h).byteValue();
            stringBuffer.append(new String(b, "GBk"));
        }
        return stringBuffer.toString();
    }

    /**
     * 随机布尔值
     * @return
     * @throws Exception
     */
    public static boolean randBool(){
        return rand(2) != 0;
    }

    /**
     * 指定长度的数字
     * @param length
     * @return
     */
    public static String rangNumber(int length){
        if (length <=0){
            length = 4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < length ; i ++){
            stringBuffer.append(rand(0,9));
        }
        return stringBuffer.toString();
    }
}
