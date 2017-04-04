package github.bonapartedawn.common.service;

import github.bonapartedawn.common.abstructs.AnnotationAspectLog;
import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.bean.AspectBean;
import github.bonapartedawn.common.enums.LogTime;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.utils.ListUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

/**
 * 记录日志的Aspect
 * Created by Fuzhong.Yan on 16/11/27.
 */
@Aspect
public class AspectLog {

    private List<AnnotationAspectLog> annotationAspectLogs;

    @Around(value = "@annotation(log)")
    public Object run(ProceedingJoinPoint proceedingJoinPoint, LogInfo log) throws Exception {
        Object result = null;
        AspectBean asAspectBean = null;
        try {
            if (annotationAspectLogs!=null && annotationAspectLogs.size()>0){
                    asAspectBean = new AspectBean();
                    asAspectBean.setArgs(proceedingJoinPoint.getArgs());
                    asAspectBean.setKind(proceedingJoinPoint.getKind());
                    asAspectBean.setSignature(proceedingJoinPoint.getSignature());
                    asAspectBean.setSourceLocation(proceedingJoinPoint.getSourceLocation());
                    asAspectBean.setStaticPart(proceedingJoinPoint.getStaticPart());
                    asAspectBean.setThisObj(proceedingJoinPoint.getThis());
                    asAspectBean.setType(log.type());
                    asAspectBean.setName(log.name());
                    asAspectBean.setDescription(log.description());
                    asAspectBean.setLogTimes(log.logTime());
                for (AnnotationAspectLog annotationAspectLog:annotationAspectLogs){
                    if (annotationAspectLog.getLogType()!=null){
                        LogType[] types = log.type();
                        if (types != null){
                            for (LogType t:types){
                                if (annotationAspectLog != null && asAspectBean !=null && ListUtils.isNotEmpty(asAspectBean.getLogTimes()) && t.equals(annotationAspectLog.getLogType()) ){
                                    for (LogTime azLogTime:asAspectBean.getLogTimes()){
                                        if (azLogTime.equals(LogTime.BeforeMethod)){
                                            annotationAspectLog.before(asAspectBean);
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        throw new Exception("LOG_TYPE_IS_NULL");
                    }
                }
            }
            result = proceedingJoinPoint.proceed();
            asAspectBean.setResult(result);
            if (annotationAspectLogs!=null && annotationAspectLogs.size()>0){
                for (AnnotationAspectLog annotationAspectLog:annotationAspectLogs){
                    LogType[] types = log.type();
                    if (types != null){
                        for (LogType t:types){
                            if (annotationAspectLog != null && asAspectBean !=null && ListUtils.isNotEmpty(asAspectBean.getLogTimes()) && t.equals(annotationAspectLog.getLogType()) ){
                                for (LogTime azLogTime:asAspectBean.getLogTimes()){
                                    if (azLogTime.equals(LogTime.AfterMethod)){
                                        annotationAspectLog.after(asAspectBean);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            if (annotationAspectLogs!=null && annotationAspectLogs.size()>0){
                for (AnnotationAspectLog annotationAspectLog:annotationAspectLogs){
                    LogType[] types = log.type();
                    if (types != null){
                        for (LogType t:types){
                            if (annotationAspectLog != null && asAspectBean !=null && ListUtils.isNotEmpty(asAspectBean.getLogTimes()) && t.equals(annotationAspectLog.getLogType()) ){
                                for (LogTime azLogTime:asAspectBean.getLogTimes()){
                                    if (azLogTime.equals(LogTime.ExceptionMethod)){
                                        annotationAspectLog.exception(asAspectBean,e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            throw new Exception(e.getMessage());
        }
        return result;
    }

    public List<AnnotationAspectLog> getAnnotationAspectLogs() {
        return annotationAspectLogs;
    }

    public void setAnnotationAspectLogs(List<AnnotationAspectLog> annotationAspectLogs) {
        this.annotationAspectLogs = annotationAspectLogs;
    }
}
