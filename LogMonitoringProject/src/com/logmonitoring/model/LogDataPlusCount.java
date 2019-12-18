package com.logmonitoring.model;

public class LogDataPlusCount extends LogData {
	int count;

	public LogDataPlusCount(String line) {
		super(line);
	}

	@Override
	public boolean setLogData() {	
		count = Integer.parseInt(logFactors[0]);
		IP = logFactors[1];
		email = logFactors[2];
		method = logFactors[3];
		url = logFactors[4];			
		return true;
	}

	public int getCount() {
		return count;
	}

}
