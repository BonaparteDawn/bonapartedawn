package framework.security;

import github.bonapartedawn.common.service.security.SHA512Util;
import github.bonapartedawn.common.service.security.SecurityCoder;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/3/14.
 */
public class SHA512UtilTest {
    @Test
    public void encrypt() throws Exception {
        SecurityCoder securityCoder = new SHA512Util();
        System.out.println(securityCoder.encrypt("123"));
    }

}