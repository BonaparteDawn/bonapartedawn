package github.bonapartedawn.common.abstructs;


import github.bonapartedawn.common.bean.AspectBean;
import github.bonapartedawn.common.enums.LogType;

/**
 * 日志注解接口
 * Created by Fuzhong.Yan on 16/11/27.
 */
public abstract class AnnotationAspectLog {
    /**
     * 日志类型
     */
    private LogType logType;
    /**
     * 方法执行前记录日志操作
     * @param bean
     */
    public abstract void before(AspectBean bean);
    /**
     * 方法执行后记录日志操作
     * @param bean
     */
    public abstract void after(AspectBean bean);
    /**
     * 方法抛出异常记录日志操作
     * @param bean
     * @param throwable
     */
    public abstract void exception(AspectBean bean,Throwable throwable);

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }
}