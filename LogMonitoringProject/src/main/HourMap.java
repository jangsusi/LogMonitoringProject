package main;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class HourMap extends TimeMap {
	public void add(LinkedHashMap<String,Integer> addMap) {
		for(String key: addMap.keySet()) {
			if(map.get(key)==null)
				map.put(key, addMap.get(key));
			else
				map.put(key, map.get(key)+addMap.get(key));
		}
	}
}
