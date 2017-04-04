package framework.util;

import github.bonapartedawn.common.utils.ByteUtils;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/10.
 */
public class ByteUtilsTest {
    @Test
    public void append() throws Exception {
        byte[] one = "1".getBytes();
        byte[] two = "2".getBytes();
        byte[] thirty = "30".getBytes();
        byte[] res = ByteUtils.append(one, two, thirty);
        System.out.println(new String(res));
    }
}