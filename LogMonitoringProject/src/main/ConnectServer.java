package main;
import java.io.File;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//���� ����� �α����� ��ü �޾ƿ��� �޼ҵ�
public class ConnectServer {
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	String url = "172.22.1.66";
	String user = "jsb568";
	String password = "1234";
	public ConnectServer(String url,String user,String password) throws IOException {
		this.url=url;
		this.user=user;
		this.password=password;
		
		Init();
	}
	//���ʽ���� �ε�
	public void Init() throws IOException{
	
		(new File("C:/Users/user/Desktop/monitoring_data/minute")).mkdirs();
		(new File("C:/Users/user/Desktop/monitoring_data/hour")).mkdirs();
		(new File("C:/Users/user/Desktop/monitoring_data/day")).mkdirs();

		
	    JSch jsch = new JSch();
	    try {
	    
	        session = jsch.getSession(user, url,22);
	        session.setPassword(password); 
	        java.util.Properties config = new java.util.Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);    
	        session.connect();          
	        channel = session.openChannel("sftp");
	        channel.connect();
	       
	    } catch (JSchException e) {
	        e.printStackTrace();
	    }
	    channelSftp = (ChannelSftp) channel;
  
	    //SaveLog saveLog=new SaveLog(channelSftp);
	    SaveLog saveLog=new SaveLog(null);
	}
	//����� �ε�
	public void ContinuousLoad() {
		DeleteLog deleteLog=new DeleteLog();
		deleteLog.DeleteMinute();
		deleteLog.DeleteHour();
	} 


}