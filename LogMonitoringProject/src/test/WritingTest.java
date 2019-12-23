package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.logmonitoring.common.Util;
import com.logmonitoring.main.DayLogMonitoring;
import com.logmonitoring.main.LogBundleCollecting;
import com.logmonitoring.main.LogCollecting;
import com.logmonitoring.main.LogPieceCollecting;

public class WritingTest {
	public static void main(String[] args) throws IOException {	
		try {
			BufferedWriter bfWriter = new BufferedWriter(new FileWriter(Util.LOCAL_FILE_DIR + "hour_trace.txt"));
			bfWriter.write(1);
			bfWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
