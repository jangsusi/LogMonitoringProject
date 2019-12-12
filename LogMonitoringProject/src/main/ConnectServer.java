package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

//최초 실행시 로그파일 전체 받아오는 메소드
public class ConnectServer {
	
	
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	String url;
	String user;
	String password;
	public ConnectServer(String url,String user,String password) throws IOException {
		this.url=url;
		this.user=user;
		this.password=password;
		
		
	}
	
	public ChannelSftp connect() throws IOException{
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
	    return channelSftp;
	}


}