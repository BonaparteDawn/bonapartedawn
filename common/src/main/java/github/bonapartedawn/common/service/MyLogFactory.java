package github.bonapartedawn.common.service;

import github.bonapartedawn.common.enums.LogType;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by Fuzhong.Yan on 17/1/10.
 */
public class MyLogFactory {
    public static Map<LogType,FactoryBean<Log>> logs = new HashedMap();

    public static void addLogFactoryBean(LogType type,FactoryBean<Log> factoryBean){
        logs.put(type,factoryBean);
    }
    public static Log productLog(LogType type, Class classed){
        Assert.notNull(type,"LOG_TYPE_NULL");
        Assert.notNull(classed,"CLASS_NULL");
        return productLog(type,classed,classed.getClass().getName());
    }
    public static Log productLog(LogType type, Class classed,String callerFQCN){
        Assert.notNull(type,"LOG_TYPE_NULL");
        Assert.notNull(classed,"CLASS_NULL");
        Assert.hasLength(callerFQCN,"callerFQCN_len0");
        Log log = null;
        if (LogType.Default.equals(type)){
            DefaultLog defaultLog = new DefaultLog(callerFQCN);
            defaultLog.setLogger(Logger.getLogger(classed.getClass()));
            log = defaultLog;
        }else{
            if (logs.containsKey(type)){
                try {
                    log = logs.get(type).getObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return log;
    }
    public static Log productLog(LogType type,String name){
        Assert.notNull(type,"LOG_TYPE_NULL");
        Assert.notNull(name,"NAME_NULL");
        Log log = null;
        if (LogType.Default.equals(type)){
            DefaultLog defaultLog = new DefaultLog();
            defaultLog.setLogger(Logger.getLogger(name));
            log = defaultLog;
        }else{
            if (logs.containsKey(type)){
                try {
                    log = logs.get(type).getObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return log;
    }
    public static Log productLog(LogType type,String name,String callerFQCN){
        Assert.notNull(type,"LOG_TYPE_NULL");
        Assert.notNull(name,"NAME_NULL");
        Assert.hasLength(callerFQCN,"callerFQCN_len0");
        Log log = null;
        if (LogType.Default.equals(type)){
            DefaultLog logWithCallerFQCN = new DefaultLog(callerFQCN);
            logWithCallerFQCN.setLogger(Logger.getLogger(name));
            log = logWithCallerFQCN;
        }else{
            if (logs.containsKey(type)){
                try {
                    log = logs.get(type).getObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return log;
    }
}