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
			System.out.println(i+1+"��° ���� �α׵����� �м� �Ϸ�.\n");
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
		
		//���� �� �� �� �б�
		//�α׵����� �˻��ϱ� ��������
		//�α׵����� �ð��� ���ؽð��� ��ġ�ϴ��� �˻�
		//��ġ�ϸ� �α׵����� map�� ǲ.equals="��ġ"
		//��ġ���� ������ ������� ������ map ���Ͽ� ���� ==����ġ
		//	������� ������ map ���� map�� ����
		//	������� ����
		File file=loadFiles.readFile();
		String logLine=loadFiles.readLine();
		FileCreating4,FileSearching,FileReading,FileWriting,FileDeleting,/LogParsing(LogSplit),LogPutMap/
		MapCreating,MapAddingFactor,MapDeleting/ServerConnecting,ServerDisConnecting/
		����(����,ã��,�а�,����,����), �α׵�����(�Ľ�,�ʿ� ǲ), ����(����,�����߰�,����),����(����,����),
		����ü : ���������̸�(��,��,��,����)
		
		
		���� : ���� �̸�����  createFile
		ã�� : ���� �������,���� �̸�����,  searchFilesByDir,searchFileByName
		�а� : ó������(ó������),Ư����ġ����(�ι�°������� �ش糯¥����) readFromFirstPlace,readFromMiddlePlace,
		���� : ó������ ����(�ʴ�) writeAllMapEntry
		���� : �ð�����,�д��� remain24Files, remain60Files
		
		�Ľ� : ���������, /:������ parseByBlank(splitByBlank), parseBySlashColon(splitBySlashColon)
		�ʿ� ǲ : ���� putLogData(putLine)
		
		���� : ���� createMap
		�����߰� : �ϳ��� �ʿ� ����, (��,��)���� �ʿ� ���� putKey, putMap, 
		���� : �ٻ��� removeMap
		
		
		MaptoFileContext mapContext=new MaptoFileContext();
		ParsingData logData=new Parsingdata(logLine);
		
			isIn
			change�ð�����
		���File
			���� �̸�
			����
			
		lastFile
			���� �̸�
			����
			���� ��ġ ã��
			������ ���� ��ġ ����
			
		for(���� ����-1)
		for(int i=0;i<�α� ���� ��;i++)
			�Ľ̵�����(�α�)
		if �ð�����==���� �α� �ð��κ�
		if isChangeTime
		if minuteMem.equals(logDataTime)
			minuteMap.add(logDataTime);
		else �ð��� �ٲ�����
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
		
		//������Ʈ�ÿ�
		fdown.downloadLastFile();
		//���̰� �þ�� Ȯ���̵ɶ� �޾ƿ��� 
		
		
		//SaveLog saveLog=new SaveInitialLog();
		
		 */
			int count=1;
			while(count!=3) {
				try {
					
					//Thread.sleep(8000);
					System.out.println("������Ʈ");
					new TenThread().start();
					//new ConnectServer("172.22.1.66","jsb568","1234");
					count++;
				} catch(ThreadDeath e) {
					FileWriter fileWriter=new FileWriter(new File(Main.ACCESS_FILE_DIR+"abc.txt"));
					fileWriter.write("df");
					fileWriter.close();
					System.out.println("����������");
				}
				
				
			}
			
	}
}
