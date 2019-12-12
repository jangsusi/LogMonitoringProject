package main;

public class FileName {
	String data;//logLine;
	FileName(String data){
		this.data=data;
	}
	//저장할 파일 이름 생성
		public String makeMinuteName() {
			
			String[] arr=data.split("/|:");
			
			arr[1]=Common.ChangeMMFormat(arr[1]);
			arr[0]=arr[0].substring(1); 
			StringBuilder fileName=new StringBuilder();
			fileName.append(Main.PARSING_FILE_DIR);
			return fileName.append("minute/").append(arr[2]).append(arr[1]).append(arr[0]).append(arr[3])
					.append(arr[4]).append(".txt").toString();
		}
		public String makeHourName() {
			String[] arr=data.split("/|:");
			
			arr[1]=Common.ChangeMMFormat(arr[1]);
			arr[0]=arr[0].substring(1); 
			StringBuilder fileName=new StringBuilder();
			fileName.append(Main.PARSING_FILE_DIR);
			return fileName.append("hour/").append(arr[2]).append(arr[1]).append(arr[0]).append(arr[3])
					.append(".txt").toString();
		
		}
		public String makeDayName() {
			String[] arr=data.split("/|:");
			
			arr[1]=Common.ChangeMMFormat(arr[1]);
			arr[0]=arr[0].substring(1); 
			StringBuilder fileName=new StringBuilder();
			fileName.append(Main.PARSING_FILE_DIR);
			return fileName.append("day/").append("stat_").append(arr[2]).append("-").append(arr[1])
					.append("-").append(arr[0]).append(".txt").toString();
			
		}
}
