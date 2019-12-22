package com.logmonitoring.main;

import java.io.File;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.TimeCheckable;
import com.logmonitoring.model.TraceData;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public abstract class LogCollecting {
	File[] fileList;
	LogFileProcessor logFile;
	TraceFileProcessor traceFile;
	TraceData traceData;
	MapProcessor logMap;
	String time;
	int type;
	int startIndex;
	long startPointer;

	abstract public void startLogCollecting();
	abstract public boolean updateLogCollecting();
	
	LogCollecting(int type) {
		new File(Util.TIME_FILE_DIR[type]).mkdirs();
		this.type = type;
		logFile = new LogFileProcessor();
		traceFile = new TraceFileProcessor(Util.TRACE_FILE_DIR[type]);
		traceData = new TraceData();
		logMap = new MapProcessor();
		time = null;
		startIndex = 0;
		startPointer = 0L;
	}

	protected void findStartPlace() {
		fileList = new File(Util.MONITORING_FILE_DIR[type]).listFiles();
		if (traceFile.size() != 0) {
			traceData = traceFile.getLastInfo();
			startIndex = Util.getFileIndex(fileList, traceData.getName());
			startPointer = traceData.getPointer();			
		}
		if(startPointer == fileList[startIndex].length()) {
			startIndex ++;
			startPointer = 0L;
		}
	}

	protected void isTimeToWriteFile(TimeCheckable timeInfo, TraceData traceData) {
		if (time == null) {
			time = timeInfo.getTime();
			return;
		}		
		if (timeInfo.isDifferentTime(time)){
			logFile.writeLogFile(logMap, time);
			traceFile.writeLastInfo(traceData);
			time = timeInfo.getTime();
			logMap.initialize();
		} else if(type == Util.HOUR || type == Util.DAY) {
			int index = Util.getFileIndex(fileList, traceData.getName());
			if(index % 5 == 0) {
				logFile.writeLogFile(logMap, time);
				traceFile.writeLastInfo(traceData);
			}
		}
	}

}