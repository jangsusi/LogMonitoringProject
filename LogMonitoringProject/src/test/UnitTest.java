package test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.logmonitoring.main.LogDataVO;

	public class UnitTest {
	public static void main(String[] args) throws IOException {
		String str="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";
		String str1="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";

		long stime=System.currentTimeMillis();
		for(int i=0;i<100;i++) {
		LinkedHashMap<LogDataVO,Integer> map=new LinkedHashMap<LogDataVO,Integer>();
		LogDataVO log=new LogDataVO(str);
		
		LogDataVO log1=new LogDataVO(str1);	
		log1.parsingRawLogLine();
		

		map.put(log, 1);
		map.put(log1, 1);
		System.out.println(map.size());
		
		}
		long etime=System.currentTimeMillis();
		long ttime=etime-stime;
		
		System.out.println(ttime);
		
		/*if(log==log1)
			System.out.println("동일");
		if(log.equals(log1))
			System.out.println("동일1");
		else
			System.out.println("동일하지 않음.");*/

	}
}
