package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import com.jcraft.jsch.ChannelSftp;

public class SaveUpdatedLog extends SaveLog {
	String lastFileNameRawData;
	SaveUpdatedLog() {
		LogDataListing();
	}
	
	@Override
	public void LogDataListing() {
		try {			
			BufferedReader fReader=new BufferedReader(new FileReader(PARSING_FILE_DIR+"last_place.txt"));
				
			String filePath=Main.LOCAL_ACCESS_FILE_DIR+"access_"+Common.getTime("yyyy-MM-dd")+".txt";
			
			
			long lastFilePointer=Long.parseLong(fReader.readLine());
			String lastMinuteFileName=fReader.readLine();
			String lastHourFileName=fReader.readLine();
			fReader.close();
			LastFileToMap(lastMinuteFileName,MINUTE);
			LastFileToMap(lastHourFileName,HOUR);
			LastFileToMap(null,DAY);
			
			
			RandomAccessFile lastFile=new RandomAccessFile(filePath,"r");
			lastFile.seek(lastFilePointer);
			String logData=lastFile.readLine();
			if(logData==null) {
				System.out.println("업데이트할 것 없음.");
				return;
			}
			VariableInitialzation(logData);
			do {
				if(!logData.split(" ")[4].equals("+0900]"))
					break;
				SaveLogData(logData);
			}while((logData=lastFile.readLine())!=null);
			lastFile.close();
			SaveExtraLogData();
			
			RemeberLastPointer(lastFile.getFilePointer());		
			//DeleteLog();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	/*Calendar cal=Calendar.getInstance();
			Date nowTime=cal.getTime();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			
			String date=dateFormat.format(nowTime);
			//file만들지 따지는 구문
			if(!fileName.contains(date)) {
				File dayFile=new File(Main.fileDir+"stat_"+date+".txt");
				
				SimpleDateFormat hourFormat=new SimpleDateFormat("yyyyMMddHH");
				File hourFile=new File(Main.fileDir+hourFormat.format(nowTime));
				
				SimpleDateFormat minuteFormat=new SimpleDateFormat("yyyyMMddHHmm");
				File minuteFile=new File(Main.fileDir+minuteFormat.format(nowTime));
			}
			else {
				
			}*/
	
	public void DeleteLog() {
		File[] minuteDeleteList=new File(PARSING_FILE_DIR+"minute").listFiles();
		File[] hourDeleteList=new File(PARSING_FILE_DIR+"hour").listFiles();
		for(int i=0;i<minuteDeleteList.length-60;i++) {
			minuteDeleteList[i].delete();
		}
		for(int i=0;i<hourDeleteList.length-24;i++) {
			hourDeleteList[i].delete();
		}
	}
	
	//마지막 파일에 저장된 로그 정보들 각 map에 put
	public void LastFileToMap(String lastFileName,int saveType) throws FileNotFoundException{	
		if((saveType==MINUTE&&minuteFileName.equals(lastFileName))
				||(saveType==HOUR&&hourFileName.equals(lastFileName))
				||saveType==DAY) {
			File file=new File(MakeFileName(fileNameRawData,saveType));
			try {
				BufferedReader bfReader=new BufferedReader(new FileReader(file));
				String line="";
				while((line=bfReader.readLine())!=null) {
					String[] saveData=line.split("개");
						System.out.println(saveData[0]+saveData[1]);
					timeMapList.get(saveType).put(saveData[1], Integer.parseInt(saveData[0]));
				}
				bfReader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
