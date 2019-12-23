package com.logmonitoring.main;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.logmonitoring.common.Util;

public class UpdatedLogCollecting extends Thread {

	public void run() {
		LogCollecting hourUpdate = new LogBundleCollecting(Util.HOUR);
		hourUpdate.startLogCollecting();
		LogCollecting dayUpdate = new LogBundleCollecting(Util.DAY);
		dayUpdate.startLogCollecting();

		File[] minuteFiles = new File(Util.TIME_FILE_DIR[Util.MINUTE]).listFiles();
		String lastMinuteFileName = minuteFiles[minuteFiles.length - 1].getName();
		int minuteCheck = Integer.parseInt(lastMinuteFileName.substring(Util.MINUTE_FRONT_POSITION, Util.MINUTE_LAST_POSITION)) + 1;
		DateFormat minute = new SimpleDateFormat("mm");
		Calendar cal = Calendar.getInstance();
		if (minuteCheck == 60) {
			minuteCheck = 0;
		}
		while (true) {
			cal.setTime(new Date());
			int nowTime = Integer.parseInt(minute.format(cal.getTime()));
			if (nowTime != minuteCheck) {
				System.out.println("현재 분 : "+ nowTime + "분 업데이트 시작");
				minuteCheck = nowTime;
				LogCollecting minuteUpdate = new LogPieceCollecting(Util.MINUTE);
				if (minuteUpdate.updateLogCollecting()) {
					hourUpdate.updateLogCollecting();
					dayUpdate.updateLogCollecting();
				}
			}
		}
	}
}