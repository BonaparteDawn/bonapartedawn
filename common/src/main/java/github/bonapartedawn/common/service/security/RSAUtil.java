package github.bonapartedawn.common.service.security;


import github.bonapartedawn.common.utils.RandomUtil;
import github.bonapartedawn.common.utils.SecurityKeyUtil;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;

/**
 * Created by Fuzhong.Yan on 16/11/12.
 */
public final class RSAUtil extends  SecurityCoder implements Serializable{
    /**
     * 密钥最短长度
     */
    private static int MIN_KEY_SIZE  = (int) Math.pow(2,9);
    /**
     * 密钥最长长度
     */
    private static int MAX_KEY_SIZE  = (int) Math.pow(2,14);
    private static String sigAlg = "RSA";

    /**
     * 加密
     *
     * @param data 待加密的数据
     * @param keys 密钥(数组第一个为私钥,第二个为公钥,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys[0] == null) {
            throw new Exception("rsaUtil_keys_exception");
        }
        if (data == null){
            return  null;
        }
        Key key = recognizeKey(keys);
        return SecurityKeyUtil.encrypt(sigAlg,key,data);
    }

    /**
     * 解密
     *
     * @param data 加了密的数据
     * @param keys 密钥(数组第一个为私钥,第二个为公钥,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        if (data == null){
            return  null;
        }
        Key key = recognizeKey(keys);
        return SecurityKeyUtil.decrypt(sigAlg,key,data);
    }

    /**
     * 识别公钥还是密钥
     * @param keys 密钥(数组第一个为私钥,第二个为公钥,其他省略)
     * @return
     * @throws Exception
     */
    private Key recognizeKey(byte[] ... keys) throws Exception {
        if (keys == null || keys.length < 2 || (keys[0] == null && keys[1]==null) || (keys[0] != null && keys[1]!=null)) {
            throw new Exception("rsaUtil_keys_exception");
        }
        Key key = null;
        if (keys[0] != null){
            key = SecurityKeyUtil.convert2PrivateKey(sigAlg,keys[0]);
        }else {
            key = SecurityKeyUtil.convert2PublicKey(sigAlg,keys[1]);
        }
        return key;
    }

    public static KeyPair newKeyPair(String randomSeed,int size) throws  Exception{
        if (randomSeed == null || "".equals(randomSeed)){
            randomSeed = RandomUtil.generate32UUID();
        }
        if (size < MIN_KEY_SIZE){
            size = MIN_KEY_SIZE;
        }else if (size > MAX_KEY_SIZE){
            size = MAX_KEY_SIZE;
        }
        return SecurityKeyUtil.newKeyPair(sigAlg,randomSeed,size);
    }
    public static KeyPair newKeyPair(int keySize) throws Exception {
        return newKeyPair(null,keySize);
    }
    public static KeyPair newKeyPair(){
        try {
            return newKeyPair(RandomUtil.generate32UUID(),MIN_KEY_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
