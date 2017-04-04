package github.bonapartedawn.common.utils;

import org.springframework.util.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Fuzhong.Yan on 17/1/15.
 */
public class PropertiesUtil {
    /**
     *支持查询某个JAR包下的属性文件
     * @param classPath
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String classPath) throws IOException {
        Assert.hasLength(classPath,"CLASS_PATH_LEN0");
        Properties properties = null;
        InputStream inputStream = FileUtil.readClasspath(classPath);
        if (inputStream != null){
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        }
        return properties;
    }

    /**
     * 查找属性文件(支持classpath:filePath格式)
     * @param classPath
     * @return
     * @throws IOException
     */
    public static Properties getPropertiesByPathMatching(String classPath) throws IOException {
        Assert.hasLength(classPath,"CLASS_PATH_LEN0");
        Properties properties = null;
        InputStream inputStream = FileUtil.readClasspathWithClasspathPre(classPath);
        if (inputStream != null){
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        }
        return properties;
    }
}
