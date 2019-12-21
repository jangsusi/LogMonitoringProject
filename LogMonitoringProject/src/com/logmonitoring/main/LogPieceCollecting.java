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
		for (int i = startIndex; i < fileList.length; i++) {
			traceData.setName(fileList[i].getName());
			try (RandomAccessFile rdAccessFile = new RandomAccessFile(fileList[i], "r")) {
				rdAccessFile.seek(startPointer);
				startPointer = 0;
				String logLine = null;
				while ((logLine = rdAccessFile.readLine()) != null) {
					traceData.setPointer(rdAccessFile.getFilePointer());
					LogData logData = new LogDataPlusTime(logLine);
					if (!logData.setLogData()) {
						continue;
					}
					isTimeToWriteFile((TimeCheckable) logData, traceData);
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

}
