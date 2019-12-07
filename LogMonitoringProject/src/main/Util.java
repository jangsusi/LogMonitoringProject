package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

//Nov -> 11
public class Util {
	
	enum DayNum{}
	
	
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
	static int i=0;
	static boolean isGo=true;
	public static void PutLogData(LinkedHashMap<String,Integer> map,String logData) {
		//map에 put했을 때 count가 0이면 새것
		//아니면 기존의것
		
		
		if(map.get(logData)==null) {
			map.put(logData, 1);
		
			//System.out.println("x"+map.get(logData)+" vs "+logData);
		}
		else {
			map.put(logData, map.get(logData)+1);
			//System.out.println("o"+map.get(logData)+" vs "+logData);
		}
	}
	
	
	public static String getFileName(String data,int type) {
		String[] arr=data.split("/|:");
		StringBuilder fileName=new StringBuilder();
		arr[1]=ChangeMMFormat(arr[1]);
		arr[0]=arr[0].substring(1); 
		
		fileName.append("C:/Users/user/Desktop/monitoring_data/");
		
		switch(type) {
		//yyyyMMddHHmm
		case 1:
			return fileName.append("minute/").append(arr[2]).append(arr[1])
			.append(arr[0]).append(arr[3])
			.append(arr[4]).append(".txt").toString();
		//yyyyMMddHH
		case 2:
			return fileName.append("hour/").append(arr[2]).append(arr[1])
			.append(arr[0]).append(arr[3])
			.append(".txt").toString();
		//yyyyMMdd stat_yyyy-MM-dd.txt
		case 3:
			return fileName.append("day/").append("stat_").append(arr[2]).append("-")
					.append(arr[1]).append("-")
					.append(arr[0]).append(".txt").toString();
		}
		return null;
	}

	
	public static String getFileCotext(LinkedHashMap<String,Integer> map) {
		ArrayList<Map.Entry<String,Integer>> descList=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(descList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
					    return e2.getValue().compareTo(e1.getValue());
				}
		});
		
		
		StringBuilder totalLogList=new StringBuilder();
		for(Entry<String, Integer> logData : descList) {
			
			totalLogList.append(logData.getValue()).append(" ")
			.append(logData.getKey()).append("\n");
		}
		return totalLogList.toString();
	}
}
