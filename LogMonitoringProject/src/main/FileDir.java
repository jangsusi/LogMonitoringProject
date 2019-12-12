package main;

import java.io.File;

public class FileDir {
	public void makeDir(String folderName) {
		(new File(folderName)).mkdirs();
	
	}
	
}
