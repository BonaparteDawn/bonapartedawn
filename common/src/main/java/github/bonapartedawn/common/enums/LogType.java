package github.bonapartedawn.common.enums;

/**
 * Created by Fuzhong.Yan on 16/11/27.
 * 生成的日志类型(系统只支持Default,其他需要自己定义实现方法)
 */
public enum LogType {
    /**默认日志配置(slf4j或者log4j)*/
    Default,
    /**生成数据库日志到Mysql数据库当中*/
    MysqlDatabase,
    /**生成数据库日志到Oracle数据库当中*/
    Oracle,
    /**生成数据库日志到Sybase数据库当中*/
    Sybase,
    /**生成数据库日志到SqlServer数据库当中*/
    SqlServer,
    /**生成数据库日志到Hadoop当中*/
    Hadoop,
    /**其他*/
    Other
}
