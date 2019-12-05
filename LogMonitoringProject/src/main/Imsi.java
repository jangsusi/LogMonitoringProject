package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class Imsi{
	
	
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

   // SFTP 서버연결 
	public void init(){
		String url = "172.22.1.66";
		String user = "jsb568";
		String password = "1234";
		//JSch 객체 생성
        JSch jsch = new JSch();
        try {
        	//세션객체 생성 ( user , host, port ) 	
            session = jsch.getSession(user, url,22);
            
            //password 설정
            session.setPassword(password); 
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);    
            session.connect();          
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println(channel.getId());
           
        } catch (JSchException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
        channelSftp = (ChannelSftp) channel;
        
	}

	// 단일 파일 업로드 
	public void upload(InputStream in) throws IOException{
		
		
		try{ //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put 
			OutputStream fos=new FileOutputStream("C:/Users/user/Desktop/a/a.txt");
			byte[] buffer = new byte[1024 * 8];
			
	      while(true) {
	                int count = in.read(buffer);  
	                if(count == -1) {
	                      System.out.println("x");
	                      break;
	                }
	                
	                String str=buffer.toString();
	                fos.write(buffer, 0, count );
	      }
	      
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		}finally{
			try{
				in.close();
			} catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}

	// 단일 파일 다운로드 
	public void download() throws IOException{
		InputStream in = null;
		String path = "/opt/TerraceTims/log/catalina/webmail";
		try{ //경로탐색후 inputStream에 데이터를 넣음
			channelSftp.cd(path);
			in = channelSftp.get("access_2019-11-07.txt");
			System.out.println(in);
		}catch(SftpException se){
			System.out.println(se.getMessage());
			se.printStackTrace();
		}
		upload(in);
	}

	// 파일서버와 세션 종료
	public void disconnect(){
		channelSftp.quit();
		session.disconnect();
	}
}

