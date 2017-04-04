package framework.util;

import github.bonapartedawn.common.enums.Constant;
import github.bonapartedawn.common.service.Performance;
import github.bonapartedawn.common.utils.FileUtil;
import github.bonapartedawn.common.utils.PdfUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Fuzhong.Yan on 17/1/30.
 */
public class PdfUtilTest {
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void test(){
        Performance.test(100, new Runnable() {
            public void run() {
                try {
                    PdfUtil.html2pdf(FileUtil.path(Constant.TEST_BASE_PATH_AZ,"pdf","index.html"),FileUtil.path(Constant.TEST_BASE_PATH_OUT,"test.pdf"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}