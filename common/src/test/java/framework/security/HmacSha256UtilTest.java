package framework.security;

import github.bonapartedawn.common.service.security.HmacSha256Util;
import github.bonapartedawn.common.utils.ByteUtils;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class HmacSha256UtilTest {
    static String  key = null;
    static {
        try {
            key = ByteUtils.to16Hex("I_LOVE_YOU".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void encrypt() throws Exception {
        System.out.println("mac算法");
        System.out.println("============================");
        HmacSha256Util hmacSha256Util = new HmacSha256Util();
        String res = hmacSha256Util.encrypt("待加密的数据", key);
        System.out.println(res);
    }

    @Test
    public void decrypt() throws Exception {
        System.out.println("不支持解密");
    }

}