package com.logmonitoring.model;

public class LogData {
	String[] logFactors;
	String ip;
	String email;
	String method;
	String url;
	int count;

	public LogData(String line) {
		this.logFactors = line.split(" ");
	}

	public boolean setLogData() {
		ip = logFactors[1];
		email = logFactors[2];
		method = logFactors[3];
		url = logFactors[4];
		count = Integer.parseInt(logFactors[0]);
		return true;
	}

	public String getLogData() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(ip).append(" ").append(email).append(" ").append(method).append(" ").append(url);
		return strBuilder.toString();
	}

	public int getCount() {
		return count;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LogData) {
			if ((this.ip.equals(((LogData) obj).ip)) && (this.email.equals(((LogData) obj).email))
					&& (this.method.equals(((LogData) obj).method)) && (this.url.equals(((LogData) obj).url))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += ip.hashCode();
		result += email.hashCode();
		result += method.hashCode();
		result += url.hashCode();
		return result;
	}
	

}
