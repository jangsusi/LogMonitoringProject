package main;

public class ParsingData {
	String data;
	String time;//fileNameRawdata
	
	ParsingData(String data){
		this.data=data;
	}
	public String parsingLogData() {
		String[] logFactors=data.split(" ");
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(logFactors[0]).append(" ")
					.append(logFactors[2]).append(" ")
					.append(logFactors[5]).append(" ")
					.append(logFactors[6]);
		return(strBuilder.toString());
	}
	public String parsingTime(String[] logFactors) {
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(logFactors[0]).append(" ")
					.append(logFactors[2]).append(" ")
					.append(logFactors[5]).append(" ")
					.append(logFactors[6]);
		return(strBuilder.toString());
	}
}
