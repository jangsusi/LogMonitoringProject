package com.logmonitoring.model;

public interface TimeCheckable {
	public String getTime();
	public boolean isDifferentTime(String time);
	public String setTime(String rawTime);
}