package github.bonapartedawn.common.service;

import github.bonapartedawn.common.abstructs.MyAdvice;
import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.bean.Record;
import github.bonapartedawn.common.enums.LogTime;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.utils.ObjectUtils;
import org.apache.commons.logging.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录
 * Created by Fuzhong.Yan on 17/2/23.
 */
public class LogAdvice<E> extends MyAdvice<E> {
    private final static String CALLER_FQCN = "com.sun.proxy.$Proxy4";
    private Map<LogType,Log> logMap = new HashMap<LogType,Log>();
    public LogAdvice(E target) {
        super(target);
    }

    @Override
    public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass) {
        LogInfo logInfo = ObjectUtils.annotation(target,method,LogInfo.class);
        if (logInfo != null){
            LogTime[] logTimes = logInfo.logTime();
            if (logTimes == null){
                for (LogType type : logInfo.type()) {
                    Record record = new Record(logInfo,LogTime.ExceptionMethod,type,this.target.getClass().getName(),method.getName(),args,null,subclass);
                    error(record);
                }
            }else {
                for (LogTime logTime:logTimes){
                    if (LogTime.ExceptionMethod.equals(logTime)){
                        for (LogType type : logInfo.type()) {
                            Record record = new Record(logInfo,LogTime.ExceptionMethod,type,this.target.getClass().getName(),method.getName(),args,null,subclass);
                            error(record);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void afterReturning(Object o, Method method, Object[] args, Object o1) throws Throwable {
        LogInfo logInfo = ObjectUtils.annotation(target,method,LogInfo.class);
        if (logInfo != null){
            LogTime[] logTimes = logInfo.logTime();
            if (logTimes == null){
                for (LogType type : logInfo.type()) {
                    Record record = new Record(logInfo,LogTime.AfterMethod,type,this.target.getClass().getName(),method.getName(),args,o,null);
                    info(record);
                }
            }else {
                for (LogTime logTime:logTimes){
                    if (LogTime.AfterMethod.equals(logTime)){
                        for (LogType type : logInfo.type()) {
                            Record record = new Record(logInfo,LogTime.AfterMethod,type,this.target.getClass().getName(),method.getName(),args,o,null);
                            info(record);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void before(Method method, Object[] args, Object o) throws Throwable {
        LogInfo logInfo = ObjectUtils.annotation(target,method,LogInfo.class);
        if (logInfo != null){
            LogTime[] logTimes = logInfo.logTime();
            if (logTimes == null){
                for (LogType type : logInfo.type()) {
                    Record record = new Record(logInfo,LogTime.BeforeMethod,type,this.target.getClass().getName(),method.getName(),args,null,null);
                    info(record);
                }
            }else {
                for (LogTime logTime:logTimes){
                    if (LogTime.BeforeMethod.equals(logTime)){
                        for (LogType type : logInfo.type()) {
                            Record record = new Record(logInfo,LogTime.BeforeMethod,type,this.target.getClass().getName(),method.getName(),args,null,null);
                            info(record);
                        }
                    }
                }
            }
        }
    }

    private Log queryLog(LogType type){
        Log log = null;
        if (logMap.containsKey(type)){
            log = logMap.get(type);
        }else {
            log = MyLogFactory.productLog(type,target.getClass(),CALLER_FQCN);
            if (ObjectUtils.isNotEmpty(log)){
                logMap.put(type,log);
            }
        }
        return  log;
    }

    private void info(Record record){
        if (ObjectUtils.isEmpty(record)){
            return;
        }
        Log log = queryLog(record.getUseLogType());
        if (ObjectUtils.isNotEmpty(log)){
            log.info(record);
        }
    }
    private void error(Record record){
        if (ObjectUtils.isEmpty(record)){
            return;
        }
        Log log = queryLog(record.getUseLogType());
        if (ObjectUtils.isNotEmpty(log)){
            log.error(record,record.getThrowable());
        }
    }
}
