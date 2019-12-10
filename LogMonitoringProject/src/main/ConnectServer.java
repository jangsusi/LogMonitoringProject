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

	String url = "172.22.1.66";
	String user = "jsb568";
	String password = "1234";
	public ConnectServer(String url,String user,String password,int isFirst) throws IOException {
		this.url=url;
		this.user=user;
		this.password=password;
		
		Init(isFirst);
	}
	//최초실행시 로드
	public void Init(int isFirst) throws IOException{
	
		(new File(SaveLog.PARSING_FILE_DIR+"minute")).mkdirs();
		(new File(SaveLog.PARSING_FILE_DIR+"hour")).mkdirs();
		(new File(SaveLog.PARSING_FILE_DIR+"day")).mkdirs();
		(new File(Main.LOCAL_ACCESS_FILE_DIR)).mkdir();
		
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
	    String serverPath="/opt/TerraceTims/log/catalina/webmail";
	    try {
			channelSftp.cd(serverPath);
			if(isFirst==Main.FIRST) {
				Vector<ChannelSftp.LsEntry> fileList=channelSftp.ls("*.txt");
				Collections.sort(fileList);
				for(ChannelSftp.LsEntry entry :fileList) {
					WriteFile(entry.getFilename());
				}
			}
			else if(isFirst==Main.UPDATE) {		
				WriteFile("access_"+Common.getTime("yyyy-MM-dd")+".txt");
			}	
			channelSftp.exit();
			channel.disconnect();
			session.disconnect();
		} catch (SftpException e) {
			e.printStackTrace();
		}

	    if(isFirst==Main.FIRST)
	    	new SaveInitialLog();
	    else if(isFirst==Main.UPDATE)
	    	new SaveUpdatedLog();
	}

	public void WriteFile(String fileName){
		try {
			InputStream in = channelSftp.get(fileName);
			File file=new File(Main.LOCAL_ACCESS_FILE_DIR+fileName);
			FileOutputStream fos=new FileOutputStream(file);
			int data;
			byte[] buffer=new byte[1024*8];
			while((data=in.read(buffer,0,1024*8))!=-1) {
				fos.write(buffer,0,data);
			}
			fos.close();
		} catch (SftpException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	} 


}