package main;

import java.util.LinkedHashMap;

public class TimeMap {
	LinkedHashMap<String,Integer> map;
	TimeMap(){
		map=new LinkedHashMap<String,Integer>();
	}
	public void init(LinkedHashMap<String,Integer> addMap) {
		map.putAll(addMap);
	}
	public void delete() {
		map.clear();
	}
}
