package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class FileDownload {
	private final String ACCESS_FILE_DIR="C:/Users/user/Desktop/access_log/";
	
	ChannelSftp  channelSftp;
	Vector<ChannelSftp.LsEntry> fileList;
	
	FileDownload(ChannelSftp  channelSftp) {
		this.channelSftp=channelSftp;
	}
	
	public void searchFiles(String serverPath) {
		try {
		    channelSftp.cd(serverPath);
		    fileList=channelSftp.ls("*.txt");
		    ascSorting();
		}catch (SftpException e) {
			e.printStackTrace();
		}
	}
	
	public void ascSorting() {
		Collections.sort(fileList);
	}
	
	public void downloadFilesToLocal(){
		try {
			for(ChannelSftp.LsEntry entry :fileList) {
				InputStream in = channelSftp.get(entry.getFilename());
				File file=new File(ACCESS_FILE_DIR+entry.getFilename());
				FileOutputStream fos=new FileOutputStream(file);
				int data;
				byte[] buffer=new byte[1024*8];
				while((data=in.read(buffer,0,1024*8))!=-1) {
					fos.write(buffer,0,data);
				}
				fos.close();
				
			}	
		}catch (SftpException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	public void downloadLastFile(){
		try {
				String fileName=fileList.lastElement().getFilename();
				InputStream in = channelSftp.get(fileName);
				File file=new File(Main.PARSING_FILE_DIR+fileName);
				FileOutputStream fos=new FileOutputStream(file);
				int data;
				byte[] buffer=new byte[1024*8];
				while((data=in.read(buffer,0,1024*8))!=-1) {
					fos.write(buffer,0,data);
				
				fos.close();
			}	
		}catch (SftpException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	public int size() {
		return fileList.size();
	}
	public File getFile(int pos) {
		File file=new File(fileList.get(pos).getFilename());
		return file;
	}
}
