package github.bonapartedawn.common.service;

import github.bonapartedawn.common.utils.EnvironmentUtil;

import java.util.Date;

/**
 * 性能计算
 * Created by Fuzhong.Yan on 17/2/7.
 */
public class Performance {
    /**
     * 循环执行效率测试
     * @param loopNumber
     * @param runnable
     */
    public static void test(int loopNumber,Runnable runnable){
        Date start = EnvironmentUtil.getOperatingSystemCurrentTime();
        for (int i = 0; i < loopNumber ; i++){
            runnable.run();
        }
        Date end = EnvironmentUtil.getOperatingSystemCurrentTime();
        System.out.println("执行次数:"+loopNumber);
        System.out.println("执行效率:"+(end.getTime()-start.getTime())/(1000.0)/loopNumber+"s/次");
    }
    /**
     * 循环执行效率测试
     * @param loopNumber
     * @param runnable
     */
    public static void test(String function,int loopNumber,Runnable runnable){
        Date start = EnvironmentUtil.getOperatingSystemCurrentTime();
        for (int i = 0; i < loopNumber ; i++){
            runnable.run();
        }
        Date end = EnvironmentUtil.getOperatingSystemCurrentTime();
        System.out.println(function+"----执行次数:"+loopNumber);
        System.out.println("执行效率:"+(end.getTime()-start.getTime())/(1000.0)/loopNumber+"s/次");
    }
}
