package com.logmonitoring.main;

import java.io.File;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.FileDataPlusTime;
import com.logmonitoring.model.TimeCheckable;

public class LogBundleCollecting extends LogCollecting {

	public LogBundleCollecting(int type) {
		super(type);
	}

	@Override
	public void startLogCollecting() {
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		findStartPlace();
		if(startIndex == fileList.length) {
			return;
		}
		TimeCheckable file = new FileDataPlusTime(fileList[startIndex]);
		File lastFile = new File(Util.TIME_FILE_DIR[type] + file.getTime());
		if(lastFile.exists()) {
			logMap.putData(lastFile);
			time = file.getTime();
		}
		for (int i = startIndex; i < fileList.length; i++) {	
			traceData.setName(fileList[i].getName());
			file = new FileDataPlusTime(fileList[i]);
			isTimeToWriteFile(file, traceData);
			logMap.putData(fileList[i]);
		}
		logFile.writeLogFile(logMap, time);
		traceData.setPointer(fileList[fileList.length - 1].length());
		traceFile.writeLastInfo(traceData);
	}
	
	@Override
	public boolean updateLogCollecting() {
		fileList = new File(Util.MONITORING_FILE_DIR[type]).listFiles();
		File lastFile = fileList[fileList.length - 1];
		traceData = traceFile.getLastInfo();
		String lastTraceFileName = traceData.getName();
		if(type == Util.HOUR && lastTraceFileName.equals(lastFile.getName())) {
			return false;
		}
		String lastTime = new FileDataPlusTime(lastFile).getTime();
		logMap.initialize();
		for (int i = fileList.length - 1; i >= 0; i--) {
			TimeCheckable file = new FileDataPlusTime(fileList[i]);
			if (file.isDifferentTime(lastTime)) {
				break;
			}
			logMap.putData(fileList[i]);
		}
		logFile.writeLogFile(logMap, lastTime);
		traceData.setPointer(lastFile.length());
		traceData.setName(lastFile.getName());
		traceFile.writeLastInfo(traceData);
		if(type == Util.DAY) {
			logFile.deleteLogFile(Util.HOUR);
			logFile.deleteLogFile(Util.DAY);
		}
		return true;
	}
	

}
