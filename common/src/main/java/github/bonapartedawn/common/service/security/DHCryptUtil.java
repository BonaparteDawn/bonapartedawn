package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.SecurityKeyUtil;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import java.security.KeyPair;
import java.security.spec.AlgorithmParameterSpec;

/**
 * DH 算法(D-H算法)，密钥一致协议。<br>
 * 是由公开密钥密码体制的奠基人Diffie和Hellman所提出的一种思想。<br>
 * 简单的说就是允许两名用户在公开媒体上交换信息以生成"一致"的、可以共享的密钥。<br>
 * 换句话说，就是由甲方产出一对密钥（公钥、私钥），乙方依照甲方公钥产生乙方密钥对（公钥、私钥）。<br>
 * 以此为基线，作为数据传输保密基础，同时双方使用同一种对称加密算法构建本地密钥（SecretKey）对数据加密。<br>
 * 这样，在互通了本地密钥（SecretKey）算法后，甲乙双方公开自己的公钥，使用对方的公钥和刚才产生的私钥加密数据，同时可以使用对方的公钥和自己的私钥对数据解密。<br>
 * 不单单是甲乙双方两方，可以扩展为多方共享数据通讯，这样就完成了网络交互数据的安全通讯！该算法源于中国的同余定理——中国馀数定理。
 * <ul>
 * <li>甲方构建密钥对儿，将公钥公布给乙方，将私钥保留；双方约定数据加密算法；乙方通过甲方公钥构建密钥对儿，将公钥公布给甲方，将私钥保留。</li>
 * <li>甲方使用私钥、乙方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥加密数据，发送给乙方加密后的数据；乙方使用私钥、甲方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥对数据解密。</li>
 * <li>乙方使用私钥、甲方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥加密数据，发送给甲方加密后的数据；甲方使用私钥、乙方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥对数据解密。</li>
 * </ul>
 * @author Fuzhong.Yan
 */
public final class DHCryptUtil extends SecurityCoder{
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "DH";
    /**
     * 安全种子
     */
    private static final String SECURITY_SEED = "securitySeed";
    /**
     * 默认密钥字节数
     * <pre>
     * DH
     * Default KEY_SIZE 1024
     * KEY_SIZE must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;
    /**
     * DH 加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */
    private static final String SECRET_ALGORITHM = "DES";
    /**
     * 初始化甲方密钥对
     * @return
     * @throws Exception
     */
    public static KeyPair newKeyPair() throws Exception {
        return  SecurityKeyUtil.newKeyPair(ALGORITHM, SECURITY_SEED, KEY_SIZE);
    }
    /**
     * 初始化乙方密钥对
     * @param publicKey 甲方16进制公钥
     * @return
     * @throws Exception
     */
    public static KeyPair newKeyPair(String publicKey) throws Exception {
        // 解析甲方公钥
        byte[] keyBytes = Base64Util.decode(publicKey);
        DHPublicKey pubKey = (DHPublicKey) SecurityKeyUtil.convert2PublicKey(ALGORITHM, keyBytes);
        //由甲方公钥构建乙方密钥
        AlgorithmParameterSpec algorithmParameterSpec = pubKey.getParams();
        return SecurityKeyUtil.newKeyPair(ALGORITHM, SECURITY_SEED, algorithmParameterSpec);// 乙方公钥
    }
    /**
     * 构建密钥
     *
     * @param publicKey
     *            公钥(16进制)
     * @param privateKey
     *            私钥(16进制)
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKey(String publicKey, String privateKey) throws Exception {
        byte[] pubKeyBytes = Base64Util.decode(publicKey);
        byte[] priKeyBytes = Base64Util.decode(privateKey);
        return getSecretKey(pubKeyBytes,priKeyBytes);
    }

    /**
     * 构建密钥
     *
     * @param publicKey
     *            公钥
     * @param privateKey
     *            私钥
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
        SecretKey secretKey = SecurityKeyUtil.newSecretKey(publicKey, privateKey, ALGORITHM, SECRET_ALGORITHM);
        return secretKey;
    }
    /**
     * 加密
     *
     * @param data 待加密的数据
     * @param keys 密钥(数组第一个为私钥,第二个为公钥,其他的省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys.length < 2 || keys[0] == null || keys[1] == null){
            //密钥数量异常
            throw new IllegalArgumentException("dh_keys_number_exception");
        }
        if (data == null){
            return null;
        }
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(keys[1], keys[0]);
        // 数据加密
        return SecurityKeyUtil.encrypt(secretKey.getAlgorithm(), secretKey, data);
    }

    /**
     * 解密
     *
     * @param data 加了密的数据
     * @param keys 密钥(数组第一个为私钥,第二个为公钥,其他的省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys.length < 2 || keys[0] == null || keys[1] == null){
            //密钥数量异常
            throw new IllegalArgumentException("dh_keys_number_exception");
        }
        if (data == null){
            return null;
        }
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(keys[1], keys[0]);
        // 数据解密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return SecurityKeyUtil.decrypt(secretKey.getAlgorithm(), secretKey, data);
    }
}