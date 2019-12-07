package main;
import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class SaveLog {
	
	
	@SuppressWarnings("resource")
	ChannelSftp channelSftp=null;
	String path="/opt/TerraceTims/log/catalina/webmail";

	public static final int MINUTE=1;
	public static final int HOUR=2;
	public static final int DAY=3;

	
	FileWriter fileMinuteWriter=null;
	FileWriter fileHourWriter=null;
	FileWriter fileDayWriter=null;
	
	
	String minuteFileName="";
	String hourFileName="";

	String fileNameRawData="";
	LinkedHashMap<String,Integer> minuteLogMap=null;
	LinkedHashMap<String,Integer> hourLogMap=null;
	LinkedHashMap<String,Integer> dayLogMap=null;
	
	
	SaveLog(ChannelSftp channelSftp){
		this.channelSftp=channelSftp;
		LogDataListing();
	}
	public void LogDataListing() {		
		try {		
			int lastFileNum=0;
			BufferedReader bfReader=null;
			/*channelSftp.cd(path);
			Vector<ChannelSftp.LsEntry> fileList=channelSftp.ls(path);
			
			Collections.sort(fileList);
			int fileNum=fileList.size();*/
			File file=new File("C:/Users/user/Desktop/accessLog");
			File[] fileList=file.listFiles();
			int fileNum=fileList.length;
			for(int i=0;i<1;i++) {
			//for(ChannelSftp.LsEntry entry :fileList) {
				//String fileName=fileList.get(i).getFilename();
				if(true) {
				//if(fileName.contains("access")) {
					lastFileNum=i;
					bfReader=new BufferedReader(new FileReader(
							fileList[i]));
					/*bfReader=new BufferedReader(new InputStreamReader(
											channelSftp.get(fileName)));*/
					String logData=bfReader.readLine();			
					
					fileNameRawData=logData.split(" ")[3];
					String[] date=fileNameRawData.split("/|:");
					hourFileName=date[3];
					minuteFileName=date[4];
					
					minuteLogMap=new LinkedHashMap<String,Integer>();
					hourLogMap=new LinkedHashMap<String,Integer>();
					dayLogMap=new LinkedHashMap<String,Integer>();
					
					do {
						Parsing(logData);
					}while((logData=bfReader.readLine())!=null);
					
					SaveExtraLogData();
				}
			}
			bfReader.close();
			RememberLastFileInfo(fileList[lastFileNum].getName());
			//RememberLastFileInfo(fileList.get(lastFileNum).getFilename());
		}catch(IOException e) {
			e.printStackTrace();	
		}/*catch(SftpException e) {
			e.printStackTrace();
		}*/
		
	}
	public void SaveExtraLogData() {
		//마지막에 저장되지 않은 나머지 로그데이터들
		FileWriter fileWriter=null;
		try {
			if(minuteLogMap.size()!=0) {
				fileWriter=new FileWriter(Util.getFileName(fileNameRawData, MINUTE));
				fileWriter.write (Util.getFileCotext(minuteLogMap));
			} 
			if(hourLogMap.size()!=0) {
				fileWriter=new FileWriter(Util.getFileName(fileNameRawData, HOUR));
				fileWriter.write(Util.getFileCotext(hourLogMap));
			}	
			fileWriter=new FileWriter(Util.getFileName(fileNameRawData,DAY));
			fileWriter.write(Util.getFileCotext(dayLogMap));
			fileWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RememberLastFileInfo(String fileName) {
		try {
			FileWriter fileWriter=new FileWriter(new File("C:/Users/user/Desktop/monitoring_data/last_place.txt"));
			File lastFile=new File("C:/Users/user/Desktop/accessLog/"+fileName);
			fileWriter.write(lastFile.getName()+" "+lastFile.length());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//마지막 위치, 마지막 파일 이름을 저장해서 찾기로, 업데이트 할 minute,hour,day파일이름
	//minute,hour같은 경우 시간이 변경되서 다를 경우 새로 파일 추가해야되고 , 아니면 기존 파일 어
	public void UpdateFile(String lastFileInfo) {
		
		try {
			BufferedReader fReader=new BufferedReader(new FileReader(lastFileInfo));
			long lastPlace=Long.parseLong(fReader.readLine());
			String fileName=fReader.readLine();
			RandomAccessFile rdAccessFile=new RandomAccessFile(fileName,"r");
			rdAccessFile.seek(lastPlace);
			
			String logLine="";
			while((logLine=rdAccessFile.readLine())!=null) {
				
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//logData에서 유의미한 정보 가져오는 메소드 
	public void Parsing(File file) throws IOException {
		RandomAccessFile rdAccessFile=new RandomAccessFile(file,"r");
		try{ 
			String logLine="";		
			FileWriter fileWriterPlace=new FileWriter("C:/Users/user/Desktop/a/lastplace.txt");
			
			
			boolean isFirstData=true;
			while((logLine=rdAccessFile.readLine())!=null) {
				String[] logFactors=logLine.split(" ");
				fileNameRawData=logFactors[3];
				String[] date=fileNameRawData.split("/|:");
				String hourPresent=date[3];
				String minutePresent=date[4];
				
				//가장 첫 File만들 때
				if(isFirstData) {
					minuteFileName=minutePresent;
					hourFileName=hourPresent;
					fileHourWriter=new FileWriter(Util.getFileName(fileNameRawData,HOUR));
					fileMinuteWriter=new FileWriter(Util.getFileName(fileNameRawData,MINUTE));

					minuteLogMap=new LinkedHashMap<String,Integer>();
					hourLogMap=new LinkedHashMap<String,Integer>();
					dayLogMap=new LinkedHashMap<String,Integer>();	
					isFirstData=false;
				}				
				if(!hourPresent.equals(hourFileName)) {
					
					fileHourWriter.write(Util.getFileCotext(hourLogMap));
					fileHourWriter.close();
					fileHourWriter=new FileWriter(Util.getFileName(fileNameRawData,HOUR));
					hourFileName=hourPresent;
					hourLogMap.clear();
					
					fileMinuteWriter.write(Util.getFileCotext(minuteLogMap));
					fileMinuteWriter.close();
					fileMinuteWriter=new FileWriter(Util.getFileName(fileNameRawData,MINUTE));

					minuteFileName=minutePresent;
					minuteLogMap.clear();
				}
				////해당 분의 목록들이 다 끝났을 떄 직전 mm의 data 파일에 쓰기
				else if(!minutePresent.equals(minuteFileName)) {
					fileMinuteWriter.write(Util.getFileCotext(minuteLogMap));
					fileMinuteWriter.close();
					fileMinuteWriter=new FileWriter(Util.getFileName(fileNameRawData,MINUTE));

					minuteFileName=minutePresent;
					minuteLogMap.clear();
					
				
				}
				
				//아이피 이메일 메소드url붙이는 거
				StringBuilder strBuilder=new StringBuilder();
				strBuilder.append(logFactors[0]).append(" ")
							.append(logFactors[2]).append(" ")
							.append(logFactors[5]).append(" ")
							.append(logFactors[6]);
				String logData=strBuilder.toString();
				
				
				Util.PutLogData(minuteLogMap,logData);
				Util.PutLogData(hourLogMap, logData);
				Util.PutLogData(dayLogMap, logData);
					
			}
			
			//마지막에 저장되지 않은 나머지 로그데이터들
			if(minuteLogMap.size()!=0) {
				fileMinuteWriter.write (Util.getFileCotext(minuteLogMap));
				fileMinuteWriter.close();
			}
			if(hourLogMap.size()!=0) {
				fileHourWriter.write(Util.getFileCotext(hourLogMap));
				fileHourWriter.close();
			}

				fileDayWriter=new FileWriter(Util.getFileName(fileNameRawData,DAY));
				fileDayWriter.write(Util.getFileCotext(dayLogMap));
				fileDayWriter.close();		
			
			fileWriterPlace.write(rdAccessFile.length()+"");
			fileWriterPlace.close();
		
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		}finally{
			fileMinuteWriter.close();
			fileHourWriter.close();
			rdAccessFile.close();

			
		}
	}
	
	//logData를 요구사항에 맞는 정보로 가공한 후 map에 put
	public void Parsing(String logLine) throws IOException{
		
		String[] logFactors=logLine.split(" ");
		String[] date=logFactors[3].split("/|:");
		String hour=date[3];
		String minute=date[4];
		
		//아이피 이메일 메소드url붙이는 거
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(logFactors[0]).append(" ")
					.append(logFactors[2]).append(" ")
					.append(logFactors[5]).append(" ")
					.append(logFactors[6]);
		String logData=strBuilder.toString();
		
		
		fileNameRawData=AppendOrSave(hour,minute)?logFactors[3]:fileNameRawData;
		
		PutLogData(minuteLogMap,logData);
		PutLogData(hourLogMap, logData);
		PutLogData(dayLogMap, logData);
	}
	
	
	//파일에 저장 or map에 put
	public boolean AppendOrSave(String hour,String minute) throws IOException {
		FileWriter fileWriter=null;
		boolean isNewFile=false;
		if(!hour.equals(hourFileName)) {
			fileWriter=new FileWriter(Util.getFileName(fileNameRawData,HOUR));
			fileWriter.write(Util.getFileCotext(hourLogMap));
			fileWriter.close();
			hourFileName=hour;
			hourLogMap.clear();
			isNewFile=true;
		}
		if(!minute.equals(minuteFileName)) {
			fileWriter=new FileWriter(Util.getFileName(fileNameRawData,MINUTE));
			fileWriter.write(Util.getFileCotext(minuteLogMap));
			fileWriter.close();
			minuteFileName=minute;
			minuteLogMap.clear();
			isNewFile=true;
		}
		
		return isNewFile;
	}	
	
	
	public void PutLogData(LinkedHashMap<String,Integer> map,String logData) {
		//map에 put했을 때 count가 0이면 새것
		//아니면 기존의것
		
		
		if(map.get(logData)==null) {
			map.put(logData, 1);
		
			//System.out.println("x"+map.get(logData)+" vs "+logData);
		}
		else {
			map.put(logData, map.get(logData)+1);
			//System.out.println("o"+map.get(logData)+" vs "+logData);
		}
	}

		
	public boolean SaveAsHHmm() throws IOException{
		InputStream in = null;
		
		try{
			channelSftp.cd(path);
			Vector<ChannelSftp.LsEntry> fileList=channelSftp.ls(path);
			
			Collections.sort(fileList);
			int listSize=fileList.size();
			for(int i=0;i<listSize;i++) {
			//for(ChannelSftp.LsEntry entry :fileList) {
				String fileName=fileList.get(i).getFilename();
				if(fileList.get(i).getFilename().contains("access")) {
					BufferedReader bfReader=new BufferedReader(new InputStreamReader(
											channelSftp.get(fileName)));
					//in = channelSftp.get(entry.getFilename());
					//in = channelSftp.get("access_2019-11-09.txt");
					String logLine="";
					while((logLine=bfReader.readLine())!=null) {
						Parsing(logLine);
					}
					
					File file=new File("C:/Users/user/Desktop/a/fos.txt");
					
					FileOutputStream fos=new FileOutputStream(file);
					byte[] buf=new byte[1024*8];
					int len=0;
					while((len=in.read(buf))>0)
						fos.write(buf,0,len);
					fos.close();
					in.close();
					Parsing(file);
					break;
				}
				
			}
			/*
			 * 
			 * 
			 * 
			 * */
			
			//InputStream to File
			/*
			 * String fileTemplate="access_2019-11-";
			for(int i=1;i<=7;i++) {
				
				
				
				FileOutputStream fos=new FileOutputStream(file);
				byte[] buf=new byte[1024*8];
				int len=0;
				while((len=in.read(buf))>0)
					fos.write(buf,0,len);
				fos.close();
				//File Split
				
			}*/
		int cnt=0;
			File file=new File("C:/Users/user/Desktop/accessLog");
			File[] fileLists=file.listFiles();
			for(File aFile:fileLists) {
				Parsing(aFile);
				cnt++;
				if(cnt==1)break;
			}
			
			}catch(SftpException se){
			
			se.printStackTrace();
		}finally {
			
			
		}
		
		return true;
	}

}