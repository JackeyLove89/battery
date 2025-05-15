package com.cloud.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static String dateToString(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String format = f.format(date);
        return format;
    }
    public static String dateToString1(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = f.format(date);
        return format;
    }
    public static  long  time=1743038707+50*60*60*24;
    public static String dateToString(long date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String format = f.format(date);
        return format;
    }
    //判断是否在时间段内
    public static int compareTime(String time,String stime,String etime) {
        if (time.substring(0).equals("0")){
            time=time.substring(1,time.length()).replaceAll(":","");
        }else {
            time=time.replaceAll(":","");
        }
        if (stime.substring(0).equals("0")){
            stime=stime.substring(1,stime.length()).replaceAll(":","");
        }else {
            stime=stime.replaceAll(":","");
        }
        if (etime.substring(0).equals("0")){
            etime=etime.substring(1,etime.length()).replaceAll(":","");
        }else {
            etime=etime.replaceAll(":","");
        }
        if (Integer.parseInt(time)<Integer.parseInt(stime)){
            return 1;//无效
        }else if (Integer.parseInt(time)<=Integer.parseInt(etime)){
            return 2;//正常
        }else {
            return 3;//无效
        }

    }
    //判断是时间前后
        public static int compareTwoTime(String stime,String etime) {
        if (stime.substring(0).equals("0")){
            stime=stime.substring(1,stime.length()).replaceAll(":","");
        }else {
            stime=stime.replaceAll(":","");
        }
        if (etime.substring(0).equals("0")){
            etime=etime.substring(1,etime.length()).replaceAll(":","");
        }else {
            etime=etime.replaceAll(":","");
        }
        if (Integer.parseInt(stime)<=Integer.parseInt(etime)){
            return 1;//
        } {
            return 2;//无效
        }

    }
    //判断是否是今天
    public static boolean isTodayDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM:dd");
        String format = f.format(date);
        String format1 = f.format(new Date());
        if (format.equals(format1)){
            return true;
        }else {
            return false;
        }

    }
    //判断是否是当月
    public static boolean isMonthDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM");
        String format = f.format(date);
        String format1 = f.format(new Date());
        if (format.equals(format1)){
            return true;
        }else {
            return false;
        }

    }   //判断是否是当年
    public static boolean isYearDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        String format = f.format(date);
        String format1 = f.format(new Date());
        if (format.equals(format1)){
            return true;
        }else {
            return false;
        }

    }
    //获取前一天
    public static String getLastDate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM:dd");
        String format = f.format(new Date().getTime() - 24 * 60 * 60 * 1000);
        return format;
    }
    //获取下一天
    public static String getNextDate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM:dd");
        String format = f.format(new Date().getTime() + 24 * 60 * 60 * 1000);
        return format;
    }
    //获取时间段
    public static List<String> getDays(Date start, Date end) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
            Collections.reverse(days);
            return days;
    }
    //获取时间段
    public static List<String> getSevenDays() {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(new Date(new Date().getTime()-7*24*60*60*1000));
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(new Date());
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }
    //获取时间段
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.reverse(days);
        return days;
    }
    //获取两个时间中间时间
    public static String getMiddleTime(String stime,String etime) {
        String  time = null;
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");

        try {
            Date parse = f.parse(stime);
            Date parse1 = f.parse(etime);

            long l1 = parse.getTime() + (parse1.getTime() - parse.getTime()) / 2;
            time = f.format(l1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    //获取两个时间件秒数
    public static Long getBetwonTime(String stime,String etime) {
        Long  time = null;
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");

        try {
            Date parse = f.parse(stime);
            Date parse1 = f.parse(etime);

            long l1 = parse1.getTime() - parse.getTime();
            time = l1/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public static String timeToString(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        String format = f.format(date);
        return format;
    }
    public static String timeToString1(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        String format = f.format(date);
        return format;
    }
    public static String dateFormatTo(String date) {


        return date.substring(0,10);
    }


    public static String getFisrtDayOfMonth(String year,String month)
    {
        Calendar cal = Calendar.getInstance();

        //设置年份
        cal.set(Calendar.YEAR,Integer.parseInt(year));
        if (month.substring(0,1).equals("0")){
            month=month.substring(1,month.length());
        }
        //设置月份
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }
    public static String getLastDayOfMonth(String year,String month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,Integer.parseInt(year));
        if (month.substring(0,1).equals("0")){
            month=month.substring(1,month.length());
        }
        //设置月份
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }
}
