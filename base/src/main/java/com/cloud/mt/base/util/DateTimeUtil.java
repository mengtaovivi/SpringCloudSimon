package com.cloud.mt.base.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 日期时间相关工具,包括Date、LocalDate、LocalDateTime
 * @author Ifan
 */
@Slf4j
public final class DateTimeUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd
     */
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前标准格式化日期时间
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}

	/**
	 * 标准格式化日期时间
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return (new SimpleDateFormat(DATE_FORMAT)).format(date);
	}

    /**
     * 获取开始时间
     * @param date 日期格式字符串（2019-05-22）
     * @return 返回Date类型的标准的时间格式（2019-05-22 00:00:00）
     */
    public static Date getStartDate(String date){
        SimpleDateFormat format = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT);
        try {
            return format.parse(date + " 00:00:00");
        } catch (ParseException e) {
            log.error("!-ERROR-! {}",e) ;
        }
        return null;
    }

    /**
     * 获取结束时间
     * @param date 日期格式字符串（2019-05-22）
     * @return 返回Date类型的标准的时间格式（2019-05-22 23:59:59）
     */
    public static Date getEndtDate(String date){
        SimpleDateFormat format = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT);
        try {
            return format.parse(date + " 23:59:59");
        } catch (ParseException e) {
            log.error("!-ERROR-! {}",e) ;
        }
        return null;
    }

    /**
     * 获得当前时间的yyyy-MM-dd格式字符串
     * @return String
     */
    public static String getCurrentDate(){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String nowDate = today.format(df);
        return nowDate;
    }

    /**
     * LocalDate转化为指定格式字符串
     * @param fromDate
     * @param dateFormat
     * @return
     */
    public static String getLocalDate(LocalDate fromDate, String dateFormat){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(dateFormat);
        if(fromDate != null){
            String dateStr = fromDate.format(df);
            return dateStr;
        }
        return null ;

    }
    public static String getLocalDateTime(LocalDateTime fromDateTime, String dateTimeFotmat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateTimeFotmat);
        if(fromDateTime != null){
            String localTime = fromDateTime.format(df);
            return localTime;
        }
        return null;

    }

    /**
     * 获得一年后的日期格式字符串
     */
    public static String getOneYearLaterDate(String beginDate,String dateFormat){
        LocalDate fromDate = fromString2LocalDate(beginDate,dateFormat);
        if(fromDate != null){
            LocalDate toDate = fromDate.plus(1, ChronoUnit.YEARS);
            return getLocalDate(toDate,dateFormat);
        }
        return null;

    }

    /**
     * 时间格式字符串转化为指定格式的时间
     * @param beginDate
     * @param dateFormat
     * @return
     */
    public static LocalDate fromString2LocalDate(String beginDate,String dateFormat){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate fromDate = LocalDate.parse(beginDate,df);
            return fromDate;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 时间格式字符串转化为指定格式的时间
     * @param beginDateTime
     * @param dateFormat
     * @return
     */
    public static LocalDateTime fromString2LocalDateTime(String beginDateTime,String dateFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(beginDateTime,df);
            return fromDateTime;
        } catch (Exception e) {
            return null;
        }

    }
    /**
     * 获得毫秒数
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }


    /**
     * 根据出生日期(yyyy-MM-dd)字符串计算年龄
     * @param birthDay
     * @return
     */
    public static Integer getAgeByBirthDay(String birthDay){
        LocalDate birthDate = fromString2LocalDate(birthDay,DAY_FORMAT);
        LocalDate currentDate = LocalDate.now();
        if(birthDate != null){
            //判断birthDay是否合法
            if(currentDate.isBefore(birthDate)){
                return 0 ;
            }else{
                int age = birthDate.until(currentDate).getYears();
                return age ;
            }

        }else{
            return null ;
        }

    }
    /**
     * Long类型时间戳转化为LocalDateTime
     * @param dateTimeLong
     * @return
     */
    public static LocalDateTime fromLong2LocalDateTime(Long dateTimeLong) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeLong), ZoneId.systemDefault());
        return dateTime;
    }

    /**
     * 获取本月第一天
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDate getFirstDayOfCurrentMonth() {
        LocalDate currentDay = LocalDate.now();
        return currentDay.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本月最后一天
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDate getLastDayOfCurrentMonth() {
        LocalDate currentDay = LocalDate.now();
        return currentDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取当天开始时间 2019-06-12 00:00:00
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getTodayBeginTime(){
        LocalDate currentDay = LocalDate.now();
        return LocalDateTime.of(currentDay, LocalTime.MIN);
    }

    /**
     * 获取当天结束时间 2019-06-12 23:59:59
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getTodayEndTime(){
        LocalDate currentDay = LocalDate.now();
        return LocalDateTime.of(currentDay, LocalTime.MAX);
    }

    /**
     * 获取本周开始时间 2019-06-10 00:00:00
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getWeekBeginTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentOrdinal = currentDateTime.getDayOfWeek().ordinal();
        return currentDateTime.minusDays(currentOrdinal)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取本周开始时间 2019-06-10 00:00:00
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static String getWeekBeginTimeString(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentOrdinal = currentDateTime.getDayOfWeek().ordinal();
        LocalDateTime weekBeginDateTime =  currentDateTime.minusDays(currentOrdinal)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        return getLocalDateTime(weekBeginDateTime,FULL_FORMAT);
    }

    /**
     * 获取本周结束时间 2019-06-16 23:59:59
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getWeekEndTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentOrdinal = currentDateTime.getDayOfWeek().ordinal();
        return currentDateTime.plusDays(6-currentOrdinal)
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    /**
     * 获取本周结束时间字符串 2019-06-16 23:59:59
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static String getWeekEndTimeString(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentOrdinal = currentDateTime.getDayOfWeek().ordinal();
        LocalDateTime weekEndDateTime = currentDateTime.plusDays(6-currentOrdinal)
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getLocalDateTime(weekEndDateTime,FULL_FORMAT);
    }

    /**
     * Date类型转LocalDate类型
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localPriceDate = instant.atZone(zoneId).toLocalDate();
        return localPriceDate;
    }

    /**
     * LocalDate类型转Date类型
     * @param localDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

}
