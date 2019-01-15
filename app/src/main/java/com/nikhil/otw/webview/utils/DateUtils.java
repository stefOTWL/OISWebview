package com.nikhil.otw.webview.utils;
// Created by kamlesh on 11/8/16, 5:00 PM 


import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static String formatDateToLocal(String reqdDatePattern, String serverDatePattern, String serverDateString) throws ParseException {
		SimpleDateFormat reqdDateFormat = new SimpleDateFormat(reqdDatePattern);
		SimpleDateFormat serverDateFormat = new SimpleDateFormat(serverDatePattern);
		// server sends Time in GMT
		serverDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		// convert to local time
		reqdDateFormat.setTimeZone(TimeZone.getDefault());
		Date date = serverDateFormat.parse(serverDateString);
		String sDate = reqdDateFormat.format(date);
		return sDate;
	}

	// This method is used for Creating Challenges to send Start Date and ViewPagerEnd Date to Server in GMT format.
	public static String formatDateToGMT(String localDateString) throws ParseException {
		SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		serverDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = serverDateFormat.parse(localDateString);
		return serverDateFormat.format(date);
	}

	public static String formatDateToGMT(String reqdDatePattern, Date date) throws ParseException {
		SimpleDateFormat reqdDateFormat = new SimpleDateFormat(reqdDatePattern);
		reqdDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return reqdDateFormat.format(date);
	}

	// we don't set any TimeZone as it will be taken by default.
	public static String formatDate(String reqdDatePattern, Date date) throws ParseException {
		SimpleDateFormat reqdDateFormat = new SimpleDateFormat(reqdDatePattern);
		return reqdDateFormat.format(date);
	}

	// Conversion of Date format without changing time zone.
	public static String formatDate(String reqdDatePattern, String serverDatePattern, String serverDateString) throws ParseException {
		SimpleDateFormat reqdDateFormat = new SimpleDateFormat(reqdDatePattern);
		SimpleDateFormat serverDateFormat = new SimpleDateFormat(serverDatePattern);
		Date date = serverDateFormat.parse(serverDateString);
		String sDate = reqdDateFormat.format(date);
		return sDate;
	}

	// converts local Date to GMT format.
	public static String toGMT(Date date) {
		SimpleDateFormat format = new SimpleDateFormat();
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(date);
	}

	public static String getExpiredDate(int duration)
	{
		Calendar currentCal = Calendar.getInstance();
		currentCal.add(Calendar.MONTH,duration);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(currentCal.getTime());


	}

	// converts GMT Date to local date.
	public static String fromGMT(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm a");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			return format.parse(dateString).toString();
		} catch (ParseException e) {
			throw new RuntimeException("Date parsing error");
		}
	}

	/* returns current date in GMT in 'yyyy/MM/dd' format */
	@NonNull
	public static String getCurrentDate() {
		Calendar currentCal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(currentCal.getTime());
	}
}
