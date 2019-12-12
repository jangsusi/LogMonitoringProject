package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadFile {
	String filePath;
	File[] fileList;
	LoadFile(String filePath){
		this.filePath=filePath;
	}
	public void readFile() {
		File file=new File(filePath);
		fileList=file.listFiles();
	}
	public void readLine() {
		int len=fileList.length;
		for(int i=0;i<len;i++) {
			try {
				BufferedReader bfReader=new BufferedReader(new FileReader(fileList[i]));
				String line="";
				do {
					if(!line.split(" ")[4].equals("+0900]"))
						break;
				}while((line=bfReader.readLine())!=null);
				bfReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("BufferedReader오류");
				e.printStackTrace();
			}catch (IOException e) {
				System.out.println("문장읽기 오류");
				e.printStackTrace();
			}		
		}
	}
}
