package main;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//���� ����� �α����� ��ü �޾ƿ��� �޼ҵ�
public class LoadLog {

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	
	//���ʽ���� �ε�
	public void FirstLoad() throws IOException{
		String url = "172.22.1.66";
		String user = "jsb568";
		String password = "1234";
	
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
	    
	    /*SaveLog saveLog=new SaveLog(channelSftp);
	    boolean isSavemm=saveLog.SaveAsHHmm();
	    boolean isSaveHH=saveLog.SaveAsHH();
	    boolean isSavedd=saveLog.SaveAsdd();
	    System.out.println(
	    		"�д��� �Է� : "+isSavemm+
	    		" �ô��� �Է� : "+isSaveHH+
	    		" �ϴ����Է� : "+isSavedd);
	    */
	}
	//����� �ε�
	public void ContinuousLoad() {
		DeleteLog deleteLog=new DeleteLog();
		deleteLog.DeleteMinute();
		deleteLog.DeleteHour();
	} 

}