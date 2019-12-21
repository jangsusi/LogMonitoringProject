package com.logmonitoring.model;

public abstract class LogData {
	String[] logFactors;
	String ip;
	String email;
	String method;
	String url;
	
	abstract public boolean setLogData();
	
	LogData(String line){
		this.logFactors = line.split(" ");
	}
	
	public String getLogData() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(ip).append(" ").append(email).append(" ").append(method).append(" ").append(url);
		return strBuilder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LogData) {
			if((this.ip.equals(((LogData) obj).ip))
				&& (this.email.equals(((LogData) obj).email))
				&& (this.method.equals(((LogData) obj).method))
				&& (this.url.equals(((LogData) obj).url))) {
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
