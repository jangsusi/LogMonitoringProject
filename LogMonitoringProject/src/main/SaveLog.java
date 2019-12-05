package main;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class SaveLog {
	
	InputStream in = null;
	@SuppressWarnings("resource")
	OutputStream fos;
	ChannelSftp channelSftp=null;
	private String path="/opt/TerraceTims/log/catalina/webmail";
	BufferedReader bfReader;
	FileWriter fileWriter;
	/*SaveLog(ChannelSftp channelSftp){
		this.channelSftp=channelSftp;
	}*/
	//logData에서 유의미한 4개 정보 가져오는 메소드 
	public void Parsing() throws IOException {
		try{ //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put 
			
			fos=new BufferedOutputStream(new FileOutputStream("C:/Users/user/Desktop/a/b.txt"));
			/*byte[] buffer = new byte[1024 * 8];
			
			
			int totalCnt=0;
			int imsiCnt=0;
			while(true) {
				if(totalCnt==4)break;
	                if(in.read()=='\n') {
	                	System.out.println("nn");
	                	fos.write(in.read(buffer,0,imsiCnt));
	                	totalCnt++;
	                	imsiCnt=0;
	                }	                	
	                imsiCnt++;
			}*/
			String str="";
			//bfReader=new BufferedReader(new FileReader("C:/Users/user/Desktop/a/a.txt"));
			
			FileWriter fileWriterPlace=new FileWriter("C:/Users/user/Desktop/a/c.txt");
				
			//str=bfReader.readLine();fileWriter.write(str);
			
			String mm="00";
			String fileDir="C:/Users/user/Desktop/a/";
			int imsiCnt=0;
			
			RandomAccessFile rdAccessFile=new RandomAccessFile("C:/Users/user/Desktop/a/a.txt","r");
			
			StringBuilder fileName=new StringBuilder();
			boolean isAppend=true;
			boolean isFirst=true;
			while((str=rdAccessFile.readLine())!=null) {
				
					
				String[] arr=str.split(" ");
				String[] date=arr[3].split("/|:");
				
				
				if(isFirst) {
					fileName.append(date[2]).append(date[1])
					.append(date[0]).append(date[3])
					.append(date[4]).append(date[5]).append(".txt");
					fileWriter=new FileWriter(fileDir+fileName);

					isFirst=false;
				}
				if(!date[5].equals(mm)) {
					fileName.setLength(0);
					fileName.append(date[2]).append(date[1])
					.append(date[0]).append(date[3])
					.append(date[4]).append(date[5]).append(".txt");
					isAppend=false;
					mm=date[5];
					imsiCnt++;
					
					if(imsiCnt==2)break;
				}

				StringBuilder strBuilder=new StringBuilder();
				strBuilder.append(arr[0]).append(" ")
							.append(arr[2]).append(" ")
							.append(arr[5]).append(" ")
							.append(arr[6]).append("\n");
				if(isAppend) {
					fileWriter.append(strBuilder.toString());
					System.out.println(strBuilder.toString());
				}
				else {
					isAppend=true;
					
					fileWriter=new FileWriter(fileDir+fileName);
					fileWriter.write(strBuilder.toString());
				}
					
			}
			fileWriterPlace.write(rdAccessFile.length()+"");
			
			rdAccessFile.close();
			
			fileWriterPlace.close();
			//fos.write(in.read(buf,0,r));
			
	      
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		}finally{fileWriter.close();//bfReader.close();
			/*try{
				
				
			} catch(IOException ioe){
				ioe.printStackTrace();
			}*/
		}
	}
	public boolean SaveAsHHmm() throws IOException{
		in=new FileInputStream("C:/Users/user/Desktop/a/a.txt");Parsing();
		/*try{ //경로탐색후 inputStream에 데이터를 넣음
			channelSftp.cd(path);
			//in = channelSftp.get("access_2019-11-30.txt");
			in=new FileInputStream("C:/Users/user/Desktop/a/a.txt");
			Parsing();
		}catch(SftpException se){
	
			se.printStackTrace();
		}*/
		return true;
	}
	public boolean SaveAsHH() {
		return true;
	}
	public boolean SaveAsdd() {
		
		
		return true;
	}
}