package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Common {
	
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
	
	//format에 해당되는 현재 시간 return
	public static String getTime(String format) {
		Calendar cal=Calendar.getInstance();
		Date nowTime=cal.getTime();
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);	
		return dateFormat.format(nowTime);
	}
}
