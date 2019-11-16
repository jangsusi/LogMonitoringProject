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
		DateFormat df = new SimpleDateFormat("mm");
		DateFormat hour = new SimpleDateFormat("HH");
		Calendar cal = Calendar.getInstance();
		if (minuteCheck == 60) {
			minuteCheck = 0;
		}
		while (true) {
			cal.setTime(new Date());
			int nowTime = Integer.parseInt(df.format(cal.getTime()));
			if (nowTime != minuteCheck) {
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