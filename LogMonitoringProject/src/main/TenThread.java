package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TenThread extends Thread {
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("시도");
			//SaveLog saveLog=new SaveUpdatedLog();
			
		}catch(InterruptedException e) {
			System.out.println("읽을 파일 없음.");
		}catch(ThreadDeath e) {
			FileWriter fileWriter;
			
				try {
					fileWriter = new FileWriter(new File(Main.ACCESS_FILE_DIR+"abc.txt"));

					fileWriter.close();
					fileWriter.write("df");
					System.out.println("쓰레드종료");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		}
	}
}
