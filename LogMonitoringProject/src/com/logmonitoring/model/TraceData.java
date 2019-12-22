package com.logmonitoring.model;

import com.logmonitoring.common.Util;

public class TraceData {
	String fileName = null;
	long pointer = 0L;
	
	public TraceData(String fileInfo) {
		fileName = fileInfo.split(" ")[Util.FILE_NAME];
		pointer = Long.parseLong(fileInfo.split(" ")[Util.FILE_POINTER]);
	}
	
	public TraceData() {}
	
	public String getData() {
		return fileName + " " + pointer;
	}
	
	public String getName() {
		return fileName;
	}
	
	public long getPointer() {
		return pointer;
	}
	
	public void setName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setPointer(long pointer) {
		this.pointer = pointer;
	}
}