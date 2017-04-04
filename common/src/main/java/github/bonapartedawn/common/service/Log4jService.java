package github.bonapartedawn.common.service;

import github.bonapartedawn.common.abstructs.AnnotationAspectLog;
import github.bonapartedawn.common.bean.AspectBean;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.utils.JsonUtil;
import github.bonapartedawn.common.utils.ObjectUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

/**
 * 使用Log4j框架记录日志
 * Created by Fuzhong.Yan on 16/11/27.
 */
@Service
public class Log4jService extends AnnotationAspectLog {

    public void before(AspectBean bean) {
        Log logger = getLogger(bean);
        if (ObjectUtils.isNotEmpty(logger)){

            String appName = bean.getName();
            String appDes = bean.getDescription();
            Object[] args = bean.getArgs();
            Object staticPart = bean.getStaticPart();
            String info = "[Before] aspectCode:"+bean.hashCode()+";appName:"+appName+";appDes:"+appDes+";staticPart:"+staticPart+";args:"+ JsonUtil.toJson(args);
            logger.info(info);
        }
    }

    public void after(AspectBean bean) {
        Log logger = getLogger(bean);
        if (ObjectUtils.isNotEmpty(logger)){
            String appName = bean.getName();
            String appDes = bean.getDescription();
            Object[] args = bean.getArgs();
            Object staticPart = bean.getStaticPart();
            Object result = bean.getResult();
            String info = "[After] aspectCode:"+bean.hashCode()+";appName:"+appName+";appDes:"+appDes+";staticPart:"+staticPart+";args:"+ JsonUtil.toJson(args)+";res:"+JsonUtil.toJson(result);
            logger.info(info);
        }
    }

    public void exception(AspectBean bean, Throwable throwable) {
        Log logger = getLogger(bean);
        if (ObjectUtils.isNotEmpty(logger)){
            String appName = bean.getName();
            String appDes = bean.getDescription();
            Object[] args = bean.getArgs();
            Object staticPart = bean.getStaticPart();
            String info = "[Exception] aspectCode:"+bean.hashCode()+";appName:"+appName+";appDes:"+appDes+";staticPart:"+staticPart+";args:"+ JsonUtil.toJson(args);
            logger.info(info,throwable);
        }
    }
    private Log getLogger(AspectBean bean){
        Log logger = null;
        if (ObjectUtils.isNotEmpty(bean) && ObjectUtils.isNotEmpty(bean.getName())){
//            logger = Logger.getLogger(bean.getName());
            Log log = MyLogFactory.productLog(LogType.Default,bean.getName());
            if (ObjectUtils.isEmpty(logger)){
                System.err.println("Log4j can not find "+bean.getName() +" logger");
            }
        }
        return logger;
    }
    public Log4jService(){
        setLogType(LogType.Default);
    }
}