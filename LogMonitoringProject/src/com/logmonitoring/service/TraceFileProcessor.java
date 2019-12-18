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
		String filePosition = "0";
		String pointer = "0";
		try (BufferedReader bfReader = new BufferedReader(new FileReader(traceFile))) {
			filePosition = bfReader.readLine();
			pointer = bfReader.readLine();
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
		return filePosition + " "+ pointer;
	}

	public void writeLastPlace(int pos, RandomAccessFile rdAccessFile) {
		try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter(traceFile))) {
			bfWriter.write(pos + "\n" + rdAccessFile.getFilePointer());	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
	}

	public void editLastPosition(int lastPos) {
		String pointer = getLastInfo().split(" ")[Util.FILE_POINTER];
		try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter(traceFile))) {		
			bfWriter.write(lastPos + "\n" + pointer);	
		} catch (IOException e) {
			System.out.println(traceFile.getName() + "파일 존재 안함.\n");
			e.printStackTrace();
		}
	}

}
