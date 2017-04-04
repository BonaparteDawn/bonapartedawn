package github.bonapartedawn.common.testSample;

import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.enums.LogType;

/**
 * Created by Fuzhong.Yan on 17/2/6.
 */
public class DemoPeople extends DemoObject implements DemoRun{
    @LogInfo(name = "人跑",description = "实现的DemoRun接口的一个方法",type = LogType.Default)
    public void run() {
        System.out.println("正在跑。。。");
    }
}