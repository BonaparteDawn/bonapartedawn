package framework.security;

import github.bonapartedawn.common.service.security.DHCryptUtil;
import github.bonapartedawn.common.utils.ByteUtils;
import org.junit.Test;

import java.security.KeyPair;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class DHCryptUtilTest {
    private DHCryptUtil dhCryptUtil = new DHCryptUtil();
    /**
     * 甲方密钥对
     */
    private static KeyPair aKeyMap = null;
    /**
     * 乙方密钥对
     */
    private static KeyPair bKeyMap = null;

    static {
        try {
            System.out.println("**************************************");
            //生成甲方密钥
            aKeyMap = DHCryptUtil.newKeyPair();
            //由甲方密钥生成乙方密钥
            String aPublicKey = ByteUtils.to16Hex(aKeyMap.getPublic().getEncoded());
            bKeyMap = DHCryptUtil.newKeyPair(aPublicKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void encrypt() throws Exception {
        try {
            String aPublicKey = ByteUtils.to16Hex(aKeyMap.getPublic().getEncoded());
            String aPrivateKey = ByteUtils.to16Hex(aKeyMap.getPrivate().getEncoded());
            String bPublicKey = ByteUtils.to16Hex(bKeyMap.getPublic().getEncoded());
            String bPrivateKey = ByteUtils.to16Hex(bKeyMap.getPrivate().getEncoded());
            System.out.println("甲方公钥:" + aPublicKey);
            System.out.println("甲方私钥:" + aPrivateKey);
            //甲方加密-乙方解密==============================================================================
            System.out.println(" ===============正向加密解密================== ");
            String aInput = "甲方加密-乙方解密-测试";
            System.out.println("原文: " + aInput);
            // 由甲方公钥，乙方私钥构建密文
            String aCode = dhCryptUtil.encrypt(aInput,bPrivateKey,aPublicKey);
            System.out.println("加密的数据:"+aCode);
            // 由乙方公钥，甲方私钥解密
            String aDecode = dhCryptUtil.decrypt(aCode, aPrivateKey,bPublicKey);
            System.out.println("解密: " + aDecode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decrypt() throws Exception {
        String aPublicKey = ByteUtils.to16Hex(aKeyMap.getPublic().getEncoded());
        String aPrivateKey = ByteUtils.to16Hex(aKeyMap.getPrivate().getEncoded());
        String bPublicKey = ByteUtils.to16Hex(bKeyMap.getPublic().getEncoded());
        String bPrivateKey = ByteUtils.to16Hex(bKeyMap.getPrivate().getEncoded());
        System.out.println("乙方公钥:" + bPublicKey);
        System.out.println("乙方私钥:" + bPrivateKey);
        //乙方解密-甲方加密==============================================================================
        System.out.println(" ===============反向加密解密================== ");
        String bInput = "乙方解密-甲方加密-测试";
        System.out.println("原文: " + bInput);
        // 由乙方公钥，甲方私钥构建密文
        String bCode = dhCryptUtil.encrypt(bInput, aPrivateKey,bPublicKey);
        // 由甲方公钥，乙方私钥解密
        String bDecode = dhCryptUtil.decrypt(bCode, bPrivateKey,aPublicKey);
        System.out.println("解密: " + bDecode);
    }

}