package test;
 
import com.logmonitoring.main.UpdatedLogCollecting;

public class ThreadTest {

	public static void main(String[] args) {
		UpdatedLogCollecting thread = new UpdatedLogCollecting();
		thread.start();
	}

}
