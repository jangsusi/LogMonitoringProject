package teset;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import main.LogDataVO;

	public class UnitTest {
	public static void main(String[] args) throws IOException {
		String str="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";
		String str1="- - - [16/Nov/2019:00:00:45 +0900] GET /internal/healthcheck HTTP/1.1 200 511 \"-\" \"Java/1.8.0_162\" \"-\" 0.042";

		LinkedHashMap<LogDataVO,Integer> map=new LinkedHashMap<LogDataVO,Integer>();
		LogDataVO log=new LogDataVO(str);
		if(!log.isSupportedFormat())
			System.out.println("정상타입아님");
		log.parsingRawLogLine();
		System.out.println(log.getLogData());
		
		LogDataVO log1=new LogDataVO(str1);
		if(!log1.isSupportedFormat())
			System.out.println("정상타입아님");
		log1.parsingRawLogLine();
		System.out.println(log1.getLogData());

		map.put(log, 1);
		map.put(log1, 1);
		System.out.println(map.size());
		
		if(log==log1)
			System.out.println("동일");
		if(log.equals(log1))
			System.out.println("동일1");
		else
			System.out.println("동일하지 않음.");

	}
}
