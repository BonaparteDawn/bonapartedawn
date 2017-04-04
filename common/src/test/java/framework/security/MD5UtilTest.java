package framework.security;

import github.bonapartedawn.common.service.security.MD5Util;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class MD5UtilTest {
    @Test
    public void encrypt() throws Exception {
        MD5Util temp = new MD5Util();
        String origin = "---I_LOVE_YOU---";
        System.out.println("加密==============");
        System.out.println("原始数据:"+origin);
        String  res = temp.encrypt(origin);
        System.out.println("加密结果:"+res);
    }

    @Test
    public void decrypt() throws Exception {

    }

}