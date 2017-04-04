package framework.util;

import github.bonapartedawn.common.utils.Base64Util;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class Base64UtilTest {
    @Test
    public void encrypt() throws Exception {
        Base64Util temp = new Base64Util();
        String origin = "---I_LOVE_YOU---";
        System.out.println("Base64编码==============");
        System.out.println("原始数据:"+origin);
        String  res = temp.encode(origin.getBytes());
        System.out.println("编码结果:"+res);
    }

    @Test
    public void decrypt() throws Exception {
        Base64Util temp = new Base64Util();
        String origin = "---I-LOVE-YOU---";
        System.out.println("解码==============");
        String  encrypt = temp.encode(origin.getBytes());
        System.out.println("编码数据:"+encrypt);
        byte[] res = temp.decode(encrypt);
        System.out.println("解码结果:"+new String(res));
    }

}