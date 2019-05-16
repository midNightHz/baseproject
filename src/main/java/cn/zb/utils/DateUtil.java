package cn.zb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

    public static final SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    static {

    }
    public final static String wtb[] = { "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December", };

    /**
     * 解析日期，日期的格式为 May 12, 2014
     * 
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseEnlishDate(String str) throws ParseException {
        str = str.replace(",", "");
        String[] strArray = str.split(" ");
        String month = strArray[0].toLowerCase();
        String day = strArray[1];
        String year = strArray[2];
        String dateStr;
        switch (month) {
        case "january":// 1月
            dateStr = year + "-" + "01-" + day;
            break;
        case "february":// 2月
            dateStr = year + "-" + "02-" + day;
            break;
        case "march":// 3月
            dateStr = year + "-" + "03-" + day;
            break;
        case "april":// 4月
            dateStr = year + "-" + "04-" + day;
            break;
        case "may":// 5月
            dateStr = year + "-" + "05-" + day;
            break;
        case "june":// 6月
            dateStr = year + "-" + "06-" + day;
            break;
        case "july":// 7月
            dateStr = year + "-" + "07-" + day;
            break;
        case "august":// 8月
            dateStr = year + "-" + "08-" + day;
            break;
        case "september":// 9月
            dateStr = year + "-" + "08-" + day;
            break;
        case "october":// 10月
            dateStr = year + "-" + "10-" + day;
            break;
        case "november":// 11月
            dateStr = year + "-" + "11-" + day;
            break;
        case "december":// 12月
            dateStr = year + "-" + "12-" + day;
            break;

        default:
            return null;
        }
        return parseDate(dateStr, null);
    }

    /**
     * 解析日期，默认的日期格式为yyyy-MM-dd
     * 
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf;
        if (!StringUtils.isEmpty(pattern)) {
            sdf = new SimpleDateFormat(pattern);
        } else
            sdf = YEAR_MONTH_DAY;

        return sdf.parse(dateStr);
    }

    /**
     * 格式化日期，默认的日期格式为yyyy-MM-dd
     * 
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String formartDate(Date date, String pattern) throws Exception {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf;
        if (!StringUtils.isEmpty(pattern)) {
            sdf = new SimpleDateFormat(pattern);
        } else
            sdf = YEAR_MONTH_DAY;

        return sdf.format(date);
    }

    /**
     * 时间加减的工具
     * 
     * @param toBeProcessDate
     *            待处理的时间
     * @param time
     *            规则 1d代表一天 1m代表一分钟，除月份和分钟之外，其他不区分大小写
     * @param subtract
     *            提前还是延后，提前为true 延后为false
     * @return
     * @throws Exception
     */
    public static Date processTime(Date toBeProcessDate, String time, boolean subtract) throws Exception {
        char lastLetter = time.charAt(time.length() - 1);
        int timeFiled;
        // 获取时间标度
        switch (lastLetter) {
        // 秒
        case 's':
        case 'S':
            timeFiled = Calendar.SECOND;
            break;
        // 分
        case 'm':
            timeFiled = Calendar.MINUTE;
            break;
        // 时
        case 'H':
        case 'h':
            timeFiled = Calendar.HOUR;
            break;
        // 天
        case 'D':
        case 'd':
            timeFiled = Calendar.DAY_OF_YEAR;
            break;
        // 周
        case 'W':
        case 'w':
            timeFiled = Calendar.WEEK_OF_YEAR;
            break;
        case 'M':
            timeFiled = Calendar.MONTH;
            break;
        case 'y':
        case 'Y':
            timeFiled = Calendar.YEAR;
            break;
        default:
            throw new Exception("通知时间格式不正确");
        }
        // 获取时间数值
        int timeAmont = new Integer(time.substring(0, time.length() - 1));
        if (subtract) {
            timeAmont = -timeAmont;
        }

        Calendar toBeProcessed = Calendar.getInstance();
        toBeProcessed.setTime(toBeProcessDate);
        toBeProcessed.add(timeFiled, timeAmont);
        return toBeProcessed.getTime();
    }

    /**
     * 
     * @param time
     *            规则 1d代表一天 1m代表一分钟，除月份和分钟之外，其他不区分大小写
     * @param subtract
     *            提前还是延后，提前为true 延后为false
     * @return
     * @throws Exception
     */
    public static Date processTime(String time, boolean subtract) throws Exception {
        return processTime(new Date(), time, subtract);
    }

    /**
     * 
     * @param time
     *            规则 1d代表一天 1m代表一分钟，除月份和分钟之外，其他不区分大小写
     * @return
     * @throws Exception
     */
    public static Date processTime(String time) throws Exception {
        return processTime(time, false);
    }

    /**
     * 
     * @description 时间的处理
     * @param time
     * @param timeZone
     *            时区
     * @return
     * @throws Exception
     *             author：chenjun
     * @date ：2018年8月16日 下午5:14:18
     * @return Date
     */
    public static Date processTime(String time, int timeZone) throws Exception {
        Date currDate = processTime(time);
        Calendar calendar = Calendar.getInstance();
        int currTimeOffset = calendar.getTimeZone().getRawOffset();
        int timeOffset = timeZone * 3600 * 1000;
        return new Date(currDate.getTime() + timeOffset - currTimeOffset);
    }

    /**
     * 
     * @description
     * @param time
     * @param timeZone
     *            +8:00 或者-8:30
     * @return
     * @throws Exception
     * @author：chenjun
     * @date ：2018年9月7日 下午3:52:58
     * @return Date
     */
    public static Date processTime(String time, String timeZone) throws Exception {
        if (timeZone == null || timeZone.trim().length() == 0) {
            return processTime(time);
        }
        Date currDate = processTime(time);
        Calendar calendar = Calendar.getInstance();
        int currTimeOffset = calendar.getTimeZone().getRawOffset();
        int timeOffset = getTimeOffset(timeZone);
        return new Date(currDate.getTime() + timeOffset - currTimeOffset);

    }

    /**
     * 
     * @description 根据时区计算当前时间与0时区的偏移量
     * @param timeZone
     *            GMT +8:00 中国北京
     * @return
     * @author：chenjun
     * @date ：2018年9月7日 下午3:48:28
     * @return Long
     */
    private static int getTimeOffset(String timeZone) {
        String reg = "[\\d\\:\\+\\-]";
        StringBuilder zone = new StringBuilder();
        for (int i = 0; i < timeZone.length(); i++) {
            String str = timeZone.charAt(i) + "";
            if (str.matches(reg)) {
                zone.append(str);
            }
        }
        timeZone = zone.toString();
        String[] offsetValues = timeZone.split(":");
        int hourOffset = new Integer(offsetValues[0]);
        int minOffset = new Integer(offsetValues[1]);
        if (hourOffset > 0) {
            return hourOffset * 3600 * 1000 + minOffset * 60 * 1000;
        } else {
            return (hourOffset + 1) * 3600 * 1000 - minOffset * 60 * 1000;
        }

    }

    /**
     * 
     * @description 时区计算
     * @param timeZone
     * @return
     * @author：chenjun
     * @date ：2018年9月6日 下午4:22:11
     * @return Date
     */
    public static Date processTime(Integer timeZone) {
        Date date = new Date();
        if (timeZone == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        int currTimeOffset = calendar.getTimeZone().getRawOffset();
        int timeOffset = timeZone * 3600 * 1000;
        return new Date(date.getTime() + timeOffset - currTimeOffset);
    }

    public static Date processTimeZone(String timeZone) {
        Date date = new Date();
        if (timeZone == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        int currTimeOffset = calendar.getTimeZone().getRawOffset();
        int timeOffset = getTimeOffset(timeZone);
        return new Date(date.getTime() + timeOffset - currTimeOffset);
    }

    public static String formartEnData(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        StringBuilder sb = new StringBuilder();
        // 月份
        sb.append(wtb[c.get(Calendar.MONTH)] + " ");
        // 日期
        sb.append(c.get(Calendar.DAY_OF_MONTH) + " ");
        // 年
        sb.append(c.get(Calendar.YEAR));
        return sb.toString();
    }

    public static Date now() {
        return new Date();
    }

    /**
    * 
    * @Title: diffTime   
    * @Description: 日期相减 date1-date2  
    * @author:陈军
    * @date 2019年5月16日 上午10:53:51 
    * @param date1
    * @param date2
    * @param formart  年月日时分秒等 @see cn.zb.utils.DateField
    * @return      
    * int      
    * @throws
    */
    public static int diffTime(Date date1, Date date2, DateField formart) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        int result = 0;
        int temp = 0;
        int multi = 1;
        int y1 = c1.get(Calendar.YEAR);
        int y2 = c2.get(Calendar.YEAR);
        switch (formart) {
        case SECOND:
            int s1 = c1.get(Calendar.SECOND);
            int s2 = c2.get(Calendar.SECOND);
            temp += (s1 - s2);
            multi *= 60;
        case MINITER:
            int mi1 = c1.get(Calendar.MINUTE);
            int mi2 = c2.get(Calendar.MINUTE);
            result = (mi1 - mi2) * multi + temp;// 0
            temp = result;
            multi *= 60;
        case HOUR:
            int h1 = c1.get(Calendar.HOUR_OF_DAY);
            int h2 = c2.get(Calendar.HOUR_OF_DAY);
            result = (h1 - h2) * multi + temp;
            temp = result;
            multi *= 24;
        case DAY:
            int d1 = c1.get(Calendar.DAY_OF_YEAR);
            int d2 = c2.get(Calendar.DAY_OF_YEAR);
            int days = 0;
            for (int i = y1; i < y2; i++) {
                if (i % 400 == 0 || (i % 100 != 0 && i % 4 == 0)) {
                    days += 366;
                } else {
                    days += 365;
                }
            }
            result = (days - d2 + d1) * multi + temp;
            break;
        case YEAR:
            result = y1 - y2;
            break;
        case MONTH:
            int m1 = c1.get(Calendar.MONTH);
            int m2 = c2.get(Calendar.MONTH);
            result = (y1 - y2) * 12 + (m1 - m2);
            break;

        default:
            return 0;
        }
        return result;
    }

    public static void main(String[] args) throws ParseException {
        Date d = parseDate("2018-11-2 12:00:01", "yyyy-MM-dd HH:mm:ss");
        Date d2 = parseDate("2018-10-1 12:00:01", "yyyy-MM-dd HH:mm:ss");
        Time t = new Time();
        int a = diffTime(d, d2, DateField.DAY);
        t.stop();
        System.out.println(a);
        System.out.println(t.getTime());
    }

    // 获取当前日期前一天的月份的第一天

    public static Date lastDayMonthFirst() {
        return null;

    }

    // 获取当前日期前一天的最后日期

    // 获取当前月份的前一个月份 格式 YYYY-MM

    public static String lastDayMonthStr() throws Exception {

        return formartDate(lastDay(), "yyyy-MM");

    }

    public static Date lastDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return c.getTime();

    }

    /**
     * 
     * @Title: getTimeField   
     * @Description: 获取  某个时间属性的值 如年 月 日等
     * @author:陈军
     * @date 2019年5月16日 下午1:23:00 
     * @param date
     * @param field
     * @return      
     * int      
     * @throws
     */
    public static int getTimeField(Date date, int field) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(field);
    }

    // 格式化当前日期前一天的月份 格式 YYYY-MM

    // 获取当前月份的前一年的月份 格式YYYY-MM

}
