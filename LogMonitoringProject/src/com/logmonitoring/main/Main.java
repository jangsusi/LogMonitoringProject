package com.logmonitoring.main;

import java.util.ArrayList;
import com.logmonitoring.common.Util;

public class Main {
	
	public static void main(String[] args) {
		ServerFileStorage serverStorage = new ServerFileStorage(Util.url, Util.id, Util.password);
		serverStorage.connectServer();
		serverStorage.downloadFilesToLocal();
		
		ArrayList<LogMonitoring> program = new ArrayList<LogMonitoring>();
		program.add(new LogPieceMonitoring(Util.MINUTE));
		program.add(new LogBundleMonitoring(Util.HOUR));
		program.add(new LogBundleMonitoring(Util.DAY));

		for(LogMonitoring monitoring : program) {
			monitoring.startMonitoring();
		}
		
//		logFile.deleteLogFile(Util.MINUTE);
//		logFile.deleteLogFile(Util.HOUR);
		
	}

}