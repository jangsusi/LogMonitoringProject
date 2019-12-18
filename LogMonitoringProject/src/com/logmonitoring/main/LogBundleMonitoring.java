package com.logmonitoring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public class LogBundleMonitoring implements LogMonitoring {
	File[] fileList;
	LogFileProcessor logFile;
	TraceFileProcessor traceInfo;
	MapProcessor logMap;
	String time;
	int type;
		
	public LogBundleMonitoring(int type) {
		this.type = type;
		new File(Util.TIME_FILE_DIR[type]).mkdirs();
		logFile = new LogFileProcessor();
		traceInfo = new TraceFileProcessor(Util.LOCAL_FILE_DIR + Util.TRACE_FILE_DIR[type]);
		logMap = new MapProcessor();
	}
	
	@Override
	public void startMonitoring() {
		fileList = new File(Util.MONITORING_FILE_DIR[type]).listFiles();
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		int startPosition = 0;
		long startPointer = 0L;
		if(traceInfo.size() != 0) {
			String[] startInfo=traceInfo.getLastInfo().split(" ");
			startPosition = Integer.parseInt(startInfo[Util.FILE_POSITION]);
			startPointer = Long.parseLong(startInfo[Util.FILE_POINTER]);
			if(startPointer == fileList[startPosition].length()){
				startPosition++;
				startPointer = 0L;
			}
		}
		for(int i = startPosition; i<fileList.length; i++) {
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i],"r")){
				if(i == startPosition) {
					rdAccessFile.seek(startPointer);
					time = Util.getTime(fileList[startPosition]);
					if(startPointer != 0L) {
						logMap.putData(new File(Util.TIME_FILE_DIR[type] + time));
					}
				}
				if(!time.equals(Util.getTime(fileList[i])) || i % (type == Util.HOUR ? 5 : 2) == 0){
					logFile.writeLogFile(logMap, time);
					if(!time.equals(Util.getTime(fileList[i]))){				
						logMap = new MapProcessor();
					}
					time = Util.getTime(fileList[i]);
					traceInfo.writeLastPlace(i, rdAccessFile);
				}
				logMap.putData(fileList[i]);
				logFile.writeLogFile(logMap, time);
				traceInfo.writeLastPlace(i, rdAccessFile);
			} catch (FileNotFoundException e) {
				System.out.println(fileList[i].getName() + "파일을 찾을 수 없음(저장실패)");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
