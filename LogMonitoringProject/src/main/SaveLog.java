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

public abstract class SaveLog {
	public static final int MINUTE=0;
	public static final int HOUR=1;
	public static final int DAY=2;
	public static final String PARSING_FILE_DIR="C:/Users/user/Desktop/monitoring_data/";
	
	String minuteFileName="";
	String hourFileName="";
	String fileNameRawData="";
	
	LinkedHashMap<String,Integer> minuteLogMap=null;
	LinkedHashMap<String,Integer> hourLogMap=null;
	LinkedHashMap<String,Integer> dayLogMap=null;
	
	ArrayList<LinkedHashMap<String,Integer>> timeMapList=null;
	
	public abstract void LogDataListing();
	SaveLog(){
		minuteLogMap=new LinkedHashMap<String,Integer>();
		hourLogMap=new LinkedHashMap<String,Integer>();
		dayLogMap=new LinkedHashMap<String,Integer>();
		timeMapList=new ArrayList<LinkedHashMap<String,Integer>>();
		timeMapList.add(minuteLogMap);
		timeMapList.add(hourLogMap);
		timeMapList.add(dayLogMap);

	}
	
	//fileNameRawData : 로그데이터의 시간부분, hourFileName : 현재 시간파일의 시간, minuteFileName : 현재 분파일의 분
	public void VariableInitialzation(String firstLogData) {
		
		fileNameRawData=firstLogData.split(" ")[3];
		
		String[] date=fileNameRawData.split("/|:");
		hourFileName=date[3];
		minuteFileName=date[4];
	}
	
	//file에 저장되지 않은 map안의 데이터들
	public void SaveExtraLogData() {
		//마지막에 저장되지 않은 나머지 로그데이터들
		FileWriter fileWriter=null;
		try {
			CombineMapData(HOUR);
			CombineMapData(DAY);
			for(int i=0;i<3;i++) {
				if(timeMapList.get(i).size()!=0) {
					fileWriter=new FileWriter(MakeFileName(fileNameRawData, i));
					fileWriter.write (MoveMapToFile(timeMapList.get(i)));
					fileWriter.close();
				} 
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	//logData를 요구사항에 맞는 정보로 가공한 후 map에 put
	public void SaveLogData(String logLine){

		String[] logFactors=logLine.split(" ");
		if(!logFactors[1].equals("-")&&!logFactors[2].contains("@")) 
			return;	
		
		fileNameRawData=PutOrClearMap(logFactors[3])?logFactors[3]:fileNameRawData;
		
		
		String logData=ParsingLogData(logFactors);

		
		minuteLogMap.put(logData, minuteLogMap.get(logData)==null?1:minuteLogMap.get(logData)+1);
		/*for(int i=0;i<3;i++) 
			timeMapList.get(i).put(logData, timeMapList.get(i).get(logData)==null
											?1
											:timeMapList.get(i).get(logData)+1);*/
		
	}
	
	//ex)172.21.27.88 daoutest@terracetech.co.kr GET /internal/healthcheck
	public String ParsingLogData(String[] logFactors) {
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(logFactors[0]).append(" ")
					.append(logFactors[2]).append(" ")
					.append(logFactors[5]).append(" ")
					.append(logFactors[6]);
		return(strBuilder.toString());
	}
	
	
	//map을 파일에 저장 or 계속 map에 put
	public boolean PutOrClearMap(String dateLogFactor){
		String[] date=dateLogFactor.split("/|:");
		FileWriter fileWriter=null;
		boolean isNewFile=false;
		try {
			if(!date[4].equals(minuteFileName)) {
				fileWriter=new FileWriter(MakeFileName(fileNameRawData,MINUTE));
				fileWriter.write(MoveMapToFile(minuteLogMap));
				CombineMapData(HOUR);
				minuteFileName=date[4];
				minuteLogMap.clear();
				fileWriter.close();
				isNewFile=true;
			}
			if(!date[3].equals(hourFileName)) {
				fileWriter=new FileWriter(MakeFileName(fileNameRawData,HOUR));
				fileWriter.write(MoveMapToFile(hourLogMap));
				CombineMapData(DAY);
				hourFileName=date[3];
				hourLogMap.clear();
				fileWriter.close();
				isNewFile=true;
			}
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return isNewFile;
	}	

	//저장할 파일 이름 생성
	public String MakeFileName(String data,int type) {
		String[] arr=data.split("/|:");
		StringBuilder fileName=new StringBuilder();
		arr[1]=Common.ChangeMMFormat(arr[1]);
		arr[0]=arr[0].substring(1); 
		
		fileName.append("C:/Users/user/Desktop/monitoring_data/");
		
		switch(type) {
		//yyyyMMddHHmm
		case MINUTE:
			return fileName.append("minute/").append(arr[2]).append(arr[1]).append(arr[0]).append(arr[3])
			.append(arr[4]).append(".txt").toString();
		//yyyyMMddHH
		case HOUR:
			return fileName.append("hour/").append(arr[2]).append(arr[1]).append(arr[0]).append(arr[3])
			.append(".txt").toString();
		//yyyyMMdd stat_yyyy-MM-dd.txt
		case DAY:
			return fileName.append("day/").append("stat_").append(arr[2]).append("-").append(arr[1])
					.append("-").append(arr[0]).append(".txt").toString();
		}
		return null;
	}
	
	
	//map의 내용을 file에 wirte
	public String MoveMapToFile(LinkedHashMap<String,Integer> map) {
		//내림차순 정렬
		ArrayList<Map.Entry<String,Integer>> descList=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(descList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
					    return e2.getValue().compareTo(e1.getValue());
				}
		});
		StringBuilder totalLogList=new StringBuilder();
		for(Entry<String, Integer> logData : descList) {
			
			totalLogList.append(logData.getValue()).append("개")
			.append(logData.getKey()).append("\n");
		}
		return totalLogList.toString();
	}
	
	//해당 분파일을 시파일 map에 put
	public void CombineMapData(int saveType) {
		
		for(String logData : timeMapList.get(saveType-1).keySet()) {
			Integer logDataCnt= timeMapList.get(saveType-1).get(logData);
			timeMapList.get(saveType).put(logData,
					timeMapList.get(saveType).get(logData)==null
					?logDataCnt
					:timeMapList.get(saveType).get(logData)+logDataCnt);
		}
	}
		
	//마지막 파일 pointer저장
	public void RemeberLastPointer(Long filePointer) {
		try {
			FileWriter fileWriter=new FileWriter(new File(PARSING_FILE_DIR+"last_place.txt"));
			fileWriter.write(filePointer+"\n"+minuteFileName+"\n"+hourFileName);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}