package test;

import java.io.IOException;

import com.jcraft.jsch.SftpException;
import com.logmonitoring.main.ServerFileStorage;

public class ConnectionTest {
	private static final String url="172.22.1.66";
	private static final String id="jsb568";
	private static final String password="1234";
	
	public static void main(String[] args) throws IOException, SftpException {
		ServerFileStorage serverStorage=new ServerFileStorage(url,id,password);
		serverStorage.connectServer();
//		serverStorage.downloadFilesToLocal();
	}
}
