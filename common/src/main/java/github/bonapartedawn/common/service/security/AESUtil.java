package github.bonapartedawn.common.service.security;


import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.SecurityKeyUtil;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

/**
 * Created by Fuzhong.Yan on 16/11/13.
 */
public final class AESUtil extends  SecurityCoder implements Serializable {
    private  static  final String  sigAlg = "AES";
    /**
     * 加密
     * @param data 待加密的数据
     * @param keys 密钥(数组第一个为密钥,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys[0] == null) {
            throw new Exception("aesUtil_keys_empty");
        }
        if (data == null){
            return  null;
        }
        SecretKeySpec key = SecurityKeyUtil.newSecretKeySpec(keys[0],sigAlg);
        return SecurityKeyUtil.encrypt(sigAlg,key,data);
    }

    /**
     * 解密
     *
     * @param data 加了密的数据
     * @param keys 密钥(数组第一个为密钥,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys[0] == null) {
            throw new Exception("aesUtil_keys_empty");
        }
        if (data == null){
            return  null;
        }
        SecretKeySpec key = SecurityKeyUtil.newSecretKeySpec(keys[0],sigAlg);
        return SecurityKeyUtil.decrypt(sigAlg,key,data);
    }

    /**
     * 生成一个密钥
     * @return
     * @throws Exception
     */
    public static String newKey(int keySize) throws Exception {
//        if (keySize !=128 && keySize!=192 && keySize!=256){
//            keySize = 128;
//        }
        keySize = 128;//java目前只支持128,理论上支持128\192\256
        String res = null;
        SecretKey secretKey = SecurityKeyUtil.newSecretKey(keySize,sigAlg);
        if (secretKey != null){
            res = Base64Util.encode(secretKey.getEncoded());
        }
        return res;
    }
}
