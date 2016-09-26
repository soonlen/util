package com.wzf.com.commutil.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类
 */
@SuppressLint("SimpleDateFormat")
public class DateU {

    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DATE = "dd";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String DATE_INTERVAL_DD = "dd";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String LONG_DATE_FMT = "yyyyMMddHHmmss";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static ThreadLocal<Calendar> calInstance = new ThreadLocal<Calendar>();
    private static final ThreadLocal<SimpleDateFormat> sdf_hms = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> sdf_hm = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    /**
     * 返回两个日期的分钟数差
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int minuteDiff(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        Long diff = (d1.getTime() - d2.getTime()) / 1000 / 60;
        return diff.intValue();
    }

    /**
     * 格式-- yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDateHMS(Long date) {
        if (date == null) {
            return null;
        }
        return sdf_hms.get().format(toRealDate(date));
    }

    public static String formatDateHM(Date date) {
        if (date == null) {
            return null;
        }
        return sdf_hm.get().format(date);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdff = new SimpleDateFormat(format);
        return sdff.format(date);
    }

    public static String formatDate(Long date, String format) {
        if (date == null) {
            return null;
        }
        return formatDate(toRealDate(date), format);
    }

    public static int getSpecialTime(Date date, int type) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(type);
    }

    /**
     * 为了避免线程冲突，每个线程保存一个Calendar实例 -- by PanJun
     *
     * @return 线程级Calendar实例
     */
    private static Calendar getCalendar() {
        if (calInstance.get() == null)
            calInstance.set(Calendar.getInstance());
        Calendar ret = calInstance.get();
        ret.clear();
        return ret;
    }

    /**
     * 将日期转换为长整型数字
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static Long toLongDate(Date date, String formatStr) {
        if (date == null)
            return null;
        if (formatStr == null) {
            formatStr = "yyyyMMddHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return Long.valueOf(sdf.format(date));
    }

    /**
     * 获取13周前的日期(不算本周，每周第一天是以周一开始算)
     *
     * @param stage 是否本期
     * @return
     */
    public static String prevWeek(String stage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int week = 13; // 取13周前的日期
        Calendar cal = Calendar.getInstance();
        // 不是本期，取上期的
        if (!"Y".equals(stage)) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        }
        // 获取当前日期是星期几
        int wd = cal.get(Calendar.DAY_OF_WEEK);
        // 如果不是等于星期一
        if (wd != 2) {
            if (wd < 2) {// 如果是星期日。加到星期一再做计算
                cal.add(Calendar.DAY_OF_MONTH, 1);
            } else {// 如果是星期二到星期六，减到星期一再做计算
                cal.add(Calendar.DAY_OF_MONTH, -(wd - 2));
            }
        }
        // 减掉13周
        cal.add(Calendar.DAY_OF_MONTH, -(week * 7));
        return sdf.format(cal.getTime()) + "000000";
    }

    /**
     * 当前周的周一
     *
     * @param stage 是否本期
     * @return
     */

    public static String curWeek(String stage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        // 不是本期，取上期的
        if (!"Y".equals(stage)) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        }
        // 获取当前日期是星期几
        int wd = cal.get(Calendar.DAY_OF_WEEK);
        // 如果不是等于星期一
        if (wd != 2) {
            if (wd < 2) {// 如果是星期日。加到星期一再做计算
                cal.add(Calendar.DAY_OF_MONTH, 1);
            } else {// 如果是星期二到星期六，减到星期一再做计算
                cal.add(Calendar.DAY_OF_MONTH, -(wd - 2));
            }
        }
        return sdf.format(cal.getTime()) + "000000";
    }

    /**
     * 获取当前时间的周
     *
     * @param dateStr 时间：格式yyyyMMddHHmmss
     * @return
     * @throws ParseException
     */
    public static int getWeek(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = sdf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 得到指定日期是星期几
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek2(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        if (dayOfWeek == 1) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        L.i("day of week:" + dayOfWeek);
        return dayOfWeek;
    }

    /**
     * 获取当前为周几
     *
     * @param date
     * @return
     */
    public static String getChineseDayOfWeekForDate(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        if (dayOfWeek == 1) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        String day = LanguageUtil.getChineseNumFromNum(dayOfWeek, true);
        return "七".equals(day) ? "日" : day;
    }

    /**
     * 把日期加天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        if (date == null)
            return null;

        if (day == 0)
            return date;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 把日期加小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 加分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 把日期加秒
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 把日期加天数，返回结果
     *
     * @param date
     * @param day
     * @return
     */
    public static Long addDay(Long date, int day) {
        if (date == null)
            return null;

        Date realDate = addDay(toRealDate(date), day);
        return toLongDate(realDate);
    }

    /**
     * 请参看Calender.get(field)函数
     *
     * @param date
     * @param field
     * @return
     */
    public static int get(Date date, int field) {
        if (date == null) {
            return -1;
        }

        Calendar cal = getCalendar();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 请参看Calender.get(field)函数
     *
     * @param date
     * @param field
     * @return
     */
    public static int get(Long date, int field) {
        return get(toRealDate(date), field);
    }

    /**
     * 把长整形日期转换成真实日期
     *
     * @param date
     * @return
     */
    public static Date toRealDate(Long date) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        int ss = (int) (date - (date / 100) * 100);
        calendar.set(Calendar.SECOND, ss);

        int mi = (int) ((date - (date / 10000) * 10000) / 100);
        calendar.set(Calendar.MINUTE, mi);

        int hh = (int) ((date - (date / 1000000) * 1000000) / 10000);
        calendar.set(Calendar.HOUR_OF_DAY, hh);

        int dd = (int) ((date - (date / 100000000) * 100000000) / 1000000);
        calendar.set(Calendar.DAY_OF_MONTH, dd);

        int yy = (int) (date / 10000000000l);
        calendar.set(Calendar.YEAR, yy);

        int noYear = (int) (date - yy * 10000000000l);
        int mm = noYear / 100000000;
        calendar.set(Calendar.MONTH, mm - 1);
        return calendar.getTime();
    }

    /**
     * Date类型转换成Long型日期
     *
     * @param date
     * @return
     */
    public static Long toLongDate(Date date) {
        if (date == null)
            return null;
        return Long.valueOf(new SimpleDateFormat(LONG_DATE_FMT).format(date));
    }

    /**
     * Date类型转换成String型日期
     *
     * @param date
     * @return
     */
    public static String toStrDate(Long date, String type) {
        Date newDate = toRealDate(date);
        if (YYYY_MM_DD.equals(type)) {
            return dateToStr(newDate);
        } else {
            return dateToStr(newDate, YYYY_MM_DD_HH_MM_SS);
        }
    }

    /**
     * Date类型转换成String型日期
     *
     * @param date
     * @return
     */
    public static String toStrDateDef(Long date) {
        Date newDate = toRealDate(date);
        return dateToStr(newDate);
    }

    public static Long toLongDateStr(String date) {
        if (date == null)
            return null;

        Date newDate = strToDate(date);
        return toLongDate(newDate);
    }

    public static Long toLongDateStrWithMin(String date) {
        if (date == null)
            return null;

        Date newDate = strToDate(date, YYYY_MM_DD_HH_MM_SS);
        return toLongDate(newDate, null);
    }

    /**
     * 把日期加月份
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 把日期加月份
     *
     * @param date
     * @param month
     * @return
     */
    public static Long addMonth(Long date, int month) {
        if (date == null)
            return null;

        Date realDate = addMonth(toRealDate(date), month);
        return toLongDate(realDate);
    }

    /**
     * 日期加年
     *
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date, int year) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 把日期的时分秒去除只留年月日
     *
     * @param date
     * @return 只留年月日的日期
     */
    public static Date clearTime(Date date) {
        if (date == null)
            return null;

        Calendar calendar = getCalendar();
        calendar.setTime(date);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        return calendar.getTime();
    }

    /**
     * 把日期的时分秒去除只留年月日 20101230115511 == >20101230115511
     *
     * @param date
     * @return 只留年月日的日期
     */
    public static Long clearTime(Long date) {
        if (date == null)
            return null;

        return (date / 1000000) * 1000000;
    }

    /**
     * 日期转化为字串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        if (date == null || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToStr(Long date, String pattern) {
        if (date == null || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(toRealDate(date));
    }

    /**
     * 日期时间转化为字串yyyy-MM-dd HH:mm:SS
     *
     * @param date
     * @return
     */
    public static String dateTimeToStr(Date date) {
        return dateToStr(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 把日期转化成"yyyy-MM-dd"格式的字串
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, YYYY_MM_DD);
    }

    /**
     * 字串转化成日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date strToDate(String date, String pattern) {
        if (StringU.isBlank(date) || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回两个日期之间的天数差
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int dateDiff(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            throw new NullPointerException("dateDiff方法两个参数不能为null!");

        Long diff = (d1.getTime() - d2.getTime()) / 1000 / 60 / 60 / 24;
        return diff.intValue();
    }

    /**
     * 返回两个日期之间的天数差
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int dateDiff(Long d1, Long d2) {
        return dateDiff(toRealDate(d1), toRealDate(d2));
    }

    /**
     * 把"yyyy-MM-dd"格式的字串转化成日期
     *
     * @param date
     * @return
     */
    public static Date strToDate(String date) {
        return strToDate(date, YYYY_MM_DD);
    }

    /**
     * 判断给定日期是否为当天
     *
     * @return
     */
    public static boolean isTodayDate(Long datel) {
        long t = Long.valueOf(DateU.format(new Date(DateU.getTodayZero()), DateU.LONG_DATE_FMT));
        if (t == datel)
            return true;
        Date dt = DateU.toRealDate(datel);

        Calendar calendar = getCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long beginTime = calendar.getTime().getTime();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long endTime = calendar.getTime().getTime();

        if (dt.getTime() >= beginTime && dt.getTime() <= endTime) {
            L.i("dt:" + dt.getTime() + ",beg:" + beginTime + ",end:" + endTime);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为今天
     *
     * @param d
     * @return
     */
    public static boolean isTodayDate(Date d) {
        if (d == null)
            return false;
        long t = Long.valueOf(DateU.format(new Date(DateU.getTodayZero()), DateU.LONG_DATE_FMT));
        long dd = Long.valueOf(format(d, LONG_DATE_FMT));
        return t == dd;
    }

    /**
     * 以指定格式格式化日期，并输出字符串
     */
    public static String format(Date date, String formatStr) {
        if (date == null)
            return "";
        if (formatStr == null)
            formatStr = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 获取一个月的第一天
     *
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定星期的日期
     *
     * @param date
     * @return
     */
    public static Date getWeekDate(Date date, int week) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, week);
        return calendar.getTime();
    }

    /**
     * 获取每年的一月1号
     *
     * @param date
     * @return
     */
    public static Date getYearFirstDay(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    public static Long getTomorrowDay(Long now) {
        String nows = now.toString();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dnow = null;
        try {
            dnow = sf.parse(nows);
        } catch (ParseException e) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dnow);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomoDate = cal.getTime();
        return toLongDate(tomoDate);
    }

    public static Long getYesterdayDay(Long now) {
        String nows = now.toString();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dnow = null;
        try {
            dnow = sf.parse(nows);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dnow);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date tomoDate = cal.getTime();
        return toLongDate(tomoDate);
    }

    public static boolean isOneDay(Long l1, Long l2) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String s1 = l1.toString();
        Date d1 = null;
        try {
            d1 = sf.parse(s1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date d3 = cal.getTime();
        Long l3 = toLongDate(d3);
        if (l2.toString().equals(l3.toString()))
            return true;
        return false;

    }

    public static boolean isOneDay(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        String s1 = format(d1, DEFAULT_FORMAT);
        String s2 = format(d2, DEFAULT_FORMAT);
        return s1.equals(s2);
    }

    public static Date parse(String source, String formatStr) {
        if (source == null)
            return null;
        if (formatStr == null)
            formatStr = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期运算
     *
     * @param field
     * @param amount
     * @param date
     * @return
     */
    public static Date addDate(String field, int amount, Date date) {
        if (amount == 0) {
            return date;
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        if (field.equals(YEAR)) {
            calendar.add(Calendar.YEAR, amount);
        } else if (field.equals(MONTH)) {
            calendar.add(Calendar.MONTH, amount);
        } else if (field.equals(DATE)) {
            calendar.add(Calendar.DATE, amount);
        }
        return calendar.getTime();
    }

    public static String format(Date date) {
        return format(date, null);
    }

    public static String dateAdd(String item, int amount, String startDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        if (startDate != null) {
            if (startDate.length() < 11)
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
            date = sdf.parse(startDate);
        } else {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (item.equals("yyyy"))
            calendar.add(Calendar.YEAR, amount);
        if (item.equals("MM"))
            calendar.add(Calendar.MONTH, amount);
        if (item.equals("dd"))
            calendar.add(Calendar.DAY_OF_MONTH, amount);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取后一年,只精确到天
     *
     * @param date
     * @return
     */
    public static long getLastYear(String date) {
        if (null == date || "".equals(date))
            date = DateU.format(new Date(), DEFAULT_FORMAT);
        Long d = DateU.toLongDate(DateU.parse(date, DEFAULT_FORMAT));
        return d.longValue() + 10000000000L;
    }

    /**
     * 获取前一年,只精确到天
     *
     * @param date
     * @return
     */
    public static long getBeforYear(String date) throws Exception {
        if (null == date || "".equals(date))
            date = DateU.format(new Date(), DEFAULT_FORMAT);
        Long d = DateU.toLongDate(DateU.parse(date, DEFAULT_FORMAT));
        return d.longValue() - 10000000000L;

    }

    /**
     * 获取当前日期,只精确到天
     *
     * @return
     */
    public static long getNowDate() throws Exception {
        String d = DateU.format(new Date(), DEFAULT_FORMAT);
        Long date = DateU.toLongDate(DateU.parse(d, DEFAULT_FORMAT));
        return date.longValue();
    }

    /**
     * 计算日期相差的天数
     *
     * @param start
     * @param end
     * @return
     */
    public static long getDiff(Long start, Long end) {
        //        String fr = "yyyy-MM-dd";
        if (start == null) {
            String d = DateU.format(new Date(), DEFAULT_FORMAT);
            start = DateU.toLongDate(DateU.parse(d, DEFAULT_FORMAT));
        }
        if (end == null) {
            String d = DateU.format(new Date(), DEFAULT_FORMAT);
            end = DateU.toLongDate(DateU.parse(d, DEFAULT_FORMAT));
        }
        Date startD = DateU.toRealDate(start);
        Date endD = DateU.toRealDate(end);
        return (endD.getTime() - startD.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static int getDiffDay(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
    }

    // 获取指定时间的周一（西方周天为一周的开始，如果是周天，获取的日期为下周的周一）
    public static Date getWeekStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        return cal.getTime();
    }

    // 获取指定时间的周天
    public static Date getWeekEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    // 获取指定时间的月初
    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    // 获取指定时间的月末
    public static Date getMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    //获取上月开始月初
    public static Date getLastMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    public static long getTodayZero() {
        Date date = new Date();
        return clearTime(date).getTime();
    /*	long l = 24*60*60*1000; //每天的毫秒数
		//date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。 
		//减8个小时的毫秒值是为了解决时区的问题。
		return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);*/
    }

    /**
     * @param date long 20151005122323
     * @return 周日
     */
    public static String getDayOfWeekForLong2(long date) {
        Date newDate = toRealDate(date);
        int week = getDayOfWeek(newDate);
        if (week == 1) {
            week = 7;
        } else {
            week -= 1;
        }
        switch (week) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return null;
        }
    }

    //获取年
    public static int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    //获取月
    public static int getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    //获取在本月中的天数
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthDays(Date date) {
        int month = getMonthFromDate(date);
        int year = getYearOfDate(date);
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29;
        }
        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }
        return days;
    }

    public static Date getDateOfMonthFromYearAndMonth(int year, int month) {
        Calendar cal = getCalendar();
//		cal.set(year, month, 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 根据两个日期得到相差的月数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDiffMonth(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int month = calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) * 12;
        calendar.setTime(end);
        int endMonth = calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) * 12;
        return endMonth - month + 1;
    }
}
