package textAnalyzer;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import com.sun.istack.internal.SAXParseException2;

public class Analyzer
{
	public static Hashtable<String, Double> removeStopWords(Hashtable<String, Double> text ){
		
		Hashtable<String, Integer> stopwords = TextUtility.readFolder("stopword");
		
		for(Enumeration<String> e = stopwords.keys() ; e.hasMoreElements() ;){
			String word=e.nextElement();
			if (text.containsKey(word)){
				text.remove(word);
			}
		}
		
		return text;
	}	
	
	public static Hashtable<String, Double> sampleAnalysis(Hashtable<String, Double> sample){
		final double  LIM_INF = 0.0001;
		
		sample=removeStopWords(sample);
		
		for( Enumeration<String> e=sample.keys(); e.hasMoreElements(); ){
			String key = e.nextElement();
			if( sample.get(key)< LIM_INF  ){
				sample.remove(key);
			}
		}	
		
		return sample;
	}
	
	public static double VSdistance(Hashtable<String, Double> sample,Hashtable<String, Double> testing ){
		double sampleMod = 0;
		double testMod = 0;
		double result =0;
		
		for (Enumeration< String> e=sample.keys(); e.hasMoreElements(); ){
			String word=e.nextElement();
			double a = sample.get(word);
			double b=0;
			if (testing.containsKey(word) ) {
				b=testing.get(word);
			}
			
			result +=a*b;
		}
		
		for (Enumeration< String> e=sample.keys(); e.hasMoreElements(); ){
			String word=e.nextElement();
			sampleMod+= sample.get(word)*sample.get(word);
		}
		for (Enumeration< String> e=testing.keys(); e.hasMoreElements(); ){
			String word=e.nextElement();
			testMod+= testing.get(word)*testing.get(word);
		}
		
		result = result /(Math.sqrt(sampleMod*testMod));
		return result;
	}
	
	public static double[] VSMCategorize(ArrayList < Hashtable<String, Double>> samples,Hashtable<String, Double> testing){
		double[] results = new double[samples.size()];
		
		for (int i=0; i<results.length; i++){
			results[i]=VSdistance(samples.get(i) , testing);
		}

		return results;
	}
}
	


