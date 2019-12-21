package com.logmonitoring.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import com.logmonitoring.model.LogDataPlusTime;
import com.logmonitoring.model.LogData;
import com.logmonitoring.model.LogDataPlusCount;

public class MapProcessor {
	HashMap<LogData,Integer> map;
	
	public MapProcessor() {	
		map = new HashMap<LogData,Integer>();
	}
	
	public void initialize() {
		map.clear();
	}

	public HashMap getMap() {
		return map;
	}
	
	public void putData(LogDataPlusTime logData) {
		if(map.get(logData) == null) {
			map.put(logData, 1);
		} else {
			map.put(logData, map.get(logData)+1);
		}
	}

	private void putData(LogDataPlusCount logData) {
		if(map.get(logData) == null) {
			map.put(logData, logData.getCount());
		} else {
			map.put(logData, map.get(logData) + logData.getCount());
		}
	}
	
	public void putData(File file) {
		if(file.length() == 0) {
			return;
		}
		try (BufferedReader bfReader = new BufferedReader(new FileReader(file))) {
			String line = bfReader.readLine();
			do {
				LogData logData=new LogDataPlusCount(line);
				logData.setLogData();			
				putData((LogDataPlusCount)logData);
			} while((line=bfReader.readLine()) != null);
		} catch (FileNotFoundException e) {
			System.out.println(file.getName() + "파일을 찾을 수 없음(저장실패)");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
