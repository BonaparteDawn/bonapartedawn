package framework.security;

import github.bonapartedawn.common.service.security.BCryptUtil;
import github.bonapartedawn.common.service.security.SecurityCoder;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/3/14.
 */
public class BCryptUtilTest {
    @Test
    public void decrypt() throws Exception {

    }

    @Test
    public void encrypt() throws Exception {
        String salt = BCryptUtil.gensalt();
        SecurityCoder securityCoder = new BCryptUtil();
        System.out.println(securityCoder.encrypt("test",salt));
    }

}