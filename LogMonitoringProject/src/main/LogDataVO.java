package main;

public class LogDataVO {
	private static final int GNC=4;
	
	String logLine;
	String[] logFactors;
	String IP;
	String email;
	String method;
	String url;
	
	public LogDataVO(String logLine) {
		this.logLine=logLine;
		this.logFactors=logLine.split(" ");
	}

	public void parsingRawLogLine() {
		if(isSupportedFormat()) {
			IP=logFactors[0];
			email=logFactors[2];
			method=logFactors[5];
			url=logFactors[6];
		}
	}

	public boolean isSupportedFormat() {
		if((!logFactors[1].equals("-")&&!logFactors[2].contains("@"))
				||!logFactors[GNC].equals("+0900]")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public String getLogData() {
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(IP).append(email).append(method).append(url);
		return strBuilder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LogDataVO) {
			if((this.IP.equals(((LogDataVO) obj).IP))
				&& (this.email.equals(((LogDataVO) obj).email))
				&& (this.method.equals(((LogDataVO) obj).method))
				&& (this.url.equals(((LogDataVO) obj).url)))
					return true;
		}
		return false;
		
	}

	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;

		result=prime*result+((IP==null) ? 0 : IP.hashCode());
		result=prime*result+((email==null) ? 0 : email.hashCode());
		result=prime*result+((method==null) ? 0 : method.hashCode());
		result=prime*result+((url==null) ? 0 : url.hashCode());

		return result;
	}
}
