package com.cloud.mt.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 时间计算工具类
 *
 * @author 叶成浪
 */
public class DateUtil {

	/**
	 * the milli second of a day
	 */
	public static final long DAYMILLI = 24 * 60 * 60 * 1000;
	/**
	 * the milli seconds of an hour
	 */
	public static final long HOURMILLI = 60 * 60 * 1000;
	/**
	 * the milli seconds of a minute
	 */
	public static final long MINUTEMILLI = 60 * 1000;
	/**
	 * the milli seconds of a second
	 */
	public static final long SECONDMILLI = 1000;
	/**
	 * added time
	 */
	public static final String TIMETO = " 23:59:59";
	/**
	 * flag before
	 */
	public static final transient int BEFORE = 1;
	/**
	 * flag after
	 */
	public static final transient int AFTER = 2;
	/**
	 * flag equal
	 */
	public static final transient int EQUAL = 3;
	/**
	 * date format dd/MMM/yyyy:HH:mm:ss +0900
	 */
	public static final String TIME_PATTERN_LONG = "dd/MMM/yyyy:HH:mm:ss +0900";
	/**
	 * date format dd/MM/yyyy:HH:mm:ss +0900
	 */
	public static final String TIME_PATTERN_LONG2 = "dd/MM/yyyy:HH:mm:ss +0900";
	/**
	 * date format YYYY-MM-DD HH24:MI:SS
	 */
	public static final String DB_TIME_PATTERN = "YYYY-MM-DD HH24:MI:SS";
	/**
	 * date format YYYYMMDDHH24MISS
	 */
	public static final String DB_TIME_PATTERN_1 = "YYYYMMDDHH24MISS";
	/**
	 * date format dd/MM/yy HH:mm:ss
	 */
	public static final String TIME_PATTERN_SHORT = "dd/MM/yy HH:mm:ss";
	/**
	 * date format dd/MM/yy HH24:mm
	 */
	public static final String TIME_PATTERN_SHORT_1 = "yyyy/MM/dd HH:mm";
	/**
	 * date format yyyy年MM月dd日 HH:mm:ss
	 */
	public static final String TIME_PATTERN_SHORT_2 = "yyyy年MM月dd日 HH:mm:ss";

	/**
	 * ISO日期格式
	 */
	public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * date format yyyyMMddHHmmss
	 */
	public static final String TIME_PATTERN_SESSION = "yyyyMMddHHmmss";
	/**
	 * date format yyyyMMddHHmmssSSS
	 */
	public static final String TIME_PATTERN_MILLISECOND = "yyyyMMddHHmmssSSS";
	/**
	 * date format yyyyMMdd
	 */
	public static final String DATE_FMT_0 = "yyyyMMdd";
	/**
	 * date format yyyy/MM/dd
	 */
	public static final String DATE_FMT_1 = "yyyy/MM/dd";
	/**
	 * date format yyyy/MM/dd hh:mm:ss
	 */
	public static final String DATE_FMT_2 = "yyyy/MM/dd hh:mm:ss";
	/**
	 * date format yyyy-MM-dd
	 */
	public static final String DATE_FMT_3 = "yyyy-MM-dd";
	/**
	 * date format yyyy年MM月dd日
	 */
	public static final String DATE_FMT_4 = "yyyy年MM月dd日";
	/**
	 * date format yyyy-MM-dd HH
	 */
	public static final String DATE_FMT_5 = "yyyy-MM-dd HH";
	/**
	 * date format yyyy-MM
	 */
	public static final String DATE_FMT_6 = "yyyy-MM";
	/**
	 * date format MM月dd日 HH:mm
	 */
	public static final String DATE_FMT_7 = "MM月dd日 HH:mm";
	/**
	 * date format MM月dd日 HH:mm
	 */
	public static final String DATE_FMT_8 = "HH:mm:ss";
	/**
	 * date format MM月dd日 HH:mm
	 */
	public static final String DATE_FMT_9 = "yyyy.MM.dd";
	public static final String DATE_FMT_10 = "HH:mm";
	public static final String DATE_FMT_11 = "yyyy.MM.dd HH:mm:ss";
	/**
	 * date format yyyy年MM月dd日
	 */
	public static final String DATE_FMT_12 = "MM月dd日";
	public static final String DATE_FMT_13 = "yyyy年MM月dd日HH时mm分";
	public static final String DATE_FMT_14 = "yyyyMM";
	public static final String DATE_FMT_15 = "MM-dd HH:mm:ss";
	public static final String DATE_FMT_16 = "yyyyMMddHHmm";
	public static final String DATE_FMT_17 = "HHmmss";
	public static final String DATE_FMT_18 = "yyyy";
	/**
	 * date format yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.SHORT_DATE_PATTERN_LINE.formatter;
	private static final DateTimeFormatter SHORT_DATE_PATTERN_NONE_FORMATTER = TimeFormat.SHORT_DATE_PATTERN_NONE.formatter;
	private static final DateTimeFormatter LONG_DATE_PATTERN_LINE = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;

	private transient DateTimeFormatter formatter;

	public enum TimeFormat {
		// 短时间格式 年月日
		/**
		 * 时间格式：yyyy-MM-dd
		 */
		SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
		/**
		 * 时间格式：yyyy/MM/dd
		 */
		SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
		/**
		 * 时间格式：yyyy\\MM\\dd
		 */
		SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
		/**
		 * 时间格式：yyyyMMdd
		 */
		SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

		// 长时间格式 年月日时分秒
		/**
		 * 时间格式：yyyy-MM-dd HH:mm:ss
		 */
		LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),

		/**
		 * 时间格式：yyyy/MM/dd HH:mm:ss
		 */
		LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
		/**
		 * 时间格式：yyyy\\MM\\dd HH:mm:ss
		 */
		LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
		/**
		 * 时间格式：yyyyMMdd HH:mm:ss
		 */
		LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

		// 长时间格式 年月日时分秒 带毫秒
		LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),

		LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),

		LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),

		LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

		private transient DateTimeFormatter formatter;

		TimeFormat(String pattern) {
			formatter = DateTimeFormatter.ofPattern(pattern);

		}
	}

	public static String getDateFmt(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * localDateTime 转 自定义格式string
	 *
	 * @param localDateTime
	 * @param format        例：yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return localDateTime.format(formatter);

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * string 转 LocalDateTime
	 *
	 * @param dateStr 例："2017-08-11 01:00:00"
	 * @param format  例："yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static LocalDateTime stringToLocalDateTime(String dateStr, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDateTime.parse(dateStr, formatter);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据时间获取当月有多少天数
	 *
	 * @param date
	 * @return
	 */
	public static int getActualMaximum(Date date) {

		return dateToLocalDateTime(date).getMonth().length(dateToLocalDate(date).isLeapYear());
	}

	/**
	 * 根据日期获得星期
	 *
	 * @param date
	 * @return 1:星期一；2:星期二；3:星期三；4:星期四；5:星期五；6:星期六；7:星期日；
	 */
	public static int getWeekOfDate(Date date) {
		return dateToLocalDateTime(date).getDayOfWeek().getValue();
	}

	/**
	 * 计算两个日期LocalDate相差的天数，不考虑日期前后，返回结果>=0
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static int getAbsDateDiffDay(LocalDate before, LocalDate after) {

		return Math.abs(Period.between(before, after).getDays());
	}

	/**
	 * 计算两个时间LocalDateTime相差的天数，不考虑日期前后，返回结果>=0
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static int getAbsTimeDiffDay(LocalDateTime before, LocalDateTime after) {

		return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getDays());
	}

	/**
	 * YCL date2比date1多的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	/**
	 * 计算两个时间LocalDateTime相差的月数，不考虑日期前后，返回结果>=0
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static int getAbsTimeDiffMonth(LocalDateTime before, LocalDateTime after) {

		return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getMonths());
	}

	/**
	 * 计算两个时间LocalDateTime相差的年数，不考虑日期前后，返回结果>=0
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static int getAbsTimeDiffYear(LocalDateTime before, LocalDateTime after) {

		return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getYears());
	}

	/**
	 * 根据传入日期返回星期几
	 *
	 * @param date 日期
	 * @return 1-7 1：星期天,2:星期一,3:星期二,4:星期三,5:星期四,6:星期五,7:星期六
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定日期的当月的月份数
	 *
	 * @param date
	 * @return
	 */
	public static int getLastMonth(Date date) {
		return dateToLocalDateTime(date).getMonth().getValue();

	}

	/**
	 * 特定日期的当月第一天
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate newThisMonth(Date date) {
		LocalDate localDate = dateToLocalDate(date);
		return LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
	}

	/**
	 * 特定日期的当月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate lastThisMonth(Date date) {
		int lastDay = getActualMaximum(date);
		LocalDate localDate = dateToLocalDate(date);
		return LocalDate.of(localDate.getYear(), localDate.getMonth(), lastDay);
	}

	/**
	 * 特定日期的当年第一天
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate newThisYear(Date date) {
		LocalDate localDate = dateToLocalDate(date);
		return LocalDate.of(localDate.getYear(), 1, 1);

	}

	/*
	 * public static Timestamp getCurrentDateTime() { return new
	 * Timestamp(Instant.now().toEpochMilli());
	 *
	 * }
	 */

	/**
	 * 获取当前时间
	 *
	 * @return LocalDateTime
	 */
	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));

	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentDateTime() {
		return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentDateTimeToSeconds() {
		return LONG_DATE_PATTERN_LINE.format(LocalDateTime.now());
	}

	/**
	 * 修改日期时间的时间部分
	 *
	 * @param date
	 * @param customTime 必须为"hh:mm:ss"这种格式
	 */
	public static LocalDateTime reserveDateCustomTime(Date date, String customTime) {
		String dateStr = dateToLocalDate(date).toString() + " " + customTime;
		return stringToLocalDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	public static String nowDayTimeStamp() {
		return SHORT_DATE_PATTERN_NONE_FORMATTER.format(LocalDateTime.now());
	}

	/**
	 * 修改日期时间的时间部分
	 *
	 * @param date
	 * @param customTime 必须为"hh:mm:ss"这种格式
	 */
	public static LocalDateTime reserveDateCustomTime(Timestamp date, String customTime) {
		String dateStr = timestampToLocalDate(date).toString() + " " + customTime;
		return stringToLocalDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static final LocalDateTime zerolizedTime(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0,
				0, 0);

	}

	/**
	 * 把时间变成(yyyy-MM-dd 00:00:00:000)
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime zerolizedTime(Timestamp date) {
		LocalDateTime localDateTime = timestampToLocalDateTime(date);
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0,
				0, 0);
	}

	/**
	 * 把日期的时间变成(yyyy-MM-dd 23:59:59:999)
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime getEndTime(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 23,
				59, 59, 999 * 1000000);
	}

	/**
	 * 把时间变成(yyyy-MM-dd 23:59:59:999)
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime getEndTime(Timestamp date) {
		LocalDateTime localDateTime = timestampToLocalDateTime(date);
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 23,
				59, 59, 999 * 1000000);
	}

	/**
	 * 计算特定时间到 当天 23.59.59.999 的秒数
	 *
	 * @return
	 */
	public static int calculateToEndTime(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime end = getEndTime(date);
		return (int) (end.toEpochSecond(ZoneOffset.UTC) - localDateTime.toEpochSecond(ZoneOffset.UTC));
	}

	/**
	 * 增加或减少年/月/周/天/小时/分/秒数
	 *
	 * @param localDateTime 例：ChronoUnit.DAYS
	 * @param chronoUnit
	 * @param num
	 * @return LocalDateTime
	 */
	public static LocalDateTime addTime(LocalDateTime localDateTime, ChronoUnit chronoUnit, int num) {
		return localDateTime.plus(num, chronoUnit);
	}

	/**
	 * 增加或减少年/月/周/天/小时/分/秒数
	 *
	 * @param chronoUnit 例：ChronoUnit.DAYS
	 * @param num
	 * @return LocalDateTime
	 */
	public static LocalDateTime addTime(Date date, ChronoUnit chronoUnit, int num) {
		long nanoOfSecond = (date.getTime() % 1000) * 1000000;
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond,
				ZoneOffset.of("+8"));
		return localDateTime.plus(num, chronoUnit);
	}

	/**
	 * 增加或减少年/月/周/天/小时/分/秒数
	 *
	 * @param chronoUnit 例：ChronoUnit.DAYS
	 * @param num
	 * @return LocalDateTime
	 */
	public static LocalDateTime addTime(Timestamp date, ChronoUnit chronoUnit, int num) {
		long nanoOfSecond = (date.getTime() % 1000) * 1000000;
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond,
				ZoneOffset.of("+8"));
		return localDateTime.plus(num, chronoUnit);
	}

	/**
	 * Date 转 LocalDateTime
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		long nanoOfSecond = (date.getTime() % 1000) * 1000000;
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond,
				ZoneOffset.of("+8"));

		return localDateTime;
	}

	/**
	 * Timestamp 转 LocalDateTime
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime timestampToLocalDateTime(Timestamp date) {
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, date.getNanos(),
				ZoneOffset.of("+8"));

		return localDateTime;
	}

	/**
	 * Date 转 LocalDateTime
	 *
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate dateToLocalDate(Date date) {

		return dateToLocalDateTime(date).toLocalDate();
	}

	/**
	 * timestamp 转 LocalDateTime
	 *
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate timestampToLocalDate(Timestamp date) {

		return timestampToLocalDateTime(date).toLocalDate();
	}

	/**
	 * 比较两个LocalDateTime是否同一天
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	public static boolean isTheSameDay(LocalDateTime begin, LocalDateTime end) {
		return begin.toLocalDate().equals(end.toLocalDate());
	}

	/**
	 * 比较两个时间LocalDateTime大小
	 *
	 * @param time1
	 * @param time2
	 * @return 1:第一个比第二个大；0：第一个与第二个相同；-1：第一个比第二个小
	 */
	public static int compareTwoTime(LocalDateTime time1, LocalDateTime time2) {

		if (time1.isAfter(time2)) {
			return 1;
		} else if (time1.isBefore(time2)) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 比较time1,time2两个时间相差的秒数，如果time1<=time2,返回0
	 *
	 * @param time1
	 * @param time2
	 */
	public static long getTwoTimeDiffSecond(Timestamp time1, Timestamp time2) {
		long diff = timestampToLocalDateTime(time1).toEpochSecond(ZoneOffset.UTC)
				- timestampToLocalDateTime(time2).toEpochSecond(ZoneOffset.UTC);
		if (diff > 0) {
			return diff;
		} else {
			return 0;
		}
	}

	/**
	 * 比较time1,time2两个时间相差的分钟数，如果time1<=time2,返回0
	 *
	 * @param time1
	 * @param time2
	 */
	public static long getTwoTimeDiffMin(Timestamp time1, Timestamp time2) {
		long diff = getTwoTimeDiffSecond(time1, time2) / 60;
		if (diff > 0) {
			return diff;
		} else {
			return 0;
		}
	}

	/**
	 * 比较time1,time2两个时间相差的小时数，如果time1<=time2,返回0
	 *
	 * @param time1
	 * @param time2
	 */
	public static long getTwoTimeDiffHour(Timestamp time1, Timestamp time2) {
		long diff = getTwoTimeDiffSecond(time1, time2) / 3600;
		if (diff > 0) {
			return diff;
		} else {
			return 0;
		}
	}

	/**
	 * 判断当前时间是否在时间范围内
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean isTimeInRange(Date startTime, Date endTime) throws Exception {
		LocalDateTime now = getCurrentLocalDateTime();
		LocalDateTime start = dateToLocalDateTime(startTime);
		LocalDateTime end = dateToLocalDateTime(endTime);
		return (start.isBefore(now) && end.isAfter(now)) || start.isEqual(now) || end.isEqual(now);
	}

	/**
	 * 根据特定格式格式化日期
	 *
	 * @param date   被格式化的日期
	 * @param format 格式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 日期格式化
	 *
	 * @param date 日期对象
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return date != null ? ISO_DATEFORMAT_HOLDER.get().format(date) : null;
	}


	/**
	 * 常用的日期格式
	 */
	private final static String[] PARSE_DATE_PATTERNS = { //
			ISO_DATETIME_FORMAT, //
			"yyyy-MM-dd HH:mm:ss", //
			"yyyy-MM-dd'T'HH:mm:ss.SSS", //
			"EEE MMM dd HH:mm:ss zzz yyyy", //
			"yyyy-MM-dd HH:mm:ss.SSS", //
			"yyyy-MM-dd HH:mm", //
			"yyyy-MM-dd HH", //
			"yyyy-MM-dd", //
			"yyyy-MM", //
			"d MMM yyyy h:m a", // 30 Jun 2017 2:40 PM
			"MMM d, yyyy HH:mm", //
			"MMM d, yyyy", //
			"MM/dd/yyyy", //
			"yyyyMMdd", //
			"yyyyMM", //
			"yyyy"//
	};

	// 获得符号字符位置
	private static int indexOfSignChars(String str, int startPos) {
		int idx = str.indexOf('+', startPos);
		if (idx != -1) {
			return idx;
		}
		return str.indexOf('-', startPos);
	}

	/**
	 * 解析日期字符串
	 *
	 * @param source 日期字符串
	 * @return 日期对象
	 */
	public static Date parse(String source) {
		if (source != null) {
			try {
				SimpleDateFormat parser = new SimpleDateFormat();
				parser.setLenient(true);
				ParsePosition pos = new ParsePosition(0);
				return Stream.of(PARSE_DATE_PATTERNS).map(pattern -> {
					// LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
					if (pattern.endsWith("ZZ")) {
						pattern = pattern.substring(0, pattern.length() - 1);
					}
					parser.applyPattern(pattern);
					pos.setIndex(0);
					String str = source;
					// LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
					if (pattern.endsWith("ZZ")) {
						int signIdx = indexOfSignChars(str, 0);
						while (signIdx != -1) {
							if (signIdx >= 0 //
									&& signIdx + 5 < str.length() //
									&& Character.isDigit(str.charAt(signIdx + 1)) //
									&& Character.isDigit(str.charAt(signIdx + 2)) //
									&& str.charAt(signIdx + 3) == ':' //
									&& Character.isDigit(str.charAt(signIdx + 4)) //
									&& Character.isDigit(str.charAt(signIdx + 5))) {
								str = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
							}
							signIdx = indexOfSignChars(str, ++signIdx);
						}
					}
					Date date = parser.parse(str, pos);
					if (date != null && pos.getIndex() == str.length()) {
						return date;
					}
					return null;
				}).filter(Objects::nonNull).findFirst().orElse(null);
			} catch (Exception e) {
				// Ignore
			}
		}
		return null;
	}


	/**
	 * 日期格式化类线程变量(保证线程安全)
	 */
	private static final ThreadLocal<DateFormat> ISO_DATEFORMAT_HOLDER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATETIME_FORMAT);
		}

		;
	};
	public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
	public static final String DATE_FORMAT_DAY_2 = "yyyy/MM/dd";
	public static final String TIME_FORMAT_SEC = "HH:mm:ss";
	public static final String DATE_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_MSEC = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_MSEC_T = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final String DATE_FORMAT_MSEC_T_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String DATE_FORMAT_DAY_SIMPLE = "y/M/d";

	/**
	 * 匹配yyyy-MM-dd
	 */
	private static final String DATE_REG = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
	/**
	 * 匹配yyyy/MM/dd
	 */
	private static final String DATE_REG_2 = "^[1-9]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])$";
	/**
	 * 匹配y/M/d
	 */
	private static final String DATE_REG_SIMPLE_2 = "^[1-9]\\d{3}/([1-9]|1[0-2])/([1-9]|[1-2][0-9]|3[0-1])$";
	/**
	 * 匹配HH:mm:ss
	 */
	private static final String TIME_SEC_REG = "^(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
	/**
	 * 匹配yyyy-MM-dd HH:mm:ss
	 */
	private static final String DATE_TIME_REG = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s"
			+ "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
	/**
	 * 匹配yyyy-MM-dd HH:mm:ss.SSS
	 */
	private static final String DATE_TIME_MSEC_REG = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s"
			+ "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d\\.\\d{3}$";
	/**
	 * 匹配yyyy-MM-dd'T'HH:mm:ss.SSS
	 */
	private static final String DATE_TIME_MSEC_T_REG = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])T"
			+ "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d\\.\\d{3}$";
	/**
	 * 匹配yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 */
	private static final String DATE_TIME_MSEC_T_Z_REG = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])T"
			+ "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d\\.\\d{3}Z$";

	/**
	 * <p>
	 * 将{@link Date}类型转换为指定格式的字符串
	 * </p>
	 * author : Crab2Died date : 2017年06月02日 15:32:04
	 *
	 * @param date   {@link Date}类型的时间
	 * @param format 指定格式化类型
	 * @return 返回格式化后的时间字符串
	 */
	public static String date2Str(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * <p>
	 * 将{@link Date}类型转换为默认为[yyyy-MM-dd HH:mm:ss]类型的字符串
	 * </p>
	 * author : Crab2Died date : 2017年06月02日 15:30:01
	 *
	 * @param date {@link Date}类型的时间
	 * @return 返回格式化后的时间字符串
	 */
	public static String date2Str(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SEC);
		return sdf.format(date);
	}

	/**
	 * <p>
	 * 根据给出的格式化类型将时间字符串转为{@link Date}类型
	 * </p>
	 * author : Crab2Died date : 2017年06月02日 15:27:22
	 *
	 * @param strDate 时间字符串
	 * @param format  格式化类型
	 * @return 返回{@link java.util.Date}类型
	 */
	public static Date str2Date(String strDate, String format) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new DateTimeException("[" + strDate + "] parse to [" + format + "] exception", e);
		}
		return date;
	}

	/**
	 * 判断两个日期是否是同一天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @author zhangyu
	 * @date 2018年1月4日 下午6:47:38
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

	/**
	 * 秒转换为指定格式的日期
	 *
	 * @param second
	 * @param patten
	 * @return
	 */
	public static String secondToDate(long second, String patten) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(second * 1000);// 转换为毫秒
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(patten);
		String dateString = format.format(date);
		return dateString;
	}

	/**
	 * zhaoshuang 根据一个日期获取几个月之后的时间
	 *
	 * @param date 日期
	 * @param num  几个月
	 * @return
	 */
	public static Date afterNumMonth(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}

	/**
	 * 计算两个日期之间的分钟数
	 *
	 * @param newDate
	 * @param createTime
	 * @return
	 */
	public static Integer getDatePoor(Date newDate, Date createTime) {
		return (int) (((newDate.getTime() - createTime.getTime()) / 1000) / 60);
	}

	public static Integer daysBetween2(Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endTime);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600);
		return Integer.parseInt(String.valueOf(between_days));
	}

	private static final long MILLIS_IN_A_SECOND = 1000;

	private static final long SECONDS_IN_A_MINUTE = 60;

	private static final long MINUTES_IN_AN_HOUR = 60;

	private static final long HOURS_IN_A_DAY = 24;

	private static final int DAYS_IN_A_WEEK = 7;

	private static final int MONTHS_IN_A_YEAR = 12;

	/**
	 * 统计两个日期之间包含的天数。包含date1，但不包含date2
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDayDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException(
					"date1 and date2 cannot be null!");
		}
		Date startDate = org.apache.commons.lang.time.DateUtils.truncate(
				date1, Calendar.DATE);
		Date endDate = org.apache.commons.lang.time.DateUtils.truncate(date2,
				Calendar.DATE);
		if (startDate.after(endDate)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}
		long millSecondsInOneDay = HOURS_IN_A_DAY * MINUTES_IN_AN_HOUR
				* SECONDS_IN_A_MINUTE * MILLIS_IN_A_SECOND;
		return (int) ((endDate.getTime() - startDate.getTime()) / millSecondsInOneDay);
	}

	/**
	 * 计算time2比time1晚多少分钟，忽略日期部分
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getMinuteDiffByTime(Date time1, Date time2) {
		long startMil = 0;
		long endMil = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		calendar.set(1900, 1, 1);
		startMil = calendar.getTimeInMillis();
		calendar.setTime(time2);
		calendar.set(1900, 1, 1);
		endMil = calendar.getTimeInMillis();
		return (int) ((endMil - startMil) / MILLIS_IN_A_SECOND / SECONDS_IN_A_MINUTE);
	}

	/**
	 * 计算指定日期的前一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getPrevDay(Date date) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, -1);
	}

	/**
	 * 计算指定日期的后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, 1);
	}

	/**
	 * 判断date1是否在date2之后，忽略时间部分
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateAfter(Date date1, Date date2) {
		Date theDate1 = org.apache.commons.lang.time.DateUtils.truncate(date1,
				Calendar.DATE);
		Date theDate2 = org.apache.commons.lang.time.DateUtils.truncate(date2,
				Calendar.DATE);
		return theDate1.after(theDate2);
	}

	/**
	 * 判断date1是否在date2之前，忽略时间部分
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateBefore(Date date1, Date date2) {
		return isDateAfter(date2, date1);
	}


	/**
	 * 判断time1是否在time2之后，忽略日期部分
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isTimeAfter(Date time1, Date time2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);
		calendar1.set(1900, 1, 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);
		calendar2.set(1900, 1, 1);
		return calendar1.after(calendar2);
	}

	/**
	 * 判断time1是否在time2之前，忽略日期部分
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isTimeBefore(Date time1, Date time2) {
		return isTimeAfter(time2, time1);
	}

	/**
	 * 判断两个日期是否同一天（忽略时间部分）
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 取得参数year指定的年份的总天数
	 *
	 * @param year
	 * @return
	 */
	public static int getDaysInYear(int year) {
		Calendar aDay = Calendar.getInstance();
		aDay.set(year, 1, 1);
		Date from = aDay.getTime();
		aDay.set(year + 1, 1, 1);
		Date to = aDay.getTime();
		return getDayDiff(from, to);
	}

	/**
	 * 取得指定年月的总天数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysInMonth(int year, int month) {
		Calendar aDay = Calendar.getInstance();
		aDay.set(year, month, 1);
		Date from = aDay.getTime();
		if (month == Calendar.DECEMBER) {
			aDay.set(year + 1, Calendar.JANUARY, 1);
		} else {
			aDay.set(year, month + 1, 1);
		}
		Date to = aDay.getTime();
		return getDayDiff(from, to);
	}

	/**
	 * 获得指定日期的年份
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getFieldValue(date, Calendar.YEAR);
	}

	private static int getFieldValue(Date date, int field) {
		if (date == null) {
			throw new InvalidParameterException("date cannot be null!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 获得指定日期的月份
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return getFieldValue(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获得指定日期是当年的第几天
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		return getFieldValue(date, Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得指定日期是当月的第几天
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		return getFieldValue(date, Calendar.DAY_OF_MONTH);
	}


	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date end = sdf.parse("2020-07-01 09:30:00");

//			int result = getMinuteDiffByTime(end,new Date()) ;
//			System.out.println(result);
			Date preDate = getPrevDay(end);
			String result = sdf.format(preDate);
			System.out.println(result);

			int tianshu = getDaysInYear(Integer.valueOf(2019));
			System.out.println(tianshu);

			int ytianshu = getDaysInMonth(Integer.valueOf(2020), Integer.valueOf(07));
			System.out.println(ytianshu);

			System.out.println(getYear(end));

			System.out.println(getDayOfMonth(end));

			System.out.println("=====" + format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}


}
