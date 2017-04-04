package framework.util;

import github.bonapartedawn.common.enums.Constant;
import github.bonapartedawn.common.utils.FileUtil;
import github.bonapartedawn.common.utils.MusicUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/6.
 */
public class MusicUtilTest {
    @Test
    public void play() throws Exception {
        String path = FileUtil.path(Constant.TEST_BASE_PATH_AZ,"1.wav");
        MusicUtil.play(path);
    }
}