package com.logmonitoring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.LogData;
import com.logmonitoring.model.LogDataPlusTime;
import com.logmonitoring.model.TimeCheckable;

public class LogPieceCollecting extends LogCollecting {

	public LogPieceCollecting(int type) {
		super(type);
	}

	@Override
	public void startLogCollecting() {
		System.out.println(Util.TIME_FILE_DIR[type] + "만들기 시작");
		findStartPlace();
		if(startIndex == fileList.length) {
			return;
		}
		for (int i = startIndex; i < fileList.length; i++) {
			traceData.setName(fileList[i].getName());
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i], "r")) {
				if(i == startIndex) {
					rdAccessFile.seek(startPointer);
				}
				String logLine = null;
				while ((logLine = rdAccessFile.readLine()) != null) {
					LogData logData = new LogDataPlusTime(logLine);
					if (!logData.setLogData()) {
						continue;
					}
					isTimeToWriteFile((TimeCheckable) logData, traceData);
					traceData.setPointer(rdAccessFile.getFilePointer());
					logMap.putData(logData);
				}
			} catch (FileNotFoundException e) {
				System.out.println(fileList[i].getName() + "파일을 찾을 수 없음(저장실패)");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean updateLogCollecting() {
		System.out.println(Util.TIME_FILE_DIR[type] + "업데이트 시작");
		ServerFileStorage serverStorage = new ServerFileStorage();
		serverStorage.downloadFileToLocal();
		File[] updatedAccessLogFiles = new File(Util.ACCESS_FILE_DIR).listFiles();

		traceData = traceFile.getLastInfo();
		String lastFileName = traceData.getName();
		long pointer = traceData.getPointer();
		
		String updatedLastFileName = updatedAccessLogFiles[updatedAccessLogFiles.length - 1].getName();
		if(!updatedLastFileName.equals(lastFileName)) {
			pointer = 0L;
		} else if (updatedLastFileName.length() == pointer) {
			return false;
		}				
		traceData.setName(updatedLastFileName);
		startLogCollecting();
		return true;
	}

}