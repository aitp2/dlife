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
    private static String YY_MM_DD = "yyyy-MM-dd";

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

	public static boolean isThisWeek(String dateStr){
		Date date = fromYDMStringDate(dateStr);
		if(null == date){
			return false;
		}
		return date.getTime() > getThisWeekMonday().getTime();

	}


}
