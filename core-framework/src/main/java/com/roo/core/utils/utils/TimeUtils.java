package com.roo.core.utils.utils;

import android.os.Build;
import android.text.TextUtils;

import android.text.format.Time;


import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：${Arvin} on 2017/11/10 15:38
 * <p>
 * 描述：
 */

public class TimeUtils {
    private static final int HOUR = 60 * 60 * 1000;
    private static final int MIN = 60 * 1000;
    private static final int SEC = 1000;
    /**
     * one hour in ms
     */
    private static final int ONE_HOUR = 1 * 60 * 60 * 1000;
    /**
     * one minute in ms
     */
    private static final int ONE_MIN = 1 * 60 * 1000;
    /**
     * one second in ms
     */
    private static final int ONE_SECOND = 1 * 1000;
    private static String format1;

    public static String parseDuration(long duration) {
        int hour = (int) (duration / HOUR);
        int min = (int) (duration % HOUR / MIN);
        int sec = (int) (duration % MIN / SEC);
        if (hour == 0) {
            return String.format("%02d:%02d", min, sec);
        } else {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    public static String countDownTimer(long duration) {
        int hour = (int) (duration / HOUR);
        int min = (int) (duration % HOUR / MIN);
        int sec = (int) (duration % MIN / SEC);
        if (hour == 0) {
            return String.format("%02d:%02d", min, sec);
        } else {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    public static String countDownTimerStrig(long duration) {
        int hour = (int) (duration / HOUR);
        int min = (int) (duration % HOUR / MIN);
        int sec = (int) (duration % MIN / SEC);
        if (hour == 0) {
            return String.format("%02d分%02d秒", min, sec);
        } else {
            return String.format("%02d时%02d分", hour, min);
        }
    }



    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getDate(long time) {
        String format = "yyyy-MM-dd";
        Date curDate = new Date(time);//获取当前时间
        return new SimpleDateFormat(format).format(curDate);
    }

    public static String getDate_dd(long time) {
        String format = "yyyy-MM-dd HH:mm";
        Date curDate = new Date(time);//获取当前时间
        return new SimpleDateFormat(format).format(curDate);
    }

    public static String getDate_mm_dd(long time) {
        String format = "M月dd日 HH:mm";
        Date curDate = new Date(time);//获取当前时间
        return new SimpleDateFormat(format).format(curDate);
    }

    public static String getDate_mm_dd_ss(long time) {
        String format = "M月dd日 HH:mm:ss";
        Date curDate = new Date(time);//获取当前时间
        return new SimpleDateFormat(format).format(curDate);
    }

    public static String getDate_ymd_mm_dd_ss(long time) {
        String format = "yyyy/MM/dd HH:mm:ss";
        Date curDate = new Date(time);//获取当前时间
        return new SimpleDateFormat(format).format(curDate);
    }
    /**
     * 将时间格式换为几月几日 的形式
     *
     * @param time
     * @return
     */
    public static String dateFormat(String time) {
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.CHINESE);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format1 = null;
        try {
            long times = format.parse(time).getTime();
            Date date = new Date(times);
            format = new SimpleDateFormat("M月dd日 HH:mm");
            format1 = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format1;
    }

    public static String Date2hm(String _data) {
        SimpleDateFormat format = new SimpleDateFormat(" HH:mm");
        try {
            Date date = format.parse(_data);
            format1 = format.format(date);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String dealDateFormat(String oldDateStr) throws ParseException{
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date  date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//  Date date3 =  df2.parse(date1.toString());
        return df2.format(date1);
    }
    /**
     * 获取文件修改日期
     *
     * @param dataFormat
     * @param timeStamp
     * @return
     */
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }

    /* * 毫秒转化时分秒毫秒 */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//      Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分钟");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
//      if(milliSecond > 0) {
//          sb.append(milliSecond+"毫秒");
//      }
        return sb.toString();
    }

    public static String UTCStringtODefaultString(String UTCString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(UTCString);//拿到Date对象
            String str = sdf2.format(date);//输出格式：2017-00-22 09:28:33
            return str.format(String.valueOf(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * HH:mm:ss
     */
    public static String formatTime(long ms) {
        StringBuilder sb = new StringBuilder();
        int hour = (int) (ms / ONE_HOUR);
        int min = (int) ((ms % ONE_HOUR) / ONE_MIN);
        int sec = (int) (ms % ONE_MIN) / ONE_SECOND;
        if (hour == 0) {
//			sb.append("00:");
        } else if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (min == 0) {
            sb.append("00:");
        } else if (min < 10) {
            sb.append("0").append(min).append(":");
        } else {
            sb.append(min).append(":");
        }
        if (sec == 0) {
            sb.append("00");
        } else if (sec < 10) {
            sb.append("0").append(sec);
        } else {
            sb.append(sec);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("") || str.trim().equals("null");
    }


    /**
     * 将毫秒转化成播放器的时间显示, 例如 3000转换成00:blessing_04
     *
     * @param milliSec
     * @return 分钟：秒
     */
    public static String getMediaPlayerTimeFromMilli(int milliSec) {
        String sTime = "";
        int minute = milliSec / 1000 / 60;
        int second = milliSec / 1000 - minute * 60;
        if (second < 10 && minute < 10) {
            sTime = "0" + minute + ":0" + second;
        } else if (minute < 10 && second >= 10) {
            sTime = "0" + minute + ":" + second;
        } else if (minute >= 10 && second < 10) {
            sTime = "" + minute + ":0" + second;
        } else {
            sTime = "" + minute + ":" + second;
        }
        return sTime;
    }








    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour 开始小时，例如22
     * @param beginMin  开始小时的分钟数，例如30
     * @param endHour   结束小时，例如 8
     * @param endMin    结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;
        startTime.second = 0;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin-1;
        endTime.second = 60;

        if (!startTime.before(endTime)) {
// 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
// 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        return result;
    }

}


//    /* * 毫秒转化时分秒毫秒 */
//    public static String formatTime(Long ms) {
//        String sb = "";
//        Integer ss = 1000;
//        Integer mi = ss * 60;
//        Integer hh = mi * 60;
//        Integer dd = hh * 24;
//        Long day = ms / dd;
//        Long hour = (ms - day * dd) / hh;
//        Long minute = (ms - day * dd - hour * hh) / mi;
//        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
//
//        if (day > 0) {
////            sb.append(day + "天");
//            sb = day + "天" + hour + "小时";
//        }
//        if (hour > 0 && day <= 0) {
////            sb.append(hour + "小时");
//            sb = hour + "小时" + minute + "分钟";
//        }
//        if (minute > 0 && hour <= 0) {
////            sb.append(hour + "小时"+minute + "分钟");
////            sb = "0小时" + minute + "分钟";
//            sb = minute + "分钟";
//        }
//
//        if (second > 0 && minute == 0) {
////            sb.append(second + "秒");
////            sb = "0分钟" + second + "秒";
//            sb = second + "秒";
//        }
//        if (second > 0 && minute > 0) {
////            sb.append(second + "秒");
//            sb = minute + "分钟" + second + "秒";
//
//        }
//        if(second==0 && milliSecond==0){
//            sb = minute + "分钟" + second + "秒";
//        }
//        if (milliSecond > 0) {
////            sb.append(milliSecond + "毫秒");
//        }
//        return sb;
//    }
//}