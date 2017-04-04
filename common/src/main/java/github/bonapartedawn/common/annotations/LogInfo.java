package github.bonapartedawn.common.annotations;

import github.bonapartedawn.common.enums.LogTime;
import github.bonapartedawn.common.enums.LogType;

import java.lang.annotation.*;

/**
 * Created by Fuzhong.Yan on 16/11/27.
 * 记录日志的标签
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogInfo {
     /**日志记录类型*/
     LogType[] type();
     /**名称*/
     String name();
     /**描述*/
     String description() default "";
     /**日志记录时机*/
     LogTime[] logTime() default {LogTime.AfterMethod,LogTime.BeforeMethod,LogTime.ExceptionMethod};
}