package main;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			LoadLog loadLog=new LoadLog();
			loadLog.FirstLoad();
			int count=0;
			/*while(count!=3) {
				TenThread tenThread=new TenThread();
				tenThread.start();
				try {
					tenThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				loadLog.ContinuousLoad();
				count++;
			}*/
			
	}
}
