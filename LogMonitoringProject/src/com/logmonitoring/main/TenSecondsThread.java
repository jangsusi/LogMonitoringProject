package com.logmonitoring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.LogData;
import com.logmonitoring.model.LogDataPlusTime;
import com.logmonitoring.service.LogFileProcessor;
import com.logmonitoring.service.MapProcessor;
import com.logmonitoring.service.TraceFileProcessor;

public class TenSecondsThread extends Thread {
	public void run() {
		//����������� �� ��,�� ���� ������ �����ƴ��� Ȯ���ϱ�
		
		
		File[] fileList = new File(Util.TIME_FILE_DIR[Util.MINUTE]).listFiles();
		File lastMinutefile = fileList[fileList.length - 1];
//		int minuteCheck = Integer.parseInt(
//				lastMinutefile.getName().substring(Util.MINUTE_FRONT_POSITION, Util.MINUTE_LAST_POSITION)) + 1;
		int minuteCheck = 59;
		DateFormat df = new SimpleDateFormat("mm");
		Calendar cal = Calendar.getInstance();
		TraceFileProcessor traceFile = new TraceFileProcessor(Util.TRACE_FILE_DIR[Util.MINUTE]);
		long pointer = Long.parseLong(traceFile.getLastInfo().split(" ")[Util.FILE_POINTER]);
		String accessLogFileName = traceFile.getLastInfo().split(" ")[Util.FILE_NAME];
		//������ �� ���� ��������
		if(minuteCheck == 60) {
			minuteCheck = 0;
			pointer = 0L;
		}
		while (true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			cal.setTime(new Date());
			int nowTime = Integer.parseInt(df.format(cal.getTime()));
			
			if (nowTime != minuteCheck) {
				minuteCheck = nowTime;
				
				ServerFileStorage serverStorage = new ServerFileStorage();
				serverStorage.downloadFileToLocal();
				
				File accessLogFile = new File(accessLogFileName);
				if(accessLogFile.length() == pointer) {
					continue;
				}
				//���ο� �� ���� ���ŵ��� ��
				if(pointer == 0L) {
					File[] accessLogFileList = new File(Util.ACCESS_FILE_DIR).listFiles();
					accessLogFile = accessLogFileList[accessLogFileList.length - 1];
				}
				//LogPieceCollecting�̶� ���ϱ�2
				try (RandomAccessFile rdAccessFile = new RandomAccessFile(accessLogFile, "r")) {
					//initializeField
					rdAccessFile.seek(pointer);
					String logLine = rdAccessFile.readLine();
					String time = Util.getTime(logLine);
					LogFileProcessor logFile = new LogFileProcessor();
					MapProcessor logMap = new MapProcessor();
					//Piece
					do {
						LogData logData = new LogDataPlusTime(logLine);
						if (!logData.setLogData()) {
							continue;
						}
						
						//isTimeToWriteFile
						if (((LogDataPlusTime) logData).isDifferentTime(time)) {
							logFile.writeLogFile(logMap, time);
							traceFile.writeLastInfo(time, rdAccessFile);
							time = ((LogDataPlusTime) logData).getTime();
							logMap.initialize();
							break;
						}
						logMap.putData((LogDataPlusTime)logData);
					} while ((logLine = rdAccessFile.readLine()) != null);
					
					
					//hourUpdate
					String hourFileName = Util.getTime(lastMinutefile);
					for (int i = fileList.length - 1; i >= 0; i--) {
						if (!hourFileName.equals(Util.getTime(fileList[i]))) {
							break;
						}
						logMap.putData(fileList[i]);	
					}
					
					logFile.writeLogFile(logMap, hourFileName);
					traceFile.writeLastInfo(time, rdAccessFile);
					//�� ������ ��� �ٽ� traceInfo�� �ڷ� �̵�
					traceFile.writeLastInfo(lastMinutefile.getName(), rdAccessFile);
					logMap.initialize();
					//dayUpdate
					String dayFileName = Util.getTime(hourFileName);
					File[] hourFileList = new File(Util.TIME_FILE_DIR[Util.HOUR]).listFiles();
					//or getStartPoint�Լ� �Ἥ ������ ã�������� �ű⼭���� ������ ���ϱ��� �ֱ�(Util)
					//traceFile�������� index�� ���ĳ��� ��.
					for (int i = hourFileList.length - 1; i >= 0; i--) {
						if (!dayFileName.equals(Util.getTime(hourFileList[i]))) {
							break;
						}
						logMap.putData(hourFileList[i]);
						logFile.writeLogFile(logMap, dayFileName);
						traceFile.writeLastInfo(hourFileList[i].getName(), rdAccessFile);
					}
					//�� ������ ��� �ٽ� traceInfo�� �ڷ� �̵�
					//���� index�� ������ ������ �ƴҰ��
//					for (int i = hourFileList.length - 1; i >= 0; i--) {
//						if (!dayFileName.equals(Util.getTime(hourFileList[i]))) {
//							break;
//						}
//					} 	 �ݺ�
					traceFile.writeLastInfo(hourFileList[hourFileList.length - 1].getName(), rdAccessFile);
					logMap.initialize();
					//delete Minute Hour
					logFile.deleteLogFile(Util.MINUTE);
					logFile.deleteLogFile(Util.HOUR);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
