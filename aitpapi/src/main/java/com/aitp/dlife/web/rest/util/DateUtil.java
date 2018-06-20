package com.aitp.dlife.web.rest.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jianfei.yin
 * @create 2018-06-20 12:48 AM
 **/
public class DateUtil {
	private static String Y_M_D = "yyyy-MM-dd HH:mm:ss";

	public static String getYMDDateString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
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
}
