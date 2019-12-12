package main;

import java.util.LinkedHashMap;

public class MemberVary {
	String fileNameRawData;
	
	
	String minuteFileName;
	String hourFileName;
	
	String fileNameFactor;
	TimeMap map;
	
	public String getFileNameFactor() {
		return fileNameFactor;
	}
	public MemberVary(String fileNameRawData,TimeMap map) {
		this.fileNameRawData=fileNameRawData;
		this.map=map;
		this.minuteFileName=fileNameRawData.split("/|:")[4];
		this.hourFileName=fileNameRawData.split("/|:")[3];
	}
	public void setFileNameFactor(String fileNameFactor) {
		this.fileNameFactor = fileNameFactor;
	}

	
	LinkedHashMap<String,Integer> minuteMap;
	LinkedHashMap<String,Integer> hourMap;
	LinkedHashMap<String,Integer> dayMap;
	MemberVary(String minuteFileName,String hourFileName,String fileNameRawData){
		this.minuteFileName=minuteFileName;
		this.hourFileName=hourFileName;
		this.fileNameRawData=fileNameRawData;
		
	}
	
	public void setMinuteFileName(String minuteFileName) {
		this.minuteFileName=minuteFileName;
	}
	public void setHourFileName(String hourFileName) {
		this.hourFileName=hourFileName;
	}
	public void setFileNameRawData(String fileNameRawData) {
		this.fileNameRawData=fileNameRawData;
	}
	public String getMinuteFileName() {
		return this.minuteFileName;
	}
	public String getHourFileName() {
		return this.hourFileName;
	}
	public String getFileNameRawData() {
		return this.fileNameRawData;
	}
	
	public void update(String fileNameRawData) {
		map.clear();
		this.time=fileNameRawData.split("/|:")[4];
	}
}
