package github.bonapartedawn.common.utils;

import org.springframework.util.Assert;
import javax.management.MBeanServer;
import java.lang.management.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 系统环境
 * @author Fuzhong.Yan
 *
 */
public class EnvironmentUtil {
    public static ManagementFactory managementFactory;
    public static double B = 1;
    public static double KB = 1024*B;
    public static double MB = 1024*KB;
    public static double GB = 1024*MB;
    public static double TB = 1024*GB;
    /**
     * 毫秒
     */
    public static double MS = 1;
    /**
     *秒
     */
    public static double SECOND = 1000*MS;
    /**
     * 分钟
     */
    public static double MINUTE = 60 * SECOND;
    /**
     * 小时
     */
    public static double HOUR = 60 * MINUTE;
    /**
     * 天
     */
    public static double DAY = 24 * HOUR;
    /**
     * 获得操作系统名
     * @return
     */
    public static String getOperatingSystemName(){
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        return system.getName();
    }
    /**
     * 获取操作系统版本
     * @return
     */
    public static String getOperatingSystemVersion(){
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        return system.getVersion();
    }
    /**
     * 获得操作系统用户名
     * @return
     */
    public static String getOperatingSystemUserName(){
        return System.getProperty("user.name");
    }
    /**
     * 获得操作系统架构
     * @return
     */
    public static String getOperatingSystemArch(){
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        return system.getArch();
    }
    /**
     * 总共物理内存
     * @return
     */
    public static long getOperatingSystemTotalPhysicalMemorySize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getLongFromOperatingSystem(system,"getTotalPhysicalMemorySize");
        }
        return res;
    }
    /**
     * 剩余物理内存
     * @return
     */
    public static long getOperatingSystemFreePhysicalMemorySize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getLongFromOperatingSystem(system, "getFreePhysicalMemorySize");
        }
        return res;
    }
    /**
     * 使用的物理内存
     * @return
     */
    public static long getOperatingSystemUsedPhysicalMemorySize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getOperatingSystemTotalPhysicalMemorySize() - getOperatingSystemFreePhysicalMemorySize();
        }
        return res;
    }
    /**
     * 物理内存使用率
     * @return
     */
    public static double getOperatingSystemPhysicalMemorySizeUtilizatioRate() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        double res = -1;
        if (isSunOsMBean(system)) {
            res = getOperatingSystemUsedPhysicalMemorySize()*1.0/getOperatingSystemTotalPhysicalMemorySize();
        }
        return res;
    }
    /**
     * 总交换空间
     * @return
     */
    public static long getOperatingSystemTotalSwapSpaceSize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getLongFromOperatingSystem(system, "getTotalSwapSpaceSize");
        }
        return res;
    }
    /**
     * 剩余交换空间
     * @return
     */
    public static long getOperatingSystemFreeSwapSpaceSize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getLongFromOperatingSystem(system, "getFreeSwapSpaceSize");
        }
        return res;
    }
    /**
     * 使用的交换空间
     * @return
     */
    public static long getOperatingSystemUsedSwapSpaceSize() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        long res = -1;
        if (isSunOsMBean(system)) {
            res = getOperatingSystemTotalSwapSpaceSize() - getOperatingSystemFreeSwapSpaceSize();
        }
        return res;
    }
    /**
     * 交换空间使用率
     * @return
     */
    public static double getOperatingSystemSwapSpaceSizeUtilizatioRate() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        double res = -1;
        if (isSunOsMBean(system)) {
            res = getOperatingSystemUsedSwapSpaceSize()*1.0/getOperatingSystemTotalSwapSpaceSize();
        }
        return res;
    }
    /**
     * 获得当前时间
     * @return
     */
    public static Date getOperatingSystemCurrentTime(){
        return new Date();
    }
    /**
     * 获得用户当前工作目录
     * @return
     */
    public static String getUserDir(){
        return System.getProperty("user.dir");
    }
    /**
     * java虚拟机已分配得到的堆内存数量
     * @return
     */
    public static long getTotalMemorySize(){
        return Runtime.getRuntime().totalMemory();
    }
    /**
     * java虚拟机已分配堆内存中的剩余空间。当快要接近0时，以已分配得到的内存即将耗尽，J会决定再次向系统申请更多的内存。
     *
     * @return
     */
    public static long getFreeMemorySize(){
        return Runtime.getRuntime().freeMemory();
    }
    /**
     * java虚拟机已经使用的堆内存的数量
     * @return
     */
    public static long getUsedMemorySize(){
        return getTotalMemorySize() - getFreeMemorySize();
    }
    /**
     * java虚拟机最大堆内存总数
     * @return
     */
    public static long getMaxMemorySize(){
        return Runtime.getRuntime().maxMemory();
    }
    /**
     * java虚拟机最大可使用堆内存总数
     * @return
     */
    public static long getMaxUsableMemorySize(){
        return getMaxMemorySize() - getUsedMemorySize();
    }
    /**
     * java虚拟机最大可申请堆内存总数
     * @return
     */
    public static long getMaxApplicationMemorySize(){
        return getMaxMemorySize() - getTotalMemorySize();
    }
    /**
     * 获取可用进程的数量
     * @return
     */
    public static int getAvailableProcessors(){
        return Runtime.getRuntime().availableProcessors();
    }
    /**
     * 获得编译器的名称
     * @return
     */
    public static String getCompilationName(){
        CompilationMXBean compilation = getCompilationMXBean();
        return compilation.getName();
    }
    /**
     * 获得编译器的编译的时间（单位：秒）
     * @return
     */
    public static long getTotalCompilationTime(){
        CompilationMXBean compilation = getCompilationMXBean();
        long res = -1 ;
        //判断虚拟机是否支持编译时间的监控
        if(compilation.isCompilationTimeMonitoringSupported()){
            res = compilation.getTotalCompilationTime();
        }
        return res;
    }
    /**
     * 已加载类总数
     * @return
     */
    public static long getTotalLoadedClassCount(){
        long res = -1;
        ClassLoadingMXBean classLoad = getClassLoadingMXBean();
        if (classLoad != null) {
            res = classLoad.getTotalLoadedClassCount();
        }
        return res;
    }
    /**
     * 已加载当前类
     * @return
     */
    public static long getLoadedClassCount(){
        long res = -1;
        ClassLoadingMXBean classLoad = getClassLoadingMXBean();
        if (classLoad != null) {
            res = classLoad.getLoadedClassCount();
        }
        return res;
    }
    /**
     * 已卸载类总数
     * @return
     */
    public static long getUnloadedClassCount(){
        long res = -1;
        ClassLoadingMXBean classLoad = getClassLoadingMXBean();
        if (classLoad != null) {
            res = classLoad.getUnloadedClassCount();
        }
        return res;
    }
    /**
     * 进程PID
     * @return
     */
    public static String getPID(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getName().split("@")[0];
        }
        return res;
    }
    /**
     * 虚拟机规范名称
     * @return
     */
    public static String getSpecName(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getSpecName();
        }
        return res;
    }
    /**
     * 虚拟机规范运营商
     * @return
     */
    public static String getSpecVendor(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getSpecVendor();
        }
        return res;
    }
    /**
     * 虚拟机规范版本
     * @return
     */
    public static String getSpecVersion(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getSpecVersion();
        }
        return res;
    }
    /**
     * 虚拟机启动时间（毫秒）【返回虚拟机在毫秒内的开始时间。该方法返回了虚拟机启动时的近似时间】
     * @return
     */
    public static long getStartTime(){
        long res = -1;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getStartTime();
        }
        return res;
    }
    /**
     * 获取System.properties
     * @return
     */
    public static Map<String, String> getSystemProperties(){
        Map<String, String> res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getSystemProperties();
        }
        return res;
    }
    /**
     * 虚拟机正常运行时间（毫秒）
     * @return
     */
    public static long getUptime(){
        long res = -1;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getUptime();
        }
        return res;
    }
    /**
     * 虚拟机名称
     * @return
     */
    public static String getVmName(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getVmName();
        }
        return res;
    }
    /**
     * 虚拟机运营商
     * @return
     */
    public static String getVmVendor(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getVmVendor();
        }
        return res;
    }
    /**
     * 虚拟机实现版本
     * @return
     */
    public static String getVmVersion(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getVmVersion();
        }
        return res;
    }
    /**
     * 参数
     * @return
     */
    public static List<String> getInputArguments(){
        List<String> res = new ArrayList<String>();
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getInputArguments();
        }
        return res;
    }
    /**
     * 类路径
     * @return
     */
    public static String getClassPath(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getClassPath();
        }
        return res;
    }
    /**
     * 引导类路径
     * @return
     */
    public static String getBootClassPath(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getBootClassPath();
        }
        return res;
    }
    /**
     * 库路径
     * @return
     */
    public static String getLibraryPath(){
        String res = null;
        RuntimeMXBean runtime = getRuntimeMXBean();
        if (runtime != null) {
            res = runtime.getLibraryPath();
        }
        return res;
    }

    /**
     * 非堆内存的使用率
     * @return
     */
    public static double getNonHeapMemoryUsageUtilizatioRate(){
        double rate = -1;
        MemoryUsage nonHeap = getNonHeapMemoryUsage();
        if (nonHeap != null) {
            rate = nonHeap.getUsed()*1.0/nonHeap.getCommitted();
        }
        return rate;
    }
    /**
     * 堆内存的使用率
     * @return
     */
    public static double getHeapMemoryUsageUtilizatioRate(){
        double rate = -1;
        MemoryUsage nonHeap = getHeapMemoryUsage();
        if (nonHeap != null) {
            rate = nonHeap.getUsed()*1.0/nonHeap.getCommitted();
        }
        return rate;
    }

    /**
     * 获得堆信息
     */
    public static MemoryUsage getHeapMemoryUsage(){
        return getMemoryMXBean().getHeapMemoryUsage();
    }
    /**
     * 获得非堆信息
     */
    public static MemoryUsage getNonHeapMemoryUsage(){
        return getMemoryMXBean().getNonHeapMemoryUsage();
    }
    /**
     * java虚拟机运行线程数量总数
     * @return
     */
    public static int getTotalThread(){
        ThreadGroup parentThread = Thread.currentThread().getThreadGroup();
        while (parentThread.getParent() != null) {
            parentThread = parentThread.getParent();
        }
        return parentThread.activeCount();
    }
    /**
     * 获得所有的线程
     * @return
     */
    public static Thread[] getAllThreads(){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数
        int activeCount = topGroup.activeCount();
        Thread[] slackList = new Thread[activeCount];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return list;
    }
    /**
     * 判断线程是否存在
     * @param name
     * @return
     */
    public static boolean existThread(String name){
        Thread[] list = getAllThreads();
        if (list != null) {
            for (Thread thread : list) {
                if (name != null && name.equals(thread.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 是否存在死锁线程
     * @return
     */
    public static boolean existDeadLockedThreads(){
        ThreadMXBean thread = getThreadMXBean();
        boolean res = false;
        if (thread != null) {
            //检查是否有死锁的线程存在
            long[] deadlockedIds =  thread.findDeadlockedThreads();
            if (deadlockedIds != null && deadlockedIds.length != 0) {
                res = true;
            }
        }
        return res;
    }
    /**
     * 获得死锁线程
     * @return
     */
    public static long[] getDeadlockedThreads(){
        ThreadMXBean thread = getThreadMXBean();
        long[]  deadlockedIds = null;
        if (thread != null) {
            //检查是否有死锁的线程存在
            deadlockedIds =  thread.findDeadlockedThreads();
        }
        return deadlockedIds;
    }
    /**
     * 获得所有线程ID
     * @return
     */
    public static long[] getAllThreadIds(){
        ThreadMXBean thread = getThreadMXBean();
        long[]  deadlockedIds = null;
        if (thread != null) {
            //检查是否有死锁的线程存在
            deadlockedIds =  thread.getAllThreadIds();
        }
        return deadlockedIds;
    }
    /**
     * 获得线程信息
     * @return
     */
    public static ThreadInfo getThreadInfo(long threadID){
        if (threadID < 0) {
            return null;
        }
        ThreadMXBean thread = getThreadMXBean();
        ThreadInfo  threadInfo = null;
        if (thread != null) {
            //检查是否有死锁的线程存在
            threadInfo =  thread.getThreadInfo(threadID);
        }
        return threadInfo;
    }
    /**
     * 获得线程信息
     * @return
     */
    public static ThreadInfo[] getThreadInfos(long[] threadID){
        if (threadID == null || threadID.length == 0) {
            return null;
        }
        ThreadMXBean thread = getThreadMXBean();
        ThreadInfo[]  threadInfo = null;
        if (thread != null) {
            //检查是否有死锁的线程存在
            threadInfo =  thread.getThreadInfo(threadID);
        }
        return threadInfo;
    }
    /**
     * 获得线程峰值数
     * @return
     */
    public static int getPeakThreadCount(){
        int res = -1;
        ThreadMXBean thread = getThreadMXBean();
        if (thread != null) {
            res = thread.getPeakThreadCount();
        }
        return res;
    }
    /**
     * 仍活动的线程总数
     * @return
     */
    public static int getThreadCount(){
        int res = -1;
        ThreadMXBean thread = getThreadMXBean();
        if (thread != null) {
            res = thread.getThreadCount();
        }
        return res;
    }
    /**
     * 线程总数（被创建并执行过的线程总数）
     * @return
     */
    public static long getTotalStartedThreadCount(){
        long res = -1;
        ThreadMXBean thread = getThreadMXBean();
        if (thread != null) {
            res = thread.getTotalStartedThreadCount();
        }
        return res;
    }
    /**
     * 当初仍活动的守护线程（daemonThread）总数
     * @return
     */
    public static long getDaemonThreadCount(){
        long res = -1;
        ThreadMXBean thread = getThreadMXBean();
        if (thread != null) {
            res = thread.getDaemonThreadCount();
        }
        return res;
    }
    /**
     * 获得当前线程的堆栈跟踪元素的数组
     * @return
     */
    public static StackTraceElement[] getThreadStackTrace(){
        return Thread.currentThread().getStackTrace();
    }
    /**
     * 获得管理Bean对象的内存信息
     * @return
     */
    public static MemoryMXBean getMemoryMXBean(){
        return ManagementFactory.getMemoryMXBean();
    }
    /**
     * 获得运行时信息
     * @return
     */
    public static  RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean();
    }
    /**
     * 获得类加载信息
     * @return
     */
    public static  ClassLoadingMXBean getClassLoadingMXBean() {
        return ManagementFactory.getClassLoadingMXBean();
    }
    /**
     * 获得线程信息
     * @return
     */
    public static  ThreadMXBean getThreadMXBean() {
        return ManagementFactory.getThreadMXBean();
    }
    /**
     * 获得MBean的容器
     * @return
     */
    public static  MBeanServer getPlatformMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }
    /**
     * 获得操作系统信息
     * @return
     */
    public static  OperatingSystemMXBean getOperatingSystemMXBean() {
        return ManagementFactory.getOperatingSystemMXBean();
    }
    /**
     * 获得编译器信息
     * @return
     */
    public static  CompilationMXBean getCompilationMXBean() {
        return ManagementFactory.getCompilationMXBean();
    }
    /**
     * 获得内存管理器
     * @return
     */
    public static  List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
        return ManagementFactory.getMemoryManagerMXBeans();
    }
    /**
     * 获得虚拟机的所有内存区
     * @return
     */
    public static  List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return ManagementFactory.getMemoryPoolMXBeans();
    }
    /**
     * 获得垃圾收集器
     * @return
     */
    public static  List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans();
    }

    /**
     * 得到当前线程的类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得系统环境
     * @return
     */
    public static Map<String,String> getSystemEnv(){
        return System.getenv();
    }
    /**
     * 打印环境所有信息
     */
    public static void printAllEnvirontmentInfo(){
        printOperatingSystemInfo();
        printCompilationInfo();
        printClassLoadingInfo();
        printRuntimeInfo();
        printMemoryManagerInfo();
        printGarbageCollectorInfo();
        printMemoryInfo();
        printMemoryPoolInfo();
        printThreadInfo();
    }
    /**
     * 打印系统信息
     */
    public static void printOperatingSystemInfo(){
        System.out.println("===========打印系统信息==========");
        OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
        //相当于System.getProperty("os.name").
        System.out.println("系统名称:"+getOperatingSystemName());
        //相当于System.getProperty("os.version").
        System.out.println("系统版本:"+getOperatingSystemVersion());
        //相当于System.getProperty("os.arch").
        System.out.println("操作系统的架构:"+getOperatingSystemArch());
        //相当于 Runtime.availableProcessors()
        System.out.println("可用的内核数:"+getAvailableProcessors());
        if(isSunOsMBean(system)){
            System.out.println("物理内存：");
            System.out.println("\t总内存(G):"+getOperatingSystemTotalPhysicalMemorySize()/GB);
            System.out.println("\t已用内存(G):"+getOperatingSystemUsedPhysicalMemorySize()/GB);
            System.out.println("\t剩余内存(G):"+getOperatingSystemFreePhysicalMemorySize()/GB);
            System.out.println("\t使用率(%):"+getOperatingSystemPhysicalMemorySizeUtilizatioRate()*100);
            System.out.println("交换空间：");
            System.out.println("\t总空间(G):"+getOperatingSystemTotalSwapSpaceSize()/GB);
            System.out.println("\t已用空间(G):"+getOperatingSystemUsedSwapSpaceSize()/GB);
            System.out.println("\t剩余空间(G):"+getOperatingSystemFreeSwapSpaceSize()/GB);
            System.out.println("\t空间使用率(%):"+getOperatingSystemSwapSpaceSizeUtilizatioRate()*100);
        }
    }
    /**
     * 打印编译信息
     */
    public static void printCompilationInfo(){
        System.out.println("===========打印编译信息==========");
        System.out.println("JIT编译器名称："+getCompilationName());
        System.out.println("总编译时间(秒)："+getTotalCompilationTime());
    }
    /**
     * 打印类加载信息
     */
    public static void printClassLoadingInfo(){
        System.out.println("===========打印类加载信息==========");
        System.out.println("已加载类总数："+getTotalLoadedClassCount());
        System.out.println("已加载当前类："+getLoadedClassCount());
        System.out.println("已卸载类总数："+getUnloadedClassCount());
    }
    /**
     * 打印运行时信息
     */
    public static void printRuntimeInfo(){
        System.out.println("===========打印运行时信息==========");
        System.out.println("进程PID="+getPID());
        System.out.println("虚拟机规范名称:"+getSpecName());
        System.out.println("虚拟机规范运营商:"+getSpecVendor());
        System.out.println("虚拟机规范版本:"+getSpecVersion());
        System.out.println("虚拟机启动时间(秒):"+getStartTime()/SECOND);
        System.out.println("虚拟机正常运行时间(秒):"+getUptime()/SECOND);
        System.out.println("虚拟机名称:"+getVmName());
        System.out.println("虚拟机运营商:"+getVmVendor());
        System.out.println("虚拟机实现版本:"+getVmVersion());
        List<String> args = getInputArguments();
        if(args != null && !args.isEmpty()){
            System.out.println("虚拟机参数:");
            for(String arg : args){
                System.out.println("\t"+arg);
            }
        }
        System.out.println("用户当前工作路径："+getUserDir());
        System.out.println("类路径:"+getClassPath());
        System.out.println("获取System.properties:"+getSystemProperties());
        System.out.println("引导类路径:"+getBootClassPath());
        System.out.println("库路径:"+getLibraryPath());
    }
    /**
     * 打印内存管理器信息
     */
    public static void printMemoryManagerInfo(){
        System.out.println("===========打印内存管理器信息==========");
        List<MemoryManagerMXBean> managers = getMemoryManagerMXBeans();
        if(managers != null && !managers.isEmpty()){
            for(MemoryManagerMXBean manager : managers){
                System.out.println("虚拟机内存管理器---"+manager.getName());
                System.out.println("\t名称="+manager.getName());
                System.out.println("\t管理的内存区="+Arrays.deepToString(manager.getMemoryPoolNames()));
                System.out.println("\tObjectName="+manager.getObjectName());
            }
        }
    }
    /**
     * 打印垃圾回收信息
     */
    public static void printGarbageCollectorInfo(){
        System.out.println("===========打印垃圾回收信息==========");
        List<GarbageCollectorMXBean> garbages = getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbage : garbages){
            System.out.println("垃圾收集器--"+garbage.getName());
            System.out.println("\t名称="+garbage.getName());
            System.out.println("\t收集="+garbage.getCollectionCount());
            System.out.println("\t总花费时间(分)="+garbage.getCollectionTime()/MINUTE);
            System.out.println("\t内存区名称="+Arrays.deepToString(garbage.getMemoryPoolNames()));
        }
    }
    /**
     * 打印虚拟机内存信息
     */
    public static void printMemoryInfo(){
        System.out.println("===========打印虚拟机内存信息==========");
        MemoryUsage headMemory = getHeapMemoryUsage();
        System.out.println("head堆:");
        System.out.println("\t初始(M):"+headMemory.getInit()/MB);
        System.out.println("\t最大(上限)(M):"+headMemory.getMax()/MB);
        System.out.println("\t当前(已使用)(M):"+headMemory.getUsed()/MB);
        System.out.println("\t提交的内存(已申请)(M):"+headMemory.getCommitted()/MB);
        System.out.println("\t申请剩余内存(M):"+(headMemory.getCommitted()-headMemory.getUsed())/MB);
        System.out.println("\t最大可用内存总数(M):"+(headMemory.getMax()-headMemory.getUsed())/MB);
        System.out.println("\t最大可申请内存总数(M):"+(headMemory.getMax()-headMemory.getCommitted())/MB);
        System.out.println("\t使用率(%):"+getHeapMemoryUsageUtilizatioRate()*100);
        System.out.println("non-head非堆:");
        MemoryUsage nonheadMemory = getNonHeapMemoryUsage();
        System.out.println("\t初始(M):"+nonheadMemory.getInit()/MB);
        System.out.println("\t最大(上限)(M):"+nonheadMemory.getMax()/MB);
        System.out.println("\t当前(已使用)(M):"+nonheadMemory.getUsed()/MB);
        System.out.println("\t提交的内存(已申请)(M):"+nonheadMemory.getCommitted()/MB);
        System.out.println("\t申请剩余内存(M):"+(nonheadMemory.getCommitted()-nonheadMemory.getUsed())/MB);
        System.out.println("\t最大可用内存总数(M):"+(nonheadMemory.getMax()-nonheadMemory.getUsed())/MB);
        System.out.println("\t最大申请内存总数(M):"+(headMemory.getMax()-nonheadMemory.getCommitted())/MB);
        System.out.println("\t使用率(%):"+getNonHeapMemoryUsageUtilizatioRate()*100);

//		System.out.println("已分配得到的堆内存数量(M):"+getTotalMemorySize()/MB);
//		System.out.println("已分配堆内存中的剩余空间(M):"+getFreeMemorySize()/MB);
//		System.out.println("已经使用的堆内存的数量(M):"+getUsedMemorySize()/MB);
//		System.out.println("最大堆内存总数(M):"+getMaxMemorySize()/MB);
//		System.out.println("最大可用堆内存总数(M):"+getMaxUsableMemorySize()/MB);
//		System.out.println("最大可申请堆内存总数(M):"+getMaxApplicationMemorySize()/MB);
    }
    /**
     * 打印虚拟机各内存区信息
     */
    public static void printMemoryPoolInfo(){
        System.out.println("===========打印虚拟机各内存区信息==========");
        List<MemoryPoolMXBean> pools = getMemoryPoolMXBeans();
        if(pools != null && !pools.isEmpty()){
            for(MemoryPoolMXBean pool : pools){
                pool.getObjectName();
                //只打印一些各个内存区都有的属性，一些区的特殊属性，可看文档或百度
                //最大值，初始值，如果没有定义的话，返回-1，所以真正使用时，要注意
                System.out.println("vm内存区---"+pool.getName());
                System.out.println("\t名称="+pool.getName());
                System.out.println("\t所属内存管理者="+Arrays.deepToString(pool.getMemoryManagerNames()));
                System.out.println("\tObjectName="+pool.getObjectName()+"\n\t初始大小(M)="+pool.getUsage().getInit()/MB);
                System.out.println("\t最大(上限)(M)="+pool.getUsage().getMax()/MB);
                System.out.println("\t已用大小(M)="+pool.getUsage().getUsed()/MB);
                System.out.println("\t已提交(已申请)(M)="+pool.getUsage().getCommitted()/MB);
                System.out.println("\t使用率(%)="+(pool.getUsage().getUsed()*100/pool.getUsage().getCommitted()));

            }
        }
    }
    /**
     * 打印线程信息
     */
    public static void printThreadInfo(){
        System.out.println("===========打印线程信息==========");
        ThreadMXBean thread = getThreadMXBean();
        System.out.println("ObjectName="+thread.getObjectName());
        System.out.println("仍活动的线程总数="+getThreadCount());
        System.out.println("峰值="+getPeakThreadCount());
        System.out.println("线程总数(被创建并执行过的线程总数)="+getTotalStartedThreadCount());
        System.out.println("仍活动的守护线程总数(daemonThread)="+getDaemonThreadCount());

        //检查是否有死锁的线程存在
        if(existDeadLockedThreads()){
            long[] deadlockedIds =  getDeadlockedThreads();
            ThreadInfo[] deadlockInfos = getThreadInfos(deadlockedIds);
            for(ThreadInfo deadlockInfo : deadlockInfos){
                System.out.println("死锁线程---"+deadlockInfo.getThreadName());
                System.out.println("\t线程名称:"+deadlockInfo.getThreadName());
                System.out.println("\t状态:"+deadlockInfo.getThreadState());
                System.out.println("\t阻塞时间(秒)"+deadlockInfo.getBlockedTime()/SECOND);
                System.out.println("\t等待时间(秒)"+deadlockInfo.getWaitedTime()/SECOND);
                System.out.println("\t跟踪信息"+deadlockInfo.getStackTrace().toString());
            }
        }
        long[] threadIds = getAllThreadIds();
        if(threadIds != null && threadIds.length > 0){
            ThreadInfo[] threadInfos = getThreadInfos(threadIds);
            for(ThreadInfo threadInfo : threadInfos){
                System.out.println("线程---"+threadInfo.getThreadName());
                System.out.println("\t线程id:"+threadInfo.getThreadId());
                System.out.println("\t线程名称:"+threadInfo.getThreadName());
                System.out.println("\t状态:"+threadInfo.getThreadState());
            }
        }

    }
    private static boolean isSunOsMBean(OperatingSystemMXBean operatingSystem) {
        Assert.notNull(operatingSystem, "operatingSystem_null");
        final String className = operatingSystem.getClass().getName();
        return "com.sun.management.OperatingSystem".equals(className) || "com.sun.management.UnixOperatingSystem".equals(className);
    }
    private static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
        Assert.notNull(operatingSystem, "operatingSystem_null");
        Assert.hasLength("methodName", "methodName_len0");
        try {
            final Method method = operatingSystem.getClass().getMethod(methodName,(Class<?>[]) null);
            method.setAccessible(true);
            return (Long) method.invoke(operatingSystem, (Object[]) null);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            } else if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new IllegalStateException(e.getCause());
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}