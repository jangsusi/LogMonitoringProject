package main;

import java.util.LinkedHashMap;
import java.util.Map;

public class PutInMap {

	LinkedHashMap<String,Integer> toMap;
	PutInMap(LinkedHashMap<String,Integer> toMap){
		this.toMap=toMap;
	}
	public void mapToMap(LinkedHashMap<String,Integer> fromMap) {
		for(String data : fromMap.keySet()) {
			Integer dataNum= fromMap.get(fromMap);
			toMap.put(data,toMap.get(data)==null?dataNum:toMap.get(data)+dataNum);
		}	
	}
	public void dataToMap(String key,Integer value) {
		toMap.put(key,value);
	}
}
