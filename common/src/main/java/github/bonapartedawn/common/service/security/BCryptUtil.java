package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.ObjectUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

/**
 * bcrypt是专门为密码存储而设计的算法，基于Blowfish加密算法变形而来，
 * 由Niels Provos和David Mazières发表于1999年的USENIX。
 * bcrypt最大的好处是有一个参数（work factor)，可用于调整计算强度，
 * 而且work factor是包括在输出的摘要中的。随着攻击者计算能力的提高，使
 * 用者可以逐步增大work factor，而且不会影响已有用户的登陆。　bcrypt
 * 经过了很多安全专家的仔细分析，使用在以安全著称的OpenBSD中，一般认为它比
 * PBKDF2更能承受随着计算能力加强而带来的风险。bcrypt也有广泛的函数库支
 * 持，因此我们建议使用这种方式存储密码。
 * @author Fuzhong.Yan
 * 2017年3月13日
 */
public final class BCryptUtil extends SecurityCoder{
    /**
     * 该算法不支持解密
     */
    @Override
    @LogInfo(name="BcriptyUtil解密",description="BcriptyUtil不支持解密",type=LogType.Default)
    public byte[] decrypt(byte[] data, byte[]... arg1) throws Exception {
        throw new Exception("DECRYPT_UNSUPPORTED");//不支持解密
    }
    /**
     * 解密
     * @param data 加了密的数据
     * @param salts 密钥(数组第一个为盐,其他省略)
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name="BcriptyUtil加密",type=LogType.Default)
    public byte[] encrypt(byte[] data, byte[]... salts) throws Exception {
        if (data == null){
            return null;
        }
        if (salts == null || salts[0] == null) {
            throw new Exception("bcriptyUtil_salts_empty");
        }
        String res = BCrypt.hashpw(new String(data), new String(salts[0]));
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }
        return res.getBytes();
    }
    /**
     * 生成盐
     * @author Fuzhong.Yan
     * 2017年3月13日
     * @return
     */
    public static String gensalt(){
        return gensalt(10,new SecureRandom());
    }
    /**
     * 生成盐
     * @author Fuzhong.Yan
     * 2017年3月13日
     * @return
     */
    public static String gensalt(int log_rounds){
        return gensalt(log_rounds,new SecureRandom());
    }
    /**
     * 生成盐
     * @author Fuzhong.Yan
     * 2017年3月13日
     * @return
     */
    @LogInfo(name="BcriptyUtil生成盐",type=LogType.Default)
    public static String gensalt(int log_rounds,SecureRandom random){
        if (log_rounds < 10 ) {
            log_rounds = 10;
        }
        if (ObjectUtils.isEmpty(random)) {
            random = new SecureRandom();
        }
        String salt = BCrypt.gensalt(log_rounds, random);
        String res = null;
        try {
//            res = ByteUtils.to16Hex(salt.getBytes());
            res = Base64Util.encode(salt.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}