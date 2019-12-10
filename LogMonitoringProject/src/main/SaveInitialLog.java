package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import com.jcraft.jsch.ChannelSftp;

public class SaveInitialLog extends SaveLog {
	SaveInitialLog() {
		LogDataListing();
	}

	@Override
	public void LogDataListing() {		
		try {		
			BufferedReader bfReader=null;
			File file=new File(Main.LOCAL_ACCESS_FILE_DIR);
			File[] fileList=file.listFiles();
			int fileNum=fileList.length;
			for(int i=0;i<fileNum-1;i++) {
			//for(int i=0;i<1;i++) {
				bfReader=new BufferedReader(new FileReader(fileList[i]));	
				String logData=bfReader.readLine();	
				VariableInitialzation(logData);			
				do {
					if(!logData.split(" ")[4].equals("+0900]"))
						break;
					SaveLogData(logData);
				}while((logData=bfReader.readLine())!=null);	
				SaveExtraLogData();
				bfReader.close();
			}

			//'현재-1'분 까지 데이터 저장 위한 변수
			String lastMinute=Common.getTime("mm");
			RandomAccessFile lastFile=new RandomAccessFile(fileList[fileNum-1],"r");
			String logData=lastFile.readLine();
			VariableInitialzation(logData);	
			do {
				if(!logData.split(" ")[4].equals("+0900]")
						||logData.split(" ")[3].split("/|:")[4].equals(lastMinute))
					break;
				SaveLogData(logData);
			}while((logData=lastFile.readLine())!=null);
			lastFile.close();
			SaveExtraLogData();
			
			RemeberLastPointer(lastFile.getFilePointer());
			
		}catch(IOException e) {
			e.printStackTrace();	
		}
	}
}
