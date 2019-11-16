package com.logmonitoring.main;

import java.util.ArrayList;
import com.logmonitoring.common.Util;

public class Main {
	
	public static void main(String[] args) {
		
//		ServerFileStorage serverStorage = new ServerFileStorage();
//		serverStorage.downloadFilesToLocal();
		
		ArrayList<LogCollecting> program = new ArrayList<LogCollecting>();
		program.add(new LogPieceCollecting(Util.MINUTE));
		program.add(new LogBundleCollecting(Util.HOUR));
		program.add(new LogBundleCollecting(Util.DAY));

		for(LogCollecting monitoring : program) {
			monitoring.startLogCollecting();
		}
		
		UpdatedLogCollecting thread = new UpdatedLogCollecting();
		thread.start();
		
	}

}
