package framework.security;

import github.bonapartedawn.common.service.security.AESUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class AESUtilTest {
    static  String key;
    static {
        try {
            System.out.println("生成密钥====");
            key = AESUtil.newKey(128);
            System.out.println("密钥:"+key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void encrypt() throws Exception {
        AESUtil aesUtil = new AESUtil();
        String origin = "---I_LOVE_YOU---";
        System.out.println("加密==============");
        System.out.println("原始数据:"+origin);
        String  res = aesUtil.encrypt(origin,key);
        System.out.println("加密结果:"+res);
    }

    @Test
    public void decrypt() throws Exception {
        AESUtil aesUtil = new AESUtil();
        String origin = "---I-LOVE-YOU---";
        System.out.println("解密==============");
        String  encrypt = aesUtil.encrypt(origin,key);
        System.out.println("密码数据:"+encrypt);
        String res = aesUtil.decrypt(encrypt,key);
        System.out.println("解密结果:"+res);
    }

}