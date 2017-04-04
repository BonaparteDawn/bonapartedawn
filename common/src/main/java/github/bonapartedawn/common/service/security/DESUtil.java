package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.SecurityKeyUtil;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

/**
 DES加密介绍
 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现
 */
public final class DESUtil extends  SecurityCoder implements Serializable{
    private static final String sigAlg = "DES";
    private static final int keySize = 56;
    /**
     * 加密
     *
     * @param data 待加密的数据
     * @param keys 密钥(数组第一个为密钥,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (keys == null || keys[0] == null) {
            throw new Exception("desUtil_keys_empty");
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
            throw new Exception("desUtil_keys_empty");
        }
        if (data == null){
            return  null;
        }
        SecretKeySpec key = SecurityKeyUtil.newSecretKeySpec(keys[0],sigAlg);
        return SecurityKeyUtil.decrypt(sigAlg,key,data);
    }
    public static String newKey() throws Exception{
        String res = null;
        SecretKey secretKey = SecurityKeyUtil.newSecretKey(keySize,sigAlg);
        if (secretKey != null){
            res = Base64Util.encode(secretKey.getEncoded());
        }
        return res;
    }
}
