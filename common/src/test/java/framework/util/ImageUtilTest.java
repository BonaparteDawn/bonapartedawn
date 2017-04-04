package framework.util;

import github.bonapartedawn.common.enums.Constant;
import github.bonapartedawn.common.service.Performance;
import github.bonapartedawn.common.utils.FileUtil;
import github.bonapartedawn.common.utils.ImageUtil;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.InputStream;

/**
 * Created by Fuzhong.Yan on 17/2/7.
 */
public class ImageUtilTest {
    String path = FileUtil.path(Constant.TEST_BASE_PATH_AZ,"1.jpg");
    String path1 = FileUtil.path(Constant.TEST_BASE_PATH_OUT,"2.jpg");
    String path2 = FileUtil.path(Constant.TEST_BASE_PATH_OUT,"3.jpg");
    @Test
    public void zipImageFile() throws Exception {
        Performance.test("不变形压缩",100, new Runnable() {
            public void run() {
                try {
                    InputStream inputstream = FileUtil.convert2InputStream(path);
                    BufferedOutputStream out = FileUtil.convert2BufferedOutputStream(path1);
                    ImageUtil.zipImageFile(inputstream,out,100,100,100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void zipImageFile1() throws Exception {
        Performance.test("按比例压缩",100, new Runnable() {
            public void run() {
                try {
                    InputStream inputstream = FileUtil.convert2InputStream(path);
                    BufferedOutputStream out = FileUtil.convert2BufferedOutputStream(path2);
                    ImageUtil.zipImageFile(inputstream,out,0.6,0.6,100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}