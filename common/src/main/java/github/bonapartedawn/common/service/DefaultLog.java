package github.bonapartedawn.common.service;

import github.bonapartedawn.common.utils.EnvironmentUtil;
import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by Fuzhong.Yan on 17/1/10.
 */
public class DefaultLog implements Log,Serializable {
    private Logger logger = null;
    private String callerFQCN = null;
    public DefaultLog(){
        this(3);
    }
    public DefaultLog(int i){
        StackTraceElement[] tst = EnvironmentUtil.getThreadStackTrace();
        if (tst != null){
            callerFQCN = tst[i].getClassName();
        }
    }
    public DefaultLog(String callerFQCN){
        this.callerFQCN = callerFQCN;
    }
    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isFatalEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void trace(Object o) {
        logger.log(callerFQCN, Level.TRACE,o,null);
    }

    @Override
    public void trace(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.TRACE,o,throwable);
    }

    @Override
    public void debug(Object o) {
        logger.log(callerFQCN, Level.DEBUG,o,null);
    }

    @Override
    public void debug(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.DEBUG,o,throwable);
    }

    @Override
    public void info(Object o) {
        logger.log(callerFQCN, Level.INFO,o,null);
    }
    @Override
    public void info(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.INFO,o,throwable);
    }

    @Override
    public void warn(Object o) {
        logger.log(callerFQCN, Level.WARN,o,null);
    }

    @Override
    public void warn(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.WARN,o,throwable);
    }

    @Override
    public void error(Object o) {
        logger.log(callerFQCN, Level.ERROR,o,null);
    }

    @Override
    public void error(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.ERROR,o,throwable);
    }

    @Override
    public void fatal(Object o) {
        logger.log(callerFQCN, Level.FATAL,o,null);
    }

    @Override
    public void fatal(Object o, Throwable throwable) {
        logger.log(callerFQCN, Level.FATAL,o,throwable);
    }
}
