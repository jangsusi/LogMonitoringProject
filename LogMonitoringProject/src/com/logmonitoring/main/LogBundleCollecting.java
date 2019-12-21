package com.logmonitoring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.FileData;
import com.logmonitoring.model.LogDataPlusTime;
import com.logmonitoring.model.TimeCheckable;
import com.logmonitoring.model.TraceData;

public class LogBundleCollecting extends LogCollecting {
		
	public LogBundleCollecting(int type) {
		super(type);
	}
	
	@Override
	public void startLogCollecting() {
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		findStartPlace();
		for (int i = startIndex; i < fileList.length; i++) {
			traceData.setName(fileList[i].getName());
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i],"r")){
				TimeCheckable file = new FileData(fileList[i]);			
				isTimeToWriteFile(file, traceData);
				logMap.putData(fileList[i]);
				if(i == fileList.length - 1) {
					logFile.writeLogFile(logMap, time);
					traceFile.writeLastInfo(traceData);
				}				
			} catch (FileNotFoundException e) {
				System.out.println(fileList[i].getName() + "파일을 찾을 수 없음(저장실패)");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

}
