package github.bonapartedawn.common.service;

import github.bonapartedawn.common.enums.LogType;
import org.apache.commons.logging.Log;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池
 * @author Fuzhong.Yan
 */
public class ThreadPoolService {
    private Log log = MyLogFactory.productLog(LogType.Default,ThreadPoolService.class);
    private int DEFAULT_THREADS_NUMBER = 3;
    private  ExecutorService executorService = null;
    public ThreadPoolService(){
        executorService = new ScheduledThreadPoolExecutor(DEFAULT_THREADS_NUMBER);
        Runtime.getRuntime().addShutdownHook(new Thread(new ThreadShutdownHook()));
    }
    public ThreadPoolService(Integer threadNumber){
        if (threadNumber != null && threadNumber > 0) {
            DEFAULT_THREADS_NUMBER = threadNumber;
        }
        executorService = new ScheduledThreadPoolExecutor(DEFAULT_THREADS_NUMBER);
        Runtime.getRuntime().addShutdownHook(new Thread(new ThreadShutdownHook()));
    }
    /**
     * 所有的对象不能够有循环方法，否则会一直占用线程池里面的某个线程
     * @param runnables
     */
    public void execute(Runnable ... runnables){
        Assert.notNull(executorService,"executorService_null");
        for (Runnable runnable : runnables) {
            executorService.execute(runnable);
        }
    }
    /**
     * 所有的对象不能够有循环方法，否则会一直占用线程池里面的某个线程
     * @param runnable
     */
    public Future<?> submit(Runnable runnable){
        Assert.notNull(executorService,"executorService_null");
        return executorService.submit(runnable);
    }
    public List<Runnable> shutdownNow(){
        return this.executorService.shutdownNow();
    }
    public void shutdown(){
        this.executorService.shutdown();
    }
    class ThreadShutdownHook implements Runnable{
        public void run() {
            ThreadPoolService.this.shutdownNow();
            log.info("线程池关闭");
        }
    }
}
