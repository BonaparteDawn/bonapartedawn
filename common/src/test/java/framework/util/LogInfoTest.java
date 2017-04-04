package framework.util;

import github.bonapartedawn.common.service.LogAdvice;
import github.bonapartedawn.common.testSample.DemoPeople;
import github.bonapartedawn.common.testSample.DemoRun;
import github.bonapartedawn.common.utils.ProxyUtil;
import org.junit.Test;

/**
 * Created by Fuzhong.Yan on 17/2/23.
 */
public class LogInfoTest {
    @Test
    public void logInfoTest() throws Exception {
        DemoPeople people = new DemoPeople();
        DemoRun a = ProxyUtil.newProxyInstance1(people,new LogAdvice(people));
        a.run();
    }
}