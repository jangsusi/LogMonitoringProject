package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePointer {
	public void remeberLastPointer(Long filePointer,String filePath,String context) {
		try {
			FileWriter fileWriter=new FileWriter(new File(filePath));

			//FileWriter fileWriter=new FileWriter(new File(PARSING_FILE_DIR+"last_place.txt"));
			
			fileWriter.write(context);

			//fileWriter.write(filePointer+"\n"+minuteFileName+"\n"+hourFileName);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
