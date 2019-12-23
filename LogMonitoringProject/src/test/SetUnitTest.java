package test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.logmonitoring.main.LogCountVO;
import com.logmonitoring.main.LogDataVO;

public class SetUnitTest {
	public static void main(String[] args) throws IOException {
		String str="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";
		String str1="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";

		long stime=System.currentTimeMillis();
		Set<LogCountVO> set=new HashSet<LogCountVO>();
		LogDataVO log=new LogDataVO(str);
		log.parsingRawLogLine(); 
		LogDataVO log1=new LogDataVO(str1);
		log1.parsingRawLogLine();
		LogCountVO countVO=new LogCountVO(log);
		LogCountVO countVO1=new LogCountVO(log1);

		
		set.add(countVO);
		set.add(countVO1);
		System.out.println(set.size());
		long etime=System.currentTimeMillis();
		
		System.out.println(stime-etime); 
		Iterator<LogCountVO> iter=set.iterator();
		while(iter.hasNext()) { 
			System.out.println(iter.next().getCount());
		}

		
	}
}
