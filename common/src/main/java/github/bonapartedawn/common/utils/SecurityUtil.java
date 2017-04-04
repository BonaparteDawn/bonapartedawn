package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.service.security.*;
import org.springframework.util.Assert;

/**
 * 安全工具类
 * Created by Fuzhong.Yan on 17/2/5.
 */
public class SecurityUtil {
    public static SecurityCoder AES(){
        return new AESUtil();
    }

    public static SecurityCoder DES(){
        return new DESUtil();
    }
    public static SecurityCoder MD5(){
        return new MD5Util();
    }
    public static SecurityCoder RSA(){
        return new RSAUtil();
    }
    public static SecurityCoder DH(){
        return new DHCryptUtil();
    }
    public static SecurityCoder MAC(){
        return new HmacSha256Util();
    }
    public static SecurityCoder SHA512(){
        return new SHA512Util();
    }
    public static SecurityCoder BCryptUtil(){
        return new BCryptUtil();
    }
    /**
     * 分段加密
     * @param securityCoder 加密器
     * @param temp 待加密数据
     * @param block_size 分段加密块数大小
     * @param keys 密钥
     * @return
     */
    public static byte[] sub_encode(SecurityCoder securityCoder,byte[] temp,int block_size,byte[] ...keys) throws Exception {
        Assert.notNull(securityCoder,"securityUtil_securityCoder_null");
        Assert.notNull(temp,"securityUtil_temp_null");
        if (block_size <= 0 ){
            block_size = (int) Math.pow(2,9);
        }
        byte[] res = null;
        for (int i=0;;i++){
            byte[] bytes = ByteUtils.read(temp,i,block_size);
            if (bytes==null){
                break;
            }
            res = ByteUtils.append(res,securityCoder.encrypt(bytes,keys));
        }
        return res;
    }
    /**
     * 分段解密
     * @param securityCoder 解密器
     * @param temp 待解密数据
     * @param block_size 分段解密块数大小
     * @param keys 密钥
     * @return
     */
    public static byte[] sub_decrypt(SecurityCoder securityCoder,byte[] temp,int block_size,byte[] ...keys) throws Exception {
        Assert.notNull(securityCoder,"securityUtil_securityCoder_null");
        Assert.notNull(temp,"securityUtil_temp_null");
        if (block_size <= 0 ){
            block_size = (int) Math.pow(2,9);
        }
        byte[] res = null;
        for (int i=0;;i++){
            byte[] bytes = ByteUtils.read(temp,i,block_size);
            if (bytes==null){
                break;
            }
            res = ByteUtils.append(res,securityCoder.decrypt(bytes,keys));
        }
        return res;
    }
}
