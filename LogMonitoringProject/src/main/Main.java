package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;

public class Main {

	public static final String PARSING_FILE_DIR="C:/Users/user/Desktop/monitoring_data/";
	public static final String ACCESS_FILE_DIR="C:/Users/user/Desktop/access_log/";
	public static final String SERVER_DIR="/opt/TerraceTims/log/catalina/webmail";

	public static void main(String[] args) throws IOException {
		
		ConnectServer connectServer=new ConnectServer("172.22.1.66","jsb568","1234");
		ChannelSftp channelSftp=connectServer.connect();
		
		FileDownload accessList=new FileDownload(channelSftp);
		accessList.searchFiles(SERVER_DIR);
		accessList.downloadFilesToLocal();
		
		for(int i=0;i<accessList.size();i++) {
			LogDataListing file=new LogDataListing(accessList.getFile(i));
			file.getLogList();
			System.out.println(i+1+"번째 파일 로그데이터 분석 완료.\n");
		}
		
		
		
		/*FileList minuteList=new FileList(PARSING_FILE_DIR+"minute");
		minuteList.createFileDir();
		FileList hourList=new FileList(PARSING_FILE_DIR+"hour");
		hourList.createFileDir();
		FileList dayList=new FileList(PARSING_FILE_DIR+"day");
		dayList.createFileDir();
		FileList accessList=new FileList(ACCESS_FILE_DIR+"accessLog");
		accessList.createFileDir();
		
		FileDir fileDir=new FileDir();
		fileDir.makeDir(PARSING_FILE_DIR+"minute");
		fileDir.makeDir(PARSING_FILE_DIR+"hour");
		fileDir.makeDir(PARSING_FILE_DIR+"day");
		fileDir.makeDir(ACCESS_FILE_DIR);
		
		
		LoadFile loadFiles=new LoadFile(downloadPath);
		
		//파일 한 줄 씩 읽기
		//로그데이터 검사하기 정상인지
		//로그데이터 시간이 기준시간과 일치하는지 검사
		//일치하면 로그데이터 map에 풋.equals="일치"
		//일치하지 않으면 현재까지 저장한 map 파일에 저장 ==불일치
		//	현재까지 저장한 map 윗단 map에 저장
		//	멤버변수 변경
		File file=loadFiles.readFile();
		String logLine=loadFiles.readLine();
		FileCreating4,FileSearching,FileReading,FileWriting,FileDeleting,/LogParsing(LogSplit),LogPutMap/
		MapCreating,MapAddingFactor,MapDeleting/ServerConnecting,ServerDisConnecting/
		파일(생성,찾기,읽고,쓰고,삭제), 로그데이터(파싱,맵에 풋), 담김맵(생성,원소추가,삭제),서버(연결,끊기),
		구조체 : 현재파일이름(시,분,일,연도)
		
		
		생성 : 파일 이름으로  createFile
		찾기 : 파일 목록으로,파일 이름으로,  searchFilesByDir,searchFileByName
		읽고 : 처음부터(처음파일),특정위치부터(두번째실행부터 해당날짜파일) readFromFirstPlace,readFromMiddlePlace,
		쓰고 : 처음부터 쓰기(맵다) writeAllMapEntry
		삭제 : 시간단위,분단위 remain24Files, remain60Files
		
		파싱 : 공백단위로, /:단위로 parseByBlank(splitByBlank), parseBySlashColon(splitBySlashColon)
		맵에 풋 : 저장 putLogData(putLine)
		
		생성 : 생성 createMap
		원소추가 : 하나씩 맵에 저장, (시,일)맵을 맵에 저장 putKey, putMap, 
		삭제 : 다삭제 removeMap
		
		
		MaptoFileContext mapContext=new MaptoFileContext();
		ParsingData logData=new Parsingdata(logLine);
		
			isIn
			change시간기준
		통계File
			파일 이름
			쓰기
			
		lastFile
			파일 이름
			쓰기
			읽을 위치 찾기
			다음에 읽을 위치 저장
			
		for(파일 갯수-1)
		for(int i=0;i<로그 라인 수;i++)
			파싱데이터(로그)
		if 시간기준==현재 로그 시간부분
		if isChangeTime
		if minuteMem.equals(logDataTime)
			minuteMap.add(logDataTime);
		else 시간이 바꼈을때
			DataFile file=new DataFile(minuteMem);
			file.write(minuteMap);
			
			minuteMap.copyTo(hourMap);
			minuteMap.delete();
			minuteMem.update(logData);
		
		
			
		TimeMap minuteMap=new MinuteMap();
		TimeMap hourMap=new HourMap();
		MemberVary minuteMem=new MemberVary(logLine,minuteMap);
		MemberVary hourMem=new MemberVary(logLine,hourMap);
		
		//fileNameRawData
		FileNameRawData rawData=new FileNameRawData(logLine);
		MemberVary minuteFile=new MinuteVary(rawData);
		FileName fileNameList=new FileName(logLine);
		
		//업데이트시에
		fdown.downloadLastFile();
		//길이가 늘어난게 확인이될때 받아오기 
		
		
		//SaveLog saveLog=new SaveInitialLog();
		
		 */
			int count=1;
			while(count!=3) {
				try {
					
					//Thread.sleep(8000);
					System.out.println("업데이트");
					new TenThread().start();
					//new ConnectServer("172.22.1.66","jsb568","1234");
					count++;
				} catch(ThreadDeath e) {
					FileWriter fileWriter=new FileWriter(new File(Main.ACCESS_FILE_DIR+"abc.txt"));
					fileWriter.write("df");
					fileWriter.close();
					System.out.println("쓰레드종료");
				}
				
				
			}
			
	}
}
