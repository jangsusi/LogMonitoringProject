package main;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CopyingArray {
	public String copyToFile(ArrayList<Map.Entry<String,Integer>> list,File file) {
		//내림차순 정렬
		StringBuilder data=new StringBuilder();
		for(Entry<String, Integer> entry : list) {
			data.append(entry.getValue()).append("개")
			.append(entry.getKey()).append("\n");
		}
		return data.toString();
	}
}
