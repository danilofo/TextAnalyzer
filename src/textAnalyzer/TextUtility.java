package textAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.jfree.text.TextUtilities;

public class TextUtility {
	
	public static final int SIZE=512;
	
	public static Hashtable <String,Double> normalize(Hashtable <String,Integer> Text ){
	
		Hashtable <String,Double> normalizedText = new Hashtable<String, Double>();
		double total = 0; 
		for(Enumeration<String> e = Text.keys(); e.hasMoreElements();){
			total+=Text.get(e.nextElement());
		}
		for(Enumeration<String> e = Text.keys(); e.hasMoreElements();){
			String oldKey = e.nextElement();
			normalizedText.put(oldKey, Text.get(oldKey)/total);
		}
		return normalizedText;
	}
	public static String deleteNonAlpha(String string){
		char[] chars = string.toCharArray();
		
		char[] chars2 = new char[chars.length];
		
		for (int i=0; i<chars.length; i++)
		{
			if(!Character.isLetter(chars[i])){chars2[i]=' ';}
			else {chars2[i]=chars[i];}
		}
		String result = new String(chars2);
		
		return result;
	}
	
	
	public static Hashtable<String, Integer> evaluateFrequency(String text){
		
		//Removal of . , 
		text = text.toLowerCase(); //convert to lower case 
		text = deleteNonAlpha(text);
		
		
		Hashtable<String, Integer> frequencies = new Hashtable<String, Integer>();
		
		StringTokenizer tokenizer = new StringTokenizer(text);
		String token;
		
		while(tokenizer.hasMoreElements())
		{
			token = tokenizer.nextToken();
			
			if (token.length()>20) continue;
			if(frequencies.get(token)!=null)
			{
				frequencies.put(token, frequencies.get(token)+1);
			}
			else
			{
				frequencies.put(token, 1);
			}
		}
		
		return frequencies;
	}
	
	public static  ArrayList<Map.Entry<String, Double>> sortDoubleValue(Hashtable<String, Double> table)
	{
		ArrayList<Map.Entry<String, Double>> list = new ArrayList(table.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> arg0,
					Entry<String, Double> arg1) {
				// TODO Auto-generated method stub
				return arg0.getValue().compareTo(arg1.getValue());
			}
		});
		return list;
	}
	
	public static  ArrayList<Map.Entry<String, Integer>> sortValue(Hashtable<String, Integer> table)
	{
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList(table.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> arg0,
					Entry<String, Integer> arg1) {
				// TODO Auto-generated method stub
				return arg0.getValue().compareTo(arg1.getValue());
			}
		});
		return list;
	}

	public static Hashtable<String, Integer> readFolder(String foldername)
	{
		File folder = new File(foldername);
		StringBuffer sb = new StringBuffer();
		
		if(!folder.isDirectory()){
			System.out.println("the "+foldername+" is not a folder");
		} //TODO add more "error code"
		
		File[] listFiles = folder.listFiles();
		
		for (int i = 0; i < listFiles.length; i++) {
			
			//System.out.println(listFiles[i].getName());
			if(listFiles[i].isFile()){
				sb.append(FileUtility.readFile(foldername+"/"+listFiles[i].getName()));
			}
		}
		
		Hashtable<String, Integer> frequency = evaluateFrequency(sb.toString());
		
		return frequency;
	}
}
