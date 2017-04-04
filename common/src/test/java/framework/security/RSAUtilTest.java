package framework.security;

import github.bonapartedawn.common.service.security.RSAUtil;
import github.bonapartedawn.common.service.security.SecurityCoder;
import github.bonapartedawn.common.utils.ByteUtils;
import org.junit.Test;

import java.security.KeyPair;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class RSAUtilTest {
    private static KeyPair keyPair;
    private static String publicKey;
    private static String privateKey;
    static {
        keyPair = RSAUtil.newKeyPair();
        try {
            publicKey = ByteUtils.to16Hex(keyPair.getPublic().getEncoded());
            privateKey = ByteUtils.to16Hex(keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void encrypt() throws Exception {
        SecurityCoder securityCoder = new RSAUtil();
        String origin = "---I_LOVE_YOU---";
        System.out.println("加密==============");
        System.out.println("原始数据:"+origin);
        String  res = securityCoder.encrypt(origin,privateKey,null);
        System.out.println("加密结果:"+res);
    }
    @Test
    public void decrypt() throws Exception {
        SecurityCoder securityCoder = new RSAUtil();
        String origin = "---I-LOVE-YOU---";
        System.out.println("解密==============");
        String  encrypt = securityCoder.encrypt(origin,privateKey,null);
        System.out.println("密码数据:"+encrypt);
        String res = securityCoder.decrypt(encrypt,null,publicKey);
        System.out.println("解密结果:"+res);
    }

}