package main;

import java.io.File;

public class FileList {
	String fileDir;
	
	FileList(String fileDir){
		this.fileDir=fileDir;
	}
	
	public void createFileDir() {
		(new File(fileDir)).mkdirs();
	}
	public File createFile(String fileName) {
		return (new File(fileDir+fileName));
	}
	public void deleteFiles() {
		File[] fileList=new File(fileDir).listFiles();
		int deleteLen=0;
		if(fileDir.contains("minute"))
			deleteLen=fileList.length-60;
		else if(fileDir.contains("hour")) 		
			deleteLen=fileList.length-60;	
		for(int i=0;i<deleteLen;i++)
			fileList[i].delete();
	}
	public File findLastFile() {
		File[] fileList=new File(fileDir).listFiles();
		return fileList[fileList.length-1];	
	}
	public File[] getFiles() {
		
	}
}
