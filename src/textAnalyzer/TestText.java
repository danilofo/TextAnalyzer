package textAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

import org.junit.Ignore;
import org.junit.Test;

public class TestText 
{
	@Test@Ignore
	public void readFolder()
	{			
		System.out.println("La lista ordinata contiene: ");
		
		Hashtable<String, Integer>  Text = TextUtility.readFolder("autosample/cucina");
		double average = 0;
		double total = 0; 
		for(Enumeration<String> e = Text.keys(); e.hasMoreElements();){
			total+=Text.get(e.nextElement());
		}
		
		
		ArrayList<Map.Entry<String, Integer>> sortedText = TextUtility.sortValue(Text);
		
		Hashtable <String,Double> normalizedText = new Hashtable<String, Double>();
		
		for(Enumeration<String> e = Text.keys(); e.hasMoreElements();){
			String oldKey = e.nextElement();
			
			normalizedText.put(oldKey, Text.get(oldKey)/total);
			
		}
		Analyzer.sampleAnalysis(normalizedText);
		StringBuffer bf = new StringBuffer();
		for(int i= 0; i<sortedText.size(); i++){
			bf.append(sortedText.get(i).getKey()+" "+sortedText.get(i).getValue().toString()+"\n");
			System.out.println(sortedText.get(i).getKey()+" "+sortedText.get(i).getValue().toString());
		}
		FileUtility.writeToFile(bf.toString(), "output.txt");
		
		
		for(Enumeration<String> e = normalizedText.keys(); e.hasMoreElements();){
			String key = e.nextElement();
			average+=normalizedText.get(key)/total;
			System.out.println(key+" "+normalizedText.get(key));
		}
		System.out.println("Average:"+average+" "+"Number of keys:"+normalizedText.size()+
				" "+"Total:"+total);
		
		StringBuffer nbf = new StringBuffer();
		ArrayList<Map.Entry<String, Double>> sortedNormalizedText =TextUtility.sortDoubleValue(normalizedText);
		for(int i= 0; i<sortedNormalizedText.size(); i++){
			nbf.append(sortedNormalizedText.get(i).getKey()+" "+sortedNormalizedText.get(i).getValue().toString()+"\n");
			System.out.println(sortedNormalizedText.get(i).getKey()+" "+sortedNormalizedText.get(i).getValue().toString());
		}
		FileUtility.writeToFile(nbf.toString(), "NormalizedOutput.txt");
		
	
	}
	
	@Test@Ignore
	public void testSampleMaker()
	{
		System.out.println("Starting protocol ... or whatever");
		
		//GoogleCustomSearch.createSample("biologia", " ");
		
		GoogleCustomSearch.createSample("cucina", " ");
		
		System.out.println("I'm fucking done here !");
	}
	
	
	

	
	@Test@Ignore
	public void testVSM(){
		Hashtable<String, Double> sample0 = Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/biologia")));
		Hashtable<String, Double> sample1= Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/politica")));
		Hashtable<String, Double> sample2 = Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/informatica")));
		Hashtable<String, Double> sample3 = Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/sport")));
		Hashtable<String, Double> sample4= Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/fisica")));
		
		ArrayList< Hashtable<String, Double> > samples = new ArrayList<Hashtable<String,Double>>();
		samples.add(sample0);
		samples.add(sample1);
		samples.add(sample2);
		samples.add(sample3);
		samples.add(sample4);
		
		Hashtable<String, Double> testing = Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("evaluation")));
		
		double[] results = Analyzer.VSMCategorize(samples, testing);
		for (int i=0; i<results.length; i++){
			System.out.println(new Double(results[i]).toString() );
		}
		
	}
	
	
	@Test@Ignore
	public void createSamples(){
		
		try {
			String topic = "FISICA";

			File f = new File(topic+".txt");		
			FileReader fr=new FileReader(f);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			int len =0;
			while ( lnr.readLine()!=null){continue;}
			len=lnr.getLineNumber();
			lnr.close();
			fr.close();
			
			FileReader fr2=new FileReader(f);
			BufferedReader br = new BufferedReader(fr2);
			
			
			String line;
			String[] listedURL = new String [len];
			
			int i =0;
			while ((line = br.readLine()) != null) {
				listedURL[i]=line;
				i++;
			}
			
			br.close();
			
			
			for(int j=0; j<listedURL.length; j++)
				{
					InputStream input;
				try {
					input = UrlReader.openURL(listedURL[j]);
					
				} catch (IOException e) {

					System.out.println(e.toString());
					System.out.println("Site access denied, skipping forward");
					continue; 
				}
					
				//lets convert it to text
				String text = new String();
				try {
					text = UrlReader.htmlToText(input);
				} catch (Exception e) {

					System.out.print("\n"+e.toString());
					System.out.print(": Site could not be converted to text, skipping forward\n\r");
					continue;
				}
					
					//time to write it bitch ! (name format: politica_18.txt)
					String nameID1 = "";
					Integer nameID2 = j;
					String filename = new String();
					if(j<10) nameID1="0";
					filename += "evaluation/"+topic+"/" + topic + "_" + nameID1+ nameID2.toString() + ".txt";
					
					//just do it !
					FileUtility.writeToFile(text, filename);
			
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test@Ignore
	public void testVSM_final()
	{
			Hashtable<String, Double> sample0 = Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/biologia")));
			Hashtable<String, Double> sample1= Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/politica")));
			Hashtable<String, Double> sample2 = Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/informatica")));
			Hashtable<String, Double> sample3 = Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/sport")));
			Hashtable<String, Double> sample4= Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/fisica")));
			
			ArrayList< Hashtable<String, Double> > samples = new ArrayList<Hashtable<String,Double>>();
			samples.add(sample0);
			samples.add(sample1);
			samples.add(sample2);
			samples.add(sample3);
			samples.add(sample4);
			
			Hashtable <Integer, String> categorie = new Hashtable <Integer, String>();
			categorie.put(0,"BIOLOGIA");
			categorie.put(1,"POLITICA");
			categorie.put(2,"TECH");
			categorie.put(3,"SPORT");
			categorie.put(4,"FISICA");
			
			
			for(int l=0; l<samples.size();l++){
				
				String foldername = categorie.get(l);
				
				File folder = new File("evaluation/"+foldername);
				double efficacy[] = {0,0,0,0,0};
				
				if(!folder.isDirectory()){
					System.out.println("the "+foldername+" is not a folder");
				} //TODO add more "error code"
				
				File[] listFiles = folder.listFiles();
				StringBuffer sb = new StringBuffer();
				
				
				for (int i = 0; i < listFiles.length; i++) {
					
					//System.out.println(listFiles[i].getName());
					if(listFiles[i].isFile()){
						
						sb.append(FileUtility.readFile(foldername+"/"+listFiles[i].getName()));
						Hashtable<String, Double> testing = TextUtility.normalize(TextUtility.evaluateFrequency(sb.toString() ) );
						double[] results=Analyzer.VSMCategorize(samples, testing);
						
						double maximum = results[0];
						int max_index=0;
						for (int k=0; k<results.length; k++){
							if (maximum<results[k]){
								maximum=results[k];
								max_index=k;
							}
						}
						efficacy[max_index]++;
					}
					sb.delete(0, sb.length());
				}
				
				System.out.println("Campione in:"+" "+"evaluation/"+foldername+"/");
				
				for(int m=0; m<efficacy.length; m++){
					
					efficacy[m]=efficacy[m]/listFiles.length*100;
					System.out.println(categorie.get(m)+":  "+efficacy[m]+"%");	
				}
				System.out.println(" ");
			}
	}
	
	
	@Test
	public void testCloudGenerator(){
		final Hashtable<String, Double> sample = Analyzer.sampleAnalysis(TextUtility.normalize(
				TextUtility.readFolder("newSamples/fisica")));

		
			SwingUtilities.invokeLater(new Runnable() {
			 @Override
			 public void run() {
			 new CloudGenerator().generate(sample);
			 }
			 });
		
	}
	
	
}
