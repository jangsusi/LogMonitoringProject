package com.logmonitoring.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.logmonitoring.common.Util;
import com.logmonitoring.model.LogData;

public class LogFileProcessor {

	public LogFileProcessor() {
	}

	private String getFileDir(String fileName) {
		String fileDir = "";
		if (fileName.length() == Util.MINUTE_FILE_NAME_LENGTH) {
			fileDir = Util.MINUTE_FILE_DIR;
		} else if (fileName.length() == Util.HOUR_FILE_NAME_LENGTH) {
			fileDir = Util.HOUR_FILE_DIR;
		} else {
			fileDir = Util.DAY_FILE_DIR;
		}
		return fileDir;
	}

	public void writeLogFile(MapProcessor map, String fileName) {
		File file = new File(getFileDir(fileName) + fileName);
		List<Map.Entry<LogData, Integer>> list = new LinkedList<>(map.getMap().entrySet());
		Collections.sort(list, new Comparator<Map.Entry<LogData, Integer>>() {
			public int compare(Map.Entry<LogData, Integer> e1, Map.Entry<LogData, Integer> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});
		StringBuilder strBuilder = new StringBuilder();
		for (Map.Entry<LogData, Integer> entry : list) {
			strBuilder.append(entry.getValue()).append(" ").append(entry.getKey().getLogData()).append("\n");
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(strBuilder.toString());
		} catch (IOException e) {
			System.out.println(fileName + "파일의 이름이 유효하지 않거나 존재하지 않는 파일입니다.");
			return;
		}
	}

	public void deleteLogFile(int type) {
		String lastFileName = Util.getDeleteTimeList(type);
		System.out.println(lastFileName);
		File[] fileList = new File(Util.TIME_FILE_DIR[type]).listFiles();
		int num = 0;
		for (num = 0; num < fileList.length; num++) {
			if (fileList[num].getName().equals(lastFileName)) {
				break;
			}
			fileList[num].delete();
		}

	}

}
