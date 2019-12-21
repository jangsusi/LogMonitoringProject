package com.logmonitoring.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public abstract class LogCollecting {
	File[] fileList;
	LogFileProcessor logFile;
	TraceFileProcessor traceFile;
	MapProcessor logMap;
	String time;
	int type;

	abstract public void startLogCollecting();

	LogCollecting(int type) {
		this.type = type;
		new File(Util.TIME_FILE_DIR[type]).mkdirs();
		logFile = new LogFileProcessor();
		traceFile = new TraceFileProcessor(Util.LOCAL_FILE_DIR + Util.TRACE_FILE_DIR[type]);
		logMap = new MapProcessor();
		
	}
	
//	protected String initializeField(long startPointer, RandomAccessFile rdAccessFile) throws IOException {
//		rdAccessFile.seek(startPointer);
//		String logLine = rdAccessFile.readLine();
//		if (startPointer != 0L) {
//			logMap.putData(new File(Util.TIME_FILE_DIR[type] + fileName));
//		}
//		return logLine;
//	}
	
	//trace지점 찾기 
	protected long[] findStartPlace() {
		String startFileName = null;
		long startIndex = 0L;
		long startPointer = 0L;
		if (traceFile.size() != 0) {
			String[] startInfo = traceFile.getLastInfo().split(" ");
			startPointer = Long.parseLong(startInfo[Util.FILE_POINTER]);
			startIndex = Util.getFileIndex(fileList, startInfo[Util.FILE_NAME]);
			if (startPointer == fileList[(int) startIndex].length()) {
				startIndex++;
				startPointer = 0L;
			}
		}
		
		long[] startInfo = {startIndex, startPointer};
		return startInfo;
	}
	
	protected void updateLogCollecting(File lastFile, int type) {
		 String fileName = Util.getTime(lastFile);
		 File[] fileList = new File(Util.TIME_FILE_DIR[type]).listFiles();
		 for(int i = fileList.length - 1; i>=0; i--) {
			 if(!fileName.equals(Util.getTime(fileList[i]))) {
				 break;
			 }
			 logMap.putData(fileList[i]);
			 logFile.writeLogFile(logMap, lastFile.getName());
		 }	
	}

}
