package textAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {
	public static final int BUFFERSIZE=256;
	
public static String readFile(String fileName){
		
		StringBuffer buffer = new StringBuffer();
		File file = new File(fileName);
		byte[] array=new byte[BUFFERSIZE];
		FileInputStream fis;
		int nByteLetti=0;
		
		try {
			 fis = new FileInputStream(file);
			 while((nByteLetti = fis.read(array,0,BUFFERSIZE))!=-1){
			 
				 buffer.append( new String(array,0,nByteLetti) );
			 }
			 
			 fis.close();
			 
		} catch (Exception e) {
			
			buffer.append("exception in readFile ");
			buffer.append(e.getMessage());
			buffer.append("\n\r");
		}
		
		
		return buffer.toString();
	}
	
	public static void writeToFile(String text, String filename){
		File file = new File(filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(text.getBytes() ) ;
			
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
