package github.bonapartedawn.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64编码器工具类
 * Created by Fuzhong.Yan on 16/11/12.
 */
public final class Base64Util{
    /**
     * 编码
     * @param data 待编码的数据
     * @return
     * @throws Exception
     */
    public static String encode(byte[] data) throws Exception {
        if (data == null){
            return  null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    /**
     * 编码
     * @param data 待编码的数据
     * @return
     * @throws Exception
     */
    public static String[] encode(byte[] ...data) throws Exception {
        if (data == null){
            return  null;
        }
        String[] res = new String[data.length];
        for (int i=0;i<data.length;i++){
            res[i] = encode(data[i]);
        }
        return res;
    }
    /**
     * 解码
     * @param encodeData 编码的数据
     * @return
     * @throws Exception
     */
    public static byte[][] decode(String... encodeData) throws Exception {
        if (encodeData==null){
            return  null;
        }
        byte[][] res = new byte[encodeData.length][];
        for (int i=0;i<encodeData.length;i++){
            res[i] = decode(encodeData[i]);
        }
        return  res;
    }
    /**
     * 解码
     * @param encodeData 编码的数据
     * @return
     * @throws Exception
     */
    public static byte[] decode(String encodeData) throws Exception {
        if (encodeData==null){
            return  null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        return  decoder.decodeBuffer(encodeData);
    }
}
