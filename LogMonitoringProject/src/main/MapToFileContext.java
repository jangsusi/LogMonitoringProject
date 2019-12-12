package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapToFileContext {
	LinkedHashMap<String,Integer> map;
	File file;
	MapToFileContext(LinkedHashMap<String,Integer> map,File file){
		this.map=map;
		this.file=file;
	}
	//map�� ������ file�� wirte
		public void writeAll() {
			try {
				FileWriter fileWriter=new FileWriter(file);
				StringBuilder data=new StringBuilder();
				for(Entry<String, Integer> entry : map.entrySet()) {
					data.append(entry.getValue()).append("��")
					.append(entry.getKey()).append("\n");
				}
				fileWriter.write(data.toString());
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
}
