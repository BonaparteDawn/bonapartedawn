package github.bonapartedawn.common.bean;
import org.springframework.util.Assert;
import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.enums.LogTime;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.utils.JsonUtil;

/**
 * 记录
 * @author Fuzhong.Yan
 * 2017年3月14日
 */
public class Record {
    /**记录名*/
    private String name;
    /**记录描述*/
    private String description;
    /**记录日志类型*/
    private LogType[] logType;
    /**记录时间*/
    private LogTime[] logTime;
    /**当前使用的记录类型*/
    private LogType useLogType;
    /**当前使用的记录时间*/
    private LogTime useLogTime;
    /**记录的对象名*/
    private String target;
    /**记录的方法*/
    private String method;
    /**记录的方法的执行的结果*/
    private Object result;
    /**记录的方法传入的参数*/
    private Object args;
    /**异常*/
    private Throwable throwable;
    public Record(){
        super();
    }
    public Record(LogInfo logInfo) {
        Assert.notNull(logInfo, "Record_Record_logInfo_null");
        setLogTime(logInfo.logTime());
        setLogType(logInfo.type());
        setName(logInfo.name());
        setDescription(logInfo.description());
    }
    public Record(LogInfo logInfo,LogTime useLogTime,LogType useLogType,String target,String method,Object args,Object result,Throwable throwable) {
        Assert.notNull(logInfo, "Record_Record_logInfo_null");
        Assert.notNull(useLogTime, "Record_Record_useLogTime_null");
        Assert.notNull(useLogType, "Record_Record_useLogType_null");
        Assert.notNull(target, "Record_Record_target_null");
        Assert.notNull(method, "Record_Record_method_null");
        setUseLogTime(useLogTime);
        setUseLogType(useLogType);
        setName(logInfo.name());
        setDescription(logInfo.description());
        setTarget(target);
        setMethod(method);
        setArgs(args);
        setResult(result);
        setThrowable(throwable);
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
    public LogType[] getLogType() {
        return logType;
    }
    public void setLogType(LogType[] logType) {
        this.logType = logType;
    }
    public LogTime[] getLogTime() {
        return logTime;
    }
    public void setLogTime(LogTime[] logTime) {
        this.logTime = logTime;
    }
    public LogType getUseLogType() {
        return useLogType;
    }
    public void setUseLogType(LogType useLogType) {
        this.useLogType = useLogType;
    }
    public LogTime getUseLogTime() {
        return useLogTime;
    }
    public void setUseLogTime(LogTime useLogTime) {
        this.useLogTime = useLogTime;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    public Object getArgs() {
        return args;
    }
    public void setArgs(Object args) {
        this.args = args;
    }
    public Throwable getThrowable() {
        return throwable;
    }
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
    @Override
    public String toString() {
        return "===="+this.useLogTime+"===="+this.target+"."+this.method+"===== detail:"+JsonUtil.toJson(this);
    }
}