package main;

public class TenThread extends Thread {
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("�õ�");
			SaveLog saveLog=new SaveUpdatedLog();
			
		}catch(InterruptedException e) {
			System.out.println("���� ���� ����.");
		}
	}
}
