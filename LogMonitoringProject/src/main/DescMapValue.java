package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DescMapValue {

	
	public ArrayList desc(LinkedHashMap<String,Integer> map) {
		ArrayList<Map.Entry<String,Integer>> descList=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(descList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
					    return e2.getValue().compareTo(e1.getValue());
				}
		});
		return descList;
	}
}
