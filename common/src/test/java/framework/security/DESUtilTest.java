package framework.security;

import github.bonapartedawn.common.service.security.DESUtil;
import github.bonapartedawn.common.service.security.SecurityCoder;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class DESUtilTest {
    static  String key;
    static {
        try {
            System.out.println("生成密钥====");
            key = DESUtil.newKey();
            System.out.println("密钥:"+key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void encrypt() throws Exception {
        SecurityCoder securityCoder = new DESUtil();
        String origin = "---I_LOVE_YOU---";
        System.out.println("加密==============");
        System.out.println("原始数据:"+origin);
        String  res = securityCoder.encrypt(origin,key);
        System.out.println("加密结果:"+res);
    }

    @Test
    public void decrypt() throws Exception {
        SecurityCoder securityCoder = new DESUtil();
        String origin = "---I-LOVE-YOU---";
        System.out.println("解密==============");
        String  encrypt = securityCoder.encrypt(origin,key);
        System.out.println("密码数据:"+encrypt);
        String res = securityCoder.decrypt(encrypt,key);
        System.out.println("解密结果:"+res);
    }

}