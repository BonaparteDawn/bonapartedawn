package github.bonapartedawn.common.utils;

import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Fuzhong.Yan on 17/2/8.
 */
public class TimeUtil {
    /**
     * 单位:毫秒
     */
    public static long M_SECOND = 1;
    /**
     * 单位:秒
     */
    public static long SECOND = 1000 * M_SECOND;
    /**
     * 单位:分
     */
    public static long MINUTE = 60 * SECOND;
    /**
     * 单位:小时
     */
    public static long HOUR = 60 * MINUTE;
    /**
     * 单位:小时
     */
    public static long DAY = 24 * HOUR;
    /**
     * 单位:周
     */
    public static long WEEK = 7 * DAY;

    public static Date currentTime(){
        return EnvironmentUtil.getOperatingSystemCurrentTime();
    }
    public static Date addYear(int year){
        return addYear(currentTime(),year);
    }

    /**
     * 在指定日期添加指定年数
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date,int year){
        Calendar calendar = newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)+year);
        return calendar.getTime();
    }

    public static GregorianCalendar newInstance(){
        return new GregorianCalendar();
    }

    /**
     * 两个时间之差
     * @param end
     * @param start
     * @return
     */
    public static long difference2Long(Date end,Date start){
        Assert.notNull(end,"timeUtil_end_exception");
        Assert.notNull(end,"timeUtil_start_exception");
        long res = end.getTime() - start.getTime();
        if (res < 0){
            new IllegalArgumentException("start_end_compare_exception");
        }
        return res;
    }
    /**
     * 检验是否是周末
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        int dayOFWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOFWeek == 1 || dayOFWeek == 7 ? true:false;
    }
    /**
     * 检验是否是工作日
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @return
     */
    public static boolean isWorkDay(Date date){
        return !isWeekend(date);
    }
    /**
     * 在指定日期添加指定天数
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date,int days){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+days);
        return calendar.getTime();
    }
    /**
     * 在指定日期添加指定小时
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date,int hours){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+hours);
        return calendar.getTime();
    }
    /**
     * 在指定日期添加指定分钟
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date,int seconds){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+seconds);
        return calendar.getTime();
    }
    /**
     * 在指定日期添加指定秒
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date,int minutes){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+minutes);
        return calendar.getTime();
    }
    /**
     * 在指定日期添加指定毫秒
     * @author Fuzhong.Yan
     * 2017年3月22日
     * @param date
     * @param milliSeconds
     * @return
     */
    public static Date addMilliSeconds(Date date,int milliSeconds){
        GregorianCalendar calendar = TimeUtil.newInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)+milliSeconds);
        return calendar.getTime();
    }
}
