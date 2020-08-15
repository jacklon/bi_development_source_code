package org.java.bi.db.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by HJYIN on 14-11-26.
 */
public class DateUtil {

    /**
     * 将字符串转换成日期格式
     */
    public static LocalDate getLocalShortDateFromString(String date) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime=null;
        try {
            dateTime = LocalDate.parse(date, df);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 将字符串转换成日期格式
     */
    public static LocalDateTime getLocalDateTimeFromStringHM(String time) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(time, df);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }
    /**
     * 将字符串转换成日期格式
     */
    public static LocalDateTime getLocalDateTimeFromString(String time) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";
        if(time.length()==10){
            time+=" 00:00:00";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(time, df);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 将字符串转换成日期格式
     */
    public static LocalDateTime getLocalDateTimeFromLocalDate(LocalDate localDate) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate= localDate.format(df)+" 00:00:00";

        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(strDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }


    /**
     * 将日期转成时间戳
     *
     * @param date
     * @return 转换异常返回 0
     */
    public static long getTime(Date date){
        long lSysTime1 = date.getTime();
        return lSysTime1;
    }


    public  static void main(String[] args){
        System.out.println(getTime(new Date())/1000/(60*60*24));
    }



}
