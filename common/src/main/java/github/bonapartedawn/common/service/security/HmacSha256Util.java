package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.service.MyLogFactory;
import github.bonapartedawn.common.utils.SecurityKeyUtil;
import org.apache.commons.logging.Log;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

/**
 * MAC（Message Authentication Code，消息认证码算法）是含有密钥的散列函数算法，兼容了MD和SHA算法的特性，并在此基础上加入了[密钥]。
 * @author Fuzhong.Yan
 */
public final class HmacSha256Util extends SecurityCoder implements Serializable{
    private static final long serialVersionUID = 1L;
    private Log log = MyLogFactory.productLog(LogType.Default,HmacSha256Util.class);
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
            throw new Exception("hmacSha256Util_keys_empty");
        }
        if (data == null){
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec signingKey = SecurityKeyUtil.newSecretKeySpec(keys[0],mac.getAlgorithm());
        mac.init(signingKey);
        return mac.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 加了密的数据
     * @param keys 密钥
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        log.error("DECRYPT_UNSUPPORTED");
        throw new Exception("DECRYPT_UNSUPPORTED");//不支持解密
    }
}