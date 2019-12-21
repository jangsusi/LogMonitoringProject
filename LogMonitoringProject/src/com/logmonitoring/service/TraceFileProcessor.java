package com.logmonitoring.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.logmonitoring.common.Util;

public class TraceFileProcessor {
	File traceFile;
	
	public TraceFileProcessor(String filePath) {
		traceFile = new File(filePath);
	}

	public long size() {
		return traceFile.length();
	}

	public String getLastInfo() {
		String fileInfo = null;
		try (BufferedReader bfReader = new BufferedReader(new FileReader(traceFile))) {
			fileInfo = bfReader.readLine();	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
		return fileInfo;
	}

	public void writeLastInfo(String fileName, RandomAccessFile rdAccessFile) {
		try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter(traceFile))) {
			bfWriter.write(fileName + " " + rdAccessFile.getFilePointer());	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
	}

//	public void writeLastInfo(String fileName) {
//		try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter(traceFile))) {
//			bfWriter.write(fileName);	
//		} catch (IOException e) {
//			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
//			e.printStackTrace();
//		}
//	}

}
