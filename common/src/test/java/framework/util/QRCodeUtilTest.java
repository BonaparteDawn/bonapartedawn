package framework.util;

import github.bonapartedawn.common.enums.Constant;
import github.bonapartedawn.common.service.Performance;
import github.bonapartedawn.common.utils.QRCodeUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/1/28.
 */
public class QRCodeUtilTest {
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void create() throws Exception {
        Performance.test("生成二维码", 100, new Runnable() {
            public void run() {
                try {
                    String path = Constant.TEST_BASE_PATH_OUT+"/qr.png";
                    QRCodeUtil.encode("nihao",path);
                    System.out.println(QRCodeUtil.decode(path));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}