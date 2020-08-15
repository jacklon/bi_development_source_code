package org.java.bi.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by HJYIN on 14-11-26.
 */
public class DateUtil {

    /**
     * 将字符串转换成日期格式
     */
    public static LocalDateTime getLocalDateTimeFromStringHMS(String time) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15:234";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(time, df);
        } catch (Exception e) {
            //e.printStackTrace();
            try {
                try
                {
                    df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    dateTime = LocalDateTime.parse(time, df);

                } catch (Exception ye){
                    try {
                        df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dateTime = LocalDateTime.parse(time, df);;
                    } catch (Exception ze){
                        try
                        {
                            df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            dateTime = LocalDateTime.parse(time, df);
                        } catch (Exception ey) {
                            ey.printStackTrace();
                        }
                    }
                }
            } catch (Exception ex) {
            }
        }
        return dateTime;
    }


    /**
     * 将字符串转换成日期格式
     */
    public static LocalDateTime getLocalDateTimeFromStringHHMM(String time) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(time, df);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                dateTime = getLocalDateTimeFromStringHM(time);
            } catch (Exception ex) {
                try
                {
                    dateTime = getLocalDateTimeFromStringYYYYMMDD(time);
                } catch (Exception ey) {
                    ey.printStackTrace();
                }

            }
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
    public static LocalDateTime getLocalDateTimeFromStringYYYYMMDD(String time) {
        //字符串转时间
        //String dateTimeStr = "2018-07-28 14:11:15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(time, df);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }


    public static String getTime() {
        LocalDateTime now=LocalDateTime.now();
        String nowTime =now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return nowTime;
    }

    public static String getDate() {
        LocalDateTime now=LocalDateTime.now();
        String nowDate =now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return nowDate;
    }



    public static String getDateyyyMMddHHmmss_Format(LocalDateTime value) {
        if(value==null){
            return null;
        }

        String nowTime =value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return nowTime;
    }



    public  static void main(String[] args){

    }



}
