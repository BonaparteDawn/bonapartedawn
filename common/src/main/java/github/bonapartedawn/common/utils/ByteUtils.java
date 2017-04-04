package github.bonapartedawn.common.utils;

import java.math.BigInteger;

/**
 * Created by Fuzhong.Yan on 16/11/12.
 */
public class ByteUtils {
    /**
     * 将byte[]转为16进制的字符串
     * @param bytes byte[]
     */
    public static String to16Hex(byte[] bytes) throws Exception{
        String res = null;
        if (bytes != null && bytes.length !=0) {
            //radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制转换后的字符串
            res = new BigInteger(1, bytes).toString(16);
        }
        return res;
    }
    /**
     * 将byte[]转为16进制的字符串
     * @param bytes byte[]
     */
    public static String toRadix(byte[] bytes,int base) throws Exception{
        String res = null;
        if (bytes != null && bytes.length !=0) {
            //radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制转换后的字符串
            if (base > Character.MAX_RADIX){
                base = Character.MAX_RADIX;
            }else if (base < Character.MIN_RADIX){
                base = Character.MIN_RADIX;
            }
            res = new BigInteger(1, bytes).toString(base);
        }
        return res;
    }
    /**
     * 将16进制的字符串转为字节
     * @param hex
     * @return
     */
    public static byte[] toBytes(String hex){
        byte[] res = null;
        if (hex != null && !"".equals(hex)) {
            res = new BigInteger(hex, 16).toByteArray();
            if (res[0] == 0) {
                byte[] tmp = new byte[res.length - 1];
                System.arraycopy(res, 1, tmp, 0, tmp.length);
                res = tmp;
            }
        }
        return res;
    }

    /**
     * 将16进制的字符串转为字节
     * @param hex
     * @return
     */
    public static byte[][] toBytes(String ...hex){
        byte[][] res = null;
        if (hex!=null){
            res = new byte[hex.length][];
            for (int i=0; i < hex.length ; i++){
                byte [] temp = null;
                if (hex[i] != null){
                    temp = toBytes(hex[i]);
                }
                res[i] = temp;
            }
        }
        return res;
    }


    /**
     * 字节数组连接
     * @param datas
     * @return
     */
    public static byte[] append(byte[] ... datas){
        if (datas == null && datas !=null){
            return  null;
        }
        int len = 0;
        for (byte[] temp:datas){
            if (temp == null){
                continue;
            }
            len+= temp.length;
        }
        byte[] res = new byte[len];
        int index = 0;
        for (byte[] temp:datas){
            if (temp == null){
                continue;
            }
            System.arraycopy(temp, 0, res, index, temp.length);
            index += temp.length;
        }
        return res;
    }
    /**
     *
     * @param bytes
     * @param block_index(注意:第一块的起始下标为0,第二块为1)
     * @param block_size(基于block_index开始读取指定长度的数据)
     * @return
     */
    public static byte[] read(byte[] bytes,int block_index,int block_size){
        if (bytes == null || block_index <0 || block_size<=0){
            return null;
        }
        if (bytes.length < block_index*block_size){
            return null;
        }
        int len = bytes.length;
        int res_len = block_size;
        if ((block_index+1)*block_size>len){
            res_len = len - block_index*block_size;
        }
        if (res_len == 0){
            return null;
        }
        byte[] res = new byte[res_len];
        System.arraycopy(bytes,block_index*block_size,res,0,res_len);
        return res;
    }
}
