package framework.util;

import github.bonapartedawn.common.service.security.*;
import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.SecurityUtil;
import org.junit.Test;

import java.security.KeyPair;

/**
 * Created by Fuzhong.Yan on 17/2/5.
 */
public class SecurityUtilTest {
    String content =  "yfz我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国yfz我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国";
    @Test
    public void AES() throws Exception {
        String key = AESUtil.newKey(128);
        SecurityCoder s = SecurityUtil.AES();
        String e = s.encrypt("AES "+content,key);
        String d = s.decrypt(e,key);
        System.out.println("AES e:"+e);
        System.out.println("AES d:"+d);
    }

    @Test
    public void DES() throws Exception {
        String key = DESUtil.newKey();
        SecurityCoder s = SecurityUtil.DES();
        String e = s.encrypt("DES "+content,key);
        String d = s.decrypt(e,key);
        System.out.println("DES e:"+e);
        System.out.println("DES d:"+d);
    }

    @Test
    public void MD5() throws Exception {
        SecurityCoder s = SecurityUtil.MD5();
        String e = s.encrypt("MD5 "+content);
        System.out.println("MD5 e:"+e);
    }
    @Test
    public void MAC() throws Exception {
        String key = Base64Util.encode("test".getBytes());
        SecurityCoder s = SecurityUtil.MAC();
        String e = s.encrypt("MAC "+content,key);
        System.out.println("MAC e:"+e);
    }
    @Test
    public void RSA() throws Exception {
        //生成私钥和公钥;注意明文长度最长字节为(1024/8)-11,超过部分会报异常,建议分段加密
        KeyPair p = RSAUtil.newKeyPair(1024);
        String privateKey = Base64Util.encode(p.getPrivate().getEncoded());
        String publicKey = Base64Util.encode(p.getPublic().getEncoded());
        //私钥加密
        SecurityCoder s = SecurityUtil.RSA();
        String e = s.encrypt("RSA "+"test",privateKey,null);
        //公钥解密
        s = SecurityUtil.RSA();
        String d = s.decrypt(e,null,publicKey);
        System.out.println("RSA e:"+e);
        System.out.println("RSA d:"+d);
    }
    @Test
    public void DH() throws Exception {
        //用户甲的密钥对
        KeyPair A_P = DHCryptUtil.newKeyPair();
        String A_P_privateKey = Base64Util.encode(A_P.getPrivate().getEncoded());
        String A_P_publicKey = Base64Util.encode(A_P.getPublic().getEncoded());
        //用户乙的密钥对
        KeyPair B_P = DHCryptUtil.newKeyPair(A_P_publicKey);
        String B_P_privateKey = Base64Util.encode(B_P.getPrivate().getEncoded());
        String B_P_publicKey = Base64Util.encode(B_P.getPublic().getEncoded());
        //用户甲加密
        SecurityCoder s = SecurityUtil.DH();
        String e = s.encrypt("DH "+content,A_P_privateKey,B_P_publicKey);
        //用户乙解密
        s = SecurityUtil.DH();
        String d = s.decrypt(e,B_P_privateKey,A_P_publicKey);
        System.out.println("DH e:"+e);
        System.out.println("DH d:"+d);
    }
    @Test
    public void sub_encode() throws Exception {
        //生成私钥和公钥;注意明文长度最长字节数为((1024/8)-11),超过部分会报异常,建议分段加密
        KeyPair p = RSAUtil.newKeyPair(1024);
        String privateKey = Base64Util.encode(p.getPrivate().getEncoded());
        String publicKey = Base64Util.encode(p.getPublic().getEncoded());
        byte[] privateKeyByte = Base64Util.decode(privateKey);
        byte[] publicKeyByte = Base64Util.decode(publicKey);
        byte[][] encodeKeys = new byte[2][];
        byte[][] decodeKeys = new byte[2][];
        encodeKeys[0] = privateKeyByte;
        decodeKeys[1] = publicKeyByte;
        //私钥加密
        SecurityCoder s = SecurityUtil.RSA();
        int block_size1= 1024/8-11;
        byte[] e = SecurityUtil.sub_encode(s,content.getBytes(),block_size1,encodeKeys);
        System.out.println("sub_encode:"+Base64Util.encode(e));
        //公钥解密
        s = SecurityUtil.RSA();
        int block_size2= 1024/8;
        byte[] b = SecurityUtil.sub_decrypt(s, e, block_size2,decodeKeys);
        System.out.println("sub_sub_decrypt:"+new String(b));
    }
}