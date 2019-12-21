package com.logmonitoring.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.LogDataPlusCount;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public class LogBundleCollecting extends LogCollecting {
		
	public LogBundleCollecting(int type) {
		super(type);
	}
	
	@Override
	public void startLogCollecting() {
		fileList = new File(Util.MONITORING_FILE_DIR[type]).listFiles();
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		long[] startInfo = findStartPlace();
		int startIndex = (int)startInfo[Util.FILE_NAME];
		for(int i = startIndex; i<fileList.length; i++) {
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i],"r")){
				String logLine = null;
				if (i == startIndex) {
					logLine = rdAccessFile.readLine();
					time = Util.getTime(fileList[(int) startIndex]);
				} else {
					logLine = rdAccessFile.readLine();
				}
				isTimeToWriteFile(i, rdAccessFile);
				logMap.putData(fileList[i]);
				if(i == fileList.length - 1) {
					logFile.writeLogFile(logMap, time);
					traceFile.writeLastInfo(fileList[i].getName(), rdAccessFile);
//					traceFile.writeLastInfo(fileList[i].getName());
				}				
			} catch (FileNotFoundException e) {
				System.out.println(fileList[i].getName() + "파일을 찾을 수 없음(저장실패)");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void isTimeToWriteFile(int index, RandomAccessFile rdAccessFile) {
		if(!time.equals(Util.getTime(fileList[index])) || index % (type == Util.HOUR ? 5 : 2) == 0){
			logFile.writeLogFile(logMap, time);
			traceFile.writeLastInfo(fileList[index].getName(), rdAccessFile);
//			traceFile.writeLastInfo(fileList[index].getName());
			if(!time.equals(Util.getTime(fileList[index]))){		
				logMap.initialize();
			}
			time = Util.getTime(fileList[index]);
		}
	}

}
