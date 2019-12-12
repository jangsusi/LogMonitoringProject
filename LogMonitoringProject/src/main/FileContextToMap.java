package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class FileContextToMap {
	File file;
	FileContextToMap(File file){
		this.file=file;
	}
	public LinkedHashMap<String,Integer> copyToMap(){
		LinkedHashMap<String,Integer> map=new LinkedHashMap<String,Integer>();
		String line="";
		try {
			BufferedReader bfReader=new BufferedReader(new FileReader(file));
			while((line=bfReader.readLine())!=null) {
				String[] saveData=line.split("°³");
				map.put(saveData[1], Integer.parseInt(saveData[0]));
			}
			bfReader.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
