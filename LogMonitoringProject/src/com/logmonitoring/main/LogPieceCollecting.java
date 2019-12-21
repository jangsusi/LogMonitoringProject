package com.logmonitoring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.LogData;
import com.logmonitoring.model.LogDataPlusTime;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public class LogPieceCollecting extends LogCollecting {
	File[] fileList;
	LogFileProcessor logFile;
	TraceFileProcessor traceInfo;
	MapProcessor logMap;
	String time;
	int type;
	
	public LogPieceCollecting(int type) {
		super(type);
		this.type = type;
		new File(Util.TIME_FILE_DIR[type]).mkdirs();
		logFile = new LogFileProcessor();
		traceInfo = new TraceFileProcessor(Util.LOCAL_FILE_DIR + Util.TRACE_FILE_DIR[type]);
		logMap = new MapProcessor();
	}

	@Override
	public void startLogCollecting() {
		fileList = new File(Util.MONITORING_FILE_DIR[type]).listFiles();
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		long[] startInfo = findStartPlace();
		int startIndex = (int)startInfo[Util.FILE_NAME];
		long startPointer = startInfo[Util.FILE_POINTER];

		for (int i = startIndex; i < fileList.length; i++) {
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i], "r")) {
				String logLine = rdAccessFile.readLine();
				if (i == startIndex) {
					rdAccessFile.seek(startPointer);
					time = Util.getTime(logLine);
					if(startPointer != 0L) {
						logMap.putData(new File(Util.TIME_FILE_DIR[type] + time));
					}
				}
				do {
					LogData logData = new LogDataPlusTime(logLine);
					if (!logData.setLogData()) {
						continue;
					}	
					isTimeToWriteFile((LogDataPlusTime)logData, fileList[i].getName(), rdAccessFile);
					logMap.putData((LogDataPlusTime)logData);
				} while ((logLine = rdAccessFile.readLine()) != null);
			} catch (FileNotFoundException e) {
				System.out.println(fileList[i].getName() + "파일을 찾을 수 없음(저장실패)");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void isTimeToWriteFile(LogDataPlusTime logData, String fileName, RandomAccessFile rdAccessFile) {
		if (logData.isDifferentTime(time)) {
			logFile.writeLogFile(logMap, time);
			traceInfo.writeLastInfo(fileName, rdAccessFile);
			time = logData.getTime();					
			logMap.initialize();
		}	
	}


}
