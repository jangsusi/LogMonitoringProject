package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class LogMap {
	LinkedHashMap<LogDataVO, Integer> map;

	public LogMap() {
		map = new LinkedHashMap<LogDataVO, Integer>();
	}

	public void addLog(LogDataVO log) {
		if(map.get(log)==null) {
			map.put(log, 1);
		}
		else {
			map.put(log, map.get(log)+1);
		}
		ArrayList<LogDataVO> list=new ArrayList<LogDataVO>();
		HashSet<String> set=new HashSet<String>();
		set.c
	}
	
	public void delete() {
		map.clear();
	}
	
}
