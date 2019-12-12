package main;

public class MinuteMap extends TimeMap {
	public void add(String key) {
		if(map.get(key)==null)
			map.put(key, 1);
		else
			map.put(key, map.get(key)+1);
	}
}
