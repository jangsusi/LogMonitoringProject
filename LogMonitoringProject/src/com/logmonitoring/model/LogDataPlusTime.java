package com.logmonitoring.model;

import com.logmonitoring.common.Util;

public class LogDataPlusTime extends LogData implements TimeCheckable {
	String time;
	
	public LogDataPlusTime(String line) {
		super(line);
		time = setTime(line);
	}
	
	private boolean isNotSupportedFormat() {
		if(!logFactors[1].equals("-") && !logFactors[2].contains("@")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setLogData() {
		if(isNotSupportedFormat()) {
			return false;
		} else {
			ip = logFactors[0];
			email = logFactors[2];
			method = logFactors[5];
			url = logFactors[6];
			count = 1;
		}
		return true;
	}
	
	@Override
	public String getTime() {
		return time;
	}	
	
	@Override
	public boolean isDifferentTime(String time) {
		return (!this.time.equals(time) ? true : false);
	}

	@Override
	public String setTime(String rawTime) {
		String[] timeFactors = rawTime.split(" ")[3].split("/|:");
		StringBuilder strBuilder = new StringBuilder();
		String month = Util.changeMMFormat(timeFactors[1]);
		strBuilder.append(timeFactors[2]).append(month).append(timeFactors[0].substring(1))
			.append(timeFactors[3]).append(timeFactors[4]).append(".txt");
		return strBuilder.toString();
	}

}