package framework.util;

import github.bonapartedawn.common.bean.ResultEntity;
import github.bonapartedawn.common.enums.Constant;
import github.bonapartedawn.common.enums.ServerResponseCode;
import github.bonapartedawn.common.service.JarClassLoader;
import github.bonapartedawn.common.service.Performance;
import github.bonapartedawn.common.service.security.SecurityCoder;
import github.bonapartedawn.common.utils.IOUtils;
import github.bonapartedawn.common.utils.ReflectUtil;
import org.junit.Test;
import java.io.*;

/**
 * Created by Fuzhong.Yan on 17/2/5.
 */
public class ReflectUtilTest {

    @Test
    public void newInstance() throws Exception {
        ResultEntity a = ReflectUtil.newInstance(ResultEntity.class);
        System.out.println(a);
    }

    @Test
    public void newInstance1() throws Exception {
        ResultEntity a = ReflectUtil.newInstance(ResultEntity.class, ServerResponseCode.SUCCESS,"成功");
        System.out.println(a);
    }
    @Test
    public void classLoad() throws Exception {
        Performance.test("加载二进制类", 100, new Runnable() {
            public void run() {
                Object a = null;
                try {
                    a = ReflectUtil.newInstance(new JarClassLoader("demo", Constant.TEST_BASE_PATH_AZ+"/email_jar/"), "email.Email");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(a);
            }
        });
    }
    @Test
    public void base64classLoad() throws Exception {

        Performance.test("加载加密class类", 1, new Runnable() {
            public void run() {
                try {
                    String pre = Constant.TEST_BASE_PATH_AZ+"/email_jar/email/";
                    String encodePre = Constant.TEST_BASE_PATH_OUT +"/email_jar/email/";
                    SecurityCoder s = null;

                    OutputStream outputStream = null;
                    InputStream inputStream = null;
                    inputStream = new FileInputStream(new File(pre+"EmailServerAgent.class"));
                    byte[] bytes = IOUtils.copy2Bytes(inputStream);
                    inputStream.close();

                    outputStream = new FileOutputStream(new File(encodePre+"EmailServerAgent.class"));
                    IOUtils.write2outputStream(s.encrypt(bytes),outputStream);
                    outputStream.close();

                    inputStream = new FileInputStream(new File(pre+"EmailServer.class"));
                    bytes = IOUtils.copy2Bytes(inputStream);
                    inputStream.close();

                    outputStream = new FileOutputStream(new File(encodePre+"EmailServer.class"));
                    IOUtils.write2outputStream(s.encrypt(bytes),outputStream);
                    outputStream.close();

                    ClassLoader classLoader = new JarClassLoader(s,"demo",Constant.TEST_BASE_PATH_OUT+"/email_jar/");
                    Object a = ReflectUtil.newInstance(classLoader, "email.EmailServerAgent");
                    System.out.println(a);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}