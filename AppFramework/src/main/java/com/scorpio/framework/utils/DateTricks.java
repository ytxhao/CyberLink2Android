package com.scorpio.framework.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期格式工具类
 *
 *
 */
public class DateTricks {

	private static final String TAG = DateTricks.class.getSimpleName();
	/**
	 * 
	 * xmpp延时消息用时间
	 */
	private static final String XMPP_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	/**
	 * 
	 * gmt标准时间
	 */
	private static final String RFC1123_DATE_FORMAT_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
	
	private static final String SIMPLE_TIME = "dd_MM_yyyy_HH_mm_ss";


	public static Date paseDelayTime(String date){
		SimpleDateFormat fmt = new SimpleDateFormat(XMPP_DATETIME_FORMAT);
		ParsePosition pos = new ParsePosition(0);
		return fmt.parse(date, pos);
	}
	
	public static String getDelayTime(long date){
		SimpleDateFormat df = new SimpleDateFormat(XMPP_DATETIME_FORMAT);
		return df.format(new Date(date));
	}
	
	public static Date paseGMTTime(String date){
		SimpleDateFormat fmt = new SimpleDateFormat(RFC1123_DATE_FORMAT_PATTERN, Locale.US);
		ParsePosition pos = new ParsePosition(0);
		return fmt.parse(date, pos);
	}
	
	public static String getGMTTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat(RFC1123_DATE_FORMAT_PATTERN, Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		String time = df.format(date);
		if(!time.endsWith("GMT")){
			time = time.substring(0, time.length()-6);
		}		
		return time;
	}
	
	
	public static boolean isDateToday(long timeInMillis) {
		Calendar d = Calendar.getInstance();
		d.setTimeInMillis(timeInMillis);
		Calendar today = Calendar.getInstance();
		
		return d.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) && d.get(Calendar.MONTH) == today.get(Calendar.MONTH) && d.get(Calendar.YEAR) == today.get(Calendar.YEAR);
		
	}
	
	/**
	 * 现在是否过了指定时刻几个小时？
	 * 
	 * @param timeInMillis
	 * @param houses
	 * @return
	 */
	public static boolean isDateGoByHouse(long timeInMillis,int houses) {
		Date d = new Date();
		long now = d.getTime();
		boolean r = false;
		long sevenDaysMillis = 1000*60*60*houses;
		if((now-timeInMillis)>=sevenDaysMillis){
			r = true;
		}else{
			if((now-timeInMillis)<=0){
				ScoLog.E(TAG, "now is older than timeInMillis,^_^");
				r = false;
			}else{
				r = false;
			}
		}		
		return r;
	}
	
	public static boolean isDateThisYear(long timeInMillis) {
		Calendar d = Calendar.getInstance();
		d.setTimeInMillis(timeInMillis);
		Calendar today = Calendar.getInstance();
		
		return d.get(Calendar.YEAR) == today.get(Calendar.YEAR);
	}
	
	public static boolean isDateNearSevenDays(long timeInMillis){
		Date d = new Date();
		long now = d.getTime();
		boolean r = false;
		long sevenDaysMillis = 1000*60*60*24*7;
		if((now-timeInMillis)>=sevenDaysMillis){
			r = false;
		}else{
			if((now-timeInMillis)<=0){
				ScoLog.E(TAG,  "now is older than timeInMillis,^_^");
				r = false;
			}else{
				r = true;
			}
		}		
		return r;
	}
	
	public static String getSimpleTime(long date){
		SimpleDateFormat df = new SimpleDateFormat(SIMPLE_TIME);
		return df.format(new Date(date));
	}

}
