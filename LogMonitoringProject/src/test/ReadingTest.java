package test;

import java.io.IOException;

import com.logmonitoring.common.Util;
import com.logmonitoring.main.DayLogMonitoring;
import com.logmonitoring.main.LogBundleCollecting;
import com.logmonitoring.main.LogCollecting;
import com.logmonitoring.main.LogPieceCollecting;

public class ReadingTest {

	
	public static void main(String[] args) throws IOException {	
		LogCollecting monitoring = new LogPieceCollecting(Util.ACCESS_FILE_DIR);
		monitoring.startMonitoring();
		LogCollecting monitoring1=new LogBundleCollecting(Util.MINUTE_FILE_DIR);
		monitoring1.startMonitoring();
		LogCollecting monitoring2=new DayLogMonitoring(Util.HOUR_FILE_DIR);
		monitoring2.startMonitoring();
	}
}
