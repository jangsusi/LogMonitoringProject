package main;

public class TenThread extends Thread {
	public void run() {
		try {
			Thread.sleep(10);
			
		}catch(InterruptedException e) {
			System.out.println("���� ���� ����.");
		}
	}
}
