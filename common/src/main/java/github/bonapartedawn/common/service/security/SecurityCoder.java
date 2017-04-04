package github.bonapartedawn.common.service.security;


import github.bonapartedawn.common.utils.Base64Util;

/**
 * 用于各种加密算法的接口
 * Created by Fuzhong.Yan on 16/11/12.
 */
public abstract class SecurityCoder {
    /**
     * 加密
     * @param data
     * @return
     */
    public   byte[] encrypt(byte[] data) throws Exception{
        return encrypt(data,null);
    }

    /**
     * 加密
     * @param data 待加密的数据
     * @param keys 密钥
     * @return
     * @throws Exception
     */
    public abstract   byte[] encrypt(byte[] data,byte[] ...keys) throws Exception;

    /**
     * 解密
     * @param data 加了密的数据
     * @param keys 密钥
     * @return
     * @throws Exception
     */
    public abstract byte[] decrypt(byte[] data,byte[] ... keys) throws Exception;

    /**
     * 解密
     * @param data
     * @return
     */
    public  byte[] decrypt(byte[] data) throws Exception{
        return decrypt(data,null);
    }


    /**
     * 加密
     * @param data
     * @return
     */
    public  String encrypt(String data) throws Exception{
        return encrypt(data,null);
    }

    /**
     * 加密数据
     * @param data 待加密的数据
     * @param base64Keys base64编码格式密钥
     * @return
     */
    public String encrypt(String data,String ...base64Keys) throws Exception {
        byte[][] keys = null;
        if (base64Keys != null){
//            keys = ByteUtils.toBytes(hexKeys);
            keys = Base64Util.decode(base64Keys);
        }
        if (data == null){
            return null;
        }
        byte[] encode = encrypt(data.getBytes(),keys);
        String res = null;
        if (encode != null){
//            res = ByteUtils.to16Hex(encode);
            res = Base64Util.encode(encode);
        }
        return res;
    }

    /**
     * 解密
     * @param base64Data 待解密的base64编码格式数据
     * @param base64Keys base64编码格式密钥
     * @return
     * @throws Exception
     */
    public  String decrypt(String base64Data,String ...base64Keys) throws Exception{
        byte[][] keys = null;
        if (base64Keys != null){
            keys = Base64Util.decode(base64Keys);
        }
        if (base64Data == null){
            return null;
        }
        byte[] data = Base64Util.decode(base64Data);
        byte[] decode = decrypt(data,keys);
        String res = null;
        if (decode != null){
            res = new String(decode);
        }
        return res;
    }

    /**
     * 解密
     * @param data
     * @return
     */
    public  String decrypt(String data) throws Exception{
        return decrypt(data,null);
    }
}
