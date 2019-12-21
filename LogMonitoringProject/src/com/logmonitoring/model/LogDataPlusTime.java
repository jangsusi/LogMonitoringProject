package com.logmonitoring.model;

import com.logmonitoring.common.Util;

public class LogDataPlusTime extends LogData {
	String time;
	
	public LogDataPlusTime(String line) {
		super(line);
		time = Util.getTime(line);
	}
	
	public String getTime() {
		return time;
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
		}
		return true;
	}
	
	private boolean isNotSupportedFormat() {
		if(!logFactors[1].equals("-") && !logFactors[2].contains("@")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDifferentTime(String fileName) {
		if(!this.time.equals(fileName)) {
			return true;
		} else {
			return false;
		}
	}

}
