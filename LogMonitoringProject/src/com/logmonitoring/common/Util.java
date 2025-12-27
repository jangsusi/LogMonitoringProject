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
	public static final int MINUTE = 0;
	public static final int HOUR = 1;
	public static final int DAY = 2;
	public static final String[] TIME_FILE_DIR = {MINUTE_FILE_DIR, HOUR_FILE_DIR, DAY_FILE_DIR};
	public static final String[] MONITORING_FILE_DIR = {ACCESS_FILE_DIR, MINUTE_FILE_DIR, HOUR_FILE_DIR, DAY_FILE_DIR};
	public static final String[] TRACE_FILE_DIR = {LOCAL_FILE_DIR + "minute_trace.txt",LOCAL_FILE_DIR + "hour_trace.txt",LOCAL_FILE_DIR + "day_trace.txt"};

	

	public static String changeMMFormat(String Month) {
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
			default :
				return null;
		}
	}

	public static String getDeleteFileList(int type) {
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
	
	public static int getFileIndex(File[] fileList, String fileName) {
		int index = 0;	
		for(int i = 0; i <fileList.length; i++) {
			if(fileList[i].getName().equals(fileName)) {
				break;
			}
			index++;
		}
		return (index == fileList.length ? 0 : index);
	}

}
