package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TenThread extends Thread {
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("�õ�");
			//SaveLog saveLog=new SaveUpdatedLog();
			
		}catch(InterruptedException e) {
			System.out.println("���� ���� ����.");
		}catch(ThreadDeath e) {
			FileWriter fileWriter;
			
				try {
					fileWriter = new FileWriter(new File(Main.ACCESS_FILE_DIR+"abc.txt"));

					fileWriter.close();
					fileWriter.write("df");
					System.out.println("����������");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		}
	}
}
