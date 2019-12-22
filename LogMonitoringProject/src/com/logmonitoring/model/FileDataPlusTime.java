package com.logmonitoring.model;

import java.io.File;

import com.logmonitoring.common.Util;

public class FileDataPlusTime implements TimeCheckable {
	String time;
	File file;
	
	public FileDataPlusTime(File file) {
		this.file = file;
		this.time = setTime(file.getName());
	}

	public File getFile() {
		return file;
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
		if(file.getName().length() == Util.MINUTE_FILE_NAME_LENGTH) {
			return file.getName().substring(0, 10) + ".txt";
		} else {
			String fileName = file.getName(); 
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("stat_").append(fileName.substring(0,4)).append("-").append(fileName.substring(4,6))
				.append("-").append(fileName.substring(6,8)).append(".txt");
			return strBuilder.toString();
		}
	}


}