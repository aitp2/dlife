package com.aitp.dlife.web.rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jianfei.yin
 * @create 2018-06-20 12:48 AM
 **/
public class DateUtil {
	private static String Y_M_D = "yyyy-MM-dd HH:mm:ss";
	public static String YY_MM_DD = "yyyy-MM-dd";
	public static String YY_M_D_HH_mm = "yy.M.d HH:mm";

	public static String getDateString(String format, Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getYMDDateString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
		return sdf.format(date);
	}

	public static String getYYMMDDDateString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(YY_MM_DD);
		return sdf.format(date);
	}

	public static Date fromYDMStringDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * String -> Date
	 * 
	 * @param fromat
	 * @param date
	 * @return
	 */
	public static Date fromStringDate(String fromat, String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fromat);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isToday(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isYesterday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (fmt.format(date).toString().equals(fmt.format(cal.getTime()).toString())) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getThisWeekMonday() {
		Calendar cal = Calendar.getInstance();
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static boolean isThisWeek(String dateStr) {
		Date date = fromYDMStringDate(dateStr);
		if (null == date) {
			return false;
		}
		return date.getTime() > getThisWeekMonday().getTime();

	}

	/**
	 * date2比date1多的天数
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
		if (year1 != year2) {
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
					timeDistance += 366;
				} else {
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else {
			return day2 - day1;
		}
	}

}
