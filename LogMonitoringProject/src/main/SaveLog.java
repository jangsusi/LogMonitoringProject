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
		
	}
	
	

}