package com.logmonitoring.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.logmonitoring.model.TraceData;

//traceData랑 file간의 교류
public class TraceFileProcessor {
	File traceFile;
	
	public TraceFileProcessor(String filePath) {
		traceFile = new File(filePath);
	}

	public long size() {
		return traceFile.length();
	}
	
	public TraceData getLastInfo() {
		TraceData traceData = null;
		try (BufferedReader bfReader = new BufferedReader(new FileReader(traceFile))) {
			traceData = new TraceData(bfReader.readLine());	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
		return traceData;
	}

	public void writeLastInfo(TraceData traceData) {
		try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter(traceFile))) {
			bfWriter.write(traceData.getData());	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
	}

}
