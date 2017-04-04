package framework.util;

import github.bonapartedawn.common.utils.InternetUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class InternetUtilTest {
    @Test
    public void doPost() throws Exception {
    }

    @Test
    public void doGet() throws Exception {
        System.out.println(InternetUtil.doGet("http://www.baidu.com"));
    }

}