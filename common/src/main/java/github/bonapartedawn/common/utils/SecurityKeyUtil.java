package github.bonapartedawn.common.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 安全密钥工具类
 * @author Fuzhong.Yan
 */
public class SecurityKeyUtil {
    private static SecureRandom secureRandom;
    static{
        secureRandom = RandomUtil.newInstance();
    }
    /**
     * 生成一个钥匙对
     * @param sigAlg 给钥匙对加密的算法
     * @param seed 安全随机数的种子
     * @param keySize 密钥长度
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair newKeyPair(String sigAlg,String seed,int keySize) throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(sigAlg);
        secureRandom.setSeed(seed.getBytes());
        keyPairGenerator.initialize(keySize,secureRandom);
        return keyPairGenerator.genKeyPair();
    }
    /**
     * 生成一个钥匙对
     * @param sigAlg 给钥匙对加密的算法
     * @param seed 安全随机数的种子
     * @param algorithmParameterSpec 算法参数
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    public static KeyPair newKeyPair(String sigAlg,String seed,AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(sigAlg);
        secureRandom.setSeed(seed.getBytes());
        keyPairGenerator.initialize(algorithmParameterSpec, secureRandom);
        return keyPairGenerator.genKeyPair();
    }
    /**
     * 根据公钥的16进制编码值生成对应的公钥对象
     * @param sigAlg 给公钥加密的算法
     * @param publicEncodeKey
     * @return
     * @throws Exception
     */
    public static  PublicKey convert2PublicKey(String sigAlg,String publicEncodeKey)  throws  Exception{
        KeyFactory kf = newKeyFactory(sigAlg);
        KeySpec a = new X509EncodedKeySpec(ByteUtils.toBytes(publicEncodeKey));
        PublicKey publicKey = kf.generatePublic(a);
        return publicKey;
    }
    /**
     * 根据私钥的16进制编码值生成对应的私钥对象
     * @param sigAlg 给私钥加密的算法
     * @param privateEncodeKey
     * @return
     * @throws Exception
     */
    public static  PrivateKey convert2PrivateKey(String sigAlg,String privateEncodeKey) throws Exception{
        KeyFactory keyFactory = newKeyFactory(sigAlg);
        KeySpec keySpec = new PKCS8EncodedKeySpec(ByteUtils.toBytes(privateEncodeKey));
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    /**
     * 根据公钥的编码值生成对应的公钥对象
     * @param sigAlg 给公钥加密的算法
     * @param publicEncodeKey
     * @return
     * @throws Exception
     */
    public static  PublicKey convert2PublicKey(String sigAlg,byte[] publicEncodeKey)  throws  Exception{
        KeyFactory keyFactory = newKeyFactory(sigAlg);
        KeySpec a = new X509EncodedKeySpec(publicEncodeKey);
        PublicKey publicKey = keyFactory.generatePublic(a);
        return publicKey;
    }
    /**
     * 根据私钥的编码值生成对应的私钥对象
     * @param sigAlg 给私钥加密的算法
     * @param privateEncodeKey
     * @return
     * @throws Exception
     */
    public static  PrivateKey convert2PrivateKey(String sigAlg,byte[] privateEncodeKey) throws Exception{
        KeyFactory keyFactory = newKeyFactory(sigAlg);
        KeySpec keySpec = new PKCS8EncodedKeySpec(privateEncodeKey);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    /**
     * 构建密钥
     * @param publicKey
     * @param privateKey
     * @param keyAlgorithm 公钥和私钥使用的算法
     * @param secretAlgotithm 加密算法
     * @return
     * @throws Exception
     */
    public static SecretKey newSecretKey(byte[] publicKey, byte[] privateKey,String keyAlgorithm,String secretAlgotithm) throws Exception{
        PublicKey pubKey = SecurityKeyUtil.convert2PublicKey(keyAlgorithm, publicKey);
        PrivateKey priKey = SecurityKeyUtil.convert2PrivateKey(keyAlgorithm, privateKey);
        KeyFactory keyFactory = newKeyFactory(keyAlgorithm);
        KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory .getAlgorithm());
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);
        // 生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret(secretAlgotithm);
        return secretKey;
    }
    public static SecretKeySpec newSecretKeySpec(SecretKey secretKey,String secretAlgotithm) throws Exception{
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), secretAlgotithm);
        return secretKeySpec;
    }
    public static SecretKeySpec newSecretKeySpec(byte[] key,String secretAlgotithm) throws Exception{
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, secretAlgotithm);
        return secretKeySpec;
    }
    public static SecretKey  newSecretKey(int keySize,String secretAlgotithm) throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance(secretAlgotithm);
        keyGenerator.init(keySize, secureRandom);
        return keyGenerator.generateKey();
    }
    /**
     * 获得钥匙工厂
     * @param keyAlgorithm 给钥匙加密的算法
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static  KeyFactory newKeyFactory(String keyAlgorithm) throws NoSuchAlgorithmException{
        return KeyFactory.getInstance(keyAlgorithm);
    }
    /**
     * 加密数据
     * @param sigAlg
     * @param key
     * @param data
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static byte[] encrypt(String sigAlg,Key key,byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        Cipher cipher = Cipher.getInstance(sigAlg);//创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    /**
     * 解密数据
     * @param sigAlg
     * @param key
     * @param data
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static byte[] decrypt(String sigAlg,Key key,byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        Cipher cipher = Cipher.getInstance(sigAlg);//创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}
