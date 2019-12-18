package com.logmonitoring.common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
	public static final String SERVER_DIR="/opt/TerraceTims/log/catalina/webmail";
	public static final String ACCESS_FILE_DIR = "C:/Users/user/Desktop/access_log/";
	public static final String LOCAL_FILE_DIR = "C:/Users/user/Desktop/monitoring_data/";
	public static final String MINUTE_FILE_DIR = "C:/Users/user/Desktop/monitoring_data/minute/";
	public static final String HOUR_FILE_DIR = "C:/Users/user/Desktop/monitoring_data/hour/";
	public static final String DAY_FILE_DIR = "C:/Users/user/Desktop/monitoring_data/day/";
	public static final String url="172.22.1.66";
	public static final String id="jsb568";
	public static final String password="1234";
	public static final int FILE_POSITION = 0;
	public static final int FILE_POINTER = 1;
	public static final int MINUTE = 0;
	public static final int HOUR = 1;
	public static final int DAY = 2;
	public static final int MINUTE_FILE_NAME_LENGTH = 16;
	public static final int HOUR_FILE_NAME_LENGTH = 14;
	public static final String[] TIME_FILE_DIR = {MINUTE_FILE_DIR, HOUR_FILE_DIR, DAY_FILE_DIR};
	public static final String[] MONITORING_FILE_DIR = {ACCESS_FILE_DIR, MINUTE_FILE_DIR, HOUR_FILE_DIR, DAY_FILE_DIR};
	public static final String[] TRACE_FILE_DIR = {"minute_trace.txt", "hour_trace.txt", "day_trace.txt"};
	
	public static String ChangeMMFormat(String Month) {
		switch(Month) {
			case "Jan":
				return "01";
			case "Feb":
				return "02";
			case "Mar":
				return "03";
			case "Apr":
				return "04";
			case "May":
				return "05";
			case "Jun":
				return "06";
			case "Jul":
				return "07";
			case "Aug":
				return "08";
			case "Sep":
				return "09";
			case "Oct":
				return "10";
			case "Nov":
				return "11";
			case "Dec":
				return "12";				
		}
		return null;
	}
	
	public static String getTime(String line) {
		String[] timeFactors = line.split(" ")[3].split("/|:");
		StringBuilder strBuilder = new StringBuilder();
		String month = ChangeMMFormat(timeFactors[1]);
		strBuilder.append(timeFactors[2]).append(month).append(timeFactors[0].substring(1))
			.append(timeFactors[3]).append(timeFactors[4]).append(".txt");
		return strBuilder.toString();
	}
	
	public static String getTime(File file) {
		if(file.getName().length() == MINUTE_FILE_NAME_LENGTH) {
			return file.getName().substring(0, 10) + ".txt";
		} else {
			String fileName = file.getName(); 
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("stat_").append(fileName.substring(0,4)).append("-").append(fileName.substring(4,6))
				.append("-").append(fileName.substring(6,8)).append(".txt");
			return strBuilder.toString();
		}
	}

	public static String getDeleteTimeList(int type) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = null;
		if(type == HOUR) {
			df = new SimpleDateFormat("yyyyMMddHH");
			cal.add(Calendar.HOUR, -24);
		} else if(type == MINUTE) {
			df = new SimpleDateFormat("yyyyMMddHHmm");
			cal.add(Calendar.MINUTE, -60);
		}
		return (df.format(cal.getTime()) + ".txt");
	}

}
