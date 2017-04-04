package github.bonapartedawn.common.bean;

import github.bonapartedawn.common.enums.LogTime;
import github.bonapartedawn.common.enums.LogType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;


/**
 * Created by Fuzhong.Yan on 16/11/27.
 */
public class AspectBean {
    private Object sourceLocation;
    private Object target;
    private Object[] args;
    private String kind;
    private JoinPoint.StaticPart staticPart;
    private Object thisObj;
    private Object result;
    private Signature signature;

    /**日志记录类型*/
    private LogType[] type;
    /**应用名*/
    private String name;
    /**应用描述*/
    private String description;
    /**日志记录时机*/
    private LogTime[] logTimes;

    public Object getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(Object sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JoinPoint.StaticPart getStaticPart() {
        return staticPart;
    }

    public void setStaticPart(JoinPoint.StaticPart staticPart) {
        this.staticPart = staticPart;
    }

    public Object getThisObj() {
        return thisObj;
    }

    public void setThisObj(Object thisObj) {
        this.thisObj = thisObj;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public Signature getSignature() {
        return signature;
    }

    public LogType[] getType() {
        return type;
    }

    public void setType(LogType[] type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LogTime[] getLogTimes() {
        return logTimes;
    }

    public void setLogTimes(LogTime[] logTimes) {
        this.logTimes = logTimes;
    }
}
