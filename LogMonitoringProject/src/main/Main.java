package main;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

	public static final String LOCAL_ACCESS_FILE_DIR="C:/Users/user/Desktop/accessLog/";
	public static void main(String[] args) throws IOException {
		
			//ConnectServer loadLog=new ConnectServer("172.22.1.66","jsb568","1234",FIRST);
		
		SaveLog saveLog=new SaveInitialLog();
		
		 
			/*int count=0;
			while(count!=3) {
				try {
					Thread.sleep(400);
					System.out.println("½Ãµµ");
					count++;
					SaveLog saveLog1=new SaveUpdatedLog(null);
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}*/
			
	}
}
