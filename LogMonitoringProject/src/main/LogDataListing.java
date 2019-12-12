package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LogDataListing {
	private static final int GNC=4;
	File file;
	
	LogDataListing(File file) {
		this.file=file;	
	}
	
	public void getLogList() {
		BufferedReader bfReader=new BufferedReader(new FileReader(file));	
		String firstLine=bfReader.readLine();
		//초기화 00 00 00 일 시 분
		FileName miniuteFileName=new FileName(firstLine);
		String line="";
		LogMap logMap=new LogMap();
		do {
			LogDataVO log=new LogDataVO(line);
			if(!log.isSupportedFormat())
				continue;
			log.parsingRawLogLine();
			logMap.addLog(log);
			
			SaveLogData(line);
		}while((line=bfReader.readLine())!=null);	
		SaveExtraLogData();
		bfReader.close();
	}`
}
