package framework.util;

import github.bonapartedawn.common.testSample.DemoAdvice;
import github.bonapartedawn.common.testSample.DemoMethodInterceptor;
import github.bonapartedawn.common.testSample.DemoPeople;
import github.bonapartedawn.common.testSample.DemoRun;
import github.bonapartedawn.common.utils.ProxyUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/6.
 */
public class ProxyUtilTest {
    @Test
    public void newProxyInstance() throws Exception {
        DemoPeople people = new DemoPeople();
        DemoRun a = ProxyUtil.newProxyInstance1(people, new DemoAdvice(people));
        a.run();
    }

    @Test
    public void newProxyInstance1() throws Exception {
        DemoPeople people = new DemoPeople();
        DemoRun a = ProxyUtil.newMyProxyInstance(people,DemoAdvice.class);
        a.run();
    }
    @Test
    public void demo() throws Exception {
        DemoRun demoRun = ProxyUtil.newProxyInstance2(new DemoPeople(),new DemoMethodInterceptor());
        demoRun.run();
    }
}