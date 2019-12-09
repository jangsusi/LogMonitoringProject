package main;

public class TenThread extends Thread {
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("시도");
			SaveLog saveLog=new SaveUpdatedLog();
			
		}catch(InterruptedException e) {
			System.out.println("읽을 파일 없음.");
		}
	}
}
