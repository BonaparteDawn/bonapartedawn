package framework.util;

import github.bonapartedawn.common.service.JarClassLoader;
import github.bonapartedawn.common.utils.ClassLoaderUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/6.
 */
public class ClassLoaderUtilTest {
    @Test
    public void loadClass() throws Exception {
        ClassLoaderUtil.loadClass(new JarClassLoader("demo"),"common.framework.util.EmailUtil");
    }
}