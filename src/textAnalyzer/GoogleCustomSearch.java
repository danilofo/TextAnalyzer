package textAnalyzer;

import googlesearch.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GoogleCustomSearch {
	
	final static String apiKey = "myapikey";
	final static String customSearchEngineKey = "myenginekey";
	final static  String searchURL = "https://www.googleapis.com/customsearch/v1?";
	 
	public static String makeSearchString(String qSearch, Integer start, Integer num)
	{
	    String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey+"&q=";
	     
	    //replace spaces in the search query with +
	    String keys[]=qSearch.split("[ ]+");
	    for(String key:keys)
	    {  
	        toSearch += key +"+"; //append the keywords to the url
	    }       

	    //specify response format as json
	    toSearch+="&alt=json";
	        
	    //specify starting result number
	    toSearch+="&start="+start.toString();
	     
	    //specify the number of results you need from the starting position
	    toSearch+="&num="+num.toString();       
	     
	    return toSearch;
	}
	
	
   	public static String[] urlList(String json){
        //Get a stream for the json response       
   		
   		StringReader reader=new StringReader(json);
        Gson gson=new GsonBuilder().create();
            
        //Get the root object for the response
        googlesearch.GoogleSearch jsonObject = gson.fromJson(reader,googlesearch.GoogleSearch.class);
        List<Item> site = jsonObject.getItems();
        String[] output = new String[site.size()];
        
        for(int i=0; i<site.size();i++)
        {
        	output[i]=site.get(i).getLink().toString(); 
        }
        
        return output;
   	
   	}
   	
   	
   	public static void createSample(String mainTarget, String secondaryParams){
   		
   		System.out.println("Making "+mainTarget+" topic");
   		
   		//lets search for a particular topic
   		String querry = new String();
   		querry += "wikipedia"+ " " + mainTarget + " " + secondaryParams+ " " + "-facebook -.pdf -.doc -.ppt -answer";
   		
   		//Strings we'll need later
   		String googleUrl = new String();
   		String json = new String();
   		String[] listedURL;
   		
   		//lets iterate to get more than 10 articles
   		for(int i=0; i<10; i++)
   		{
   			googleUrl = GoogleCustomSearch.makeSearchString(querry, i*10+1, 10); //starts from 0 (first article)
   			
   			//lets open it JSON style !
   			json = UrlReader.readJSON(googleUrl);
   			
   			//lets get the list of sites !
   			listedURL = GoogleCustomSearch.urlList(json);
   			
   			//lets f*cking open them !
   			for(int j=0; j<listedURL.length; j++)
   			{
   				InputStream input;
				try {
					input = UrlReader.openURL(listedURL[j]);
	   				
				} catch (IOException e) {
	
					System.out.println(e.toString());
					System.out.println("Site access denied, skipping forward");
					continue; //lets try to continue if sites deny access
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
   				Integer nameID1 = i;
   				Integer nameID2 = j;
   				String filename = new String();
   				filename += "autosample/" + mainTarget + "_" + nameID1.toString() + nameID2.toString() + ".txt";
   				
   				//just do it !
   				FileUtility.writeToFile(text, filename);
   			
   			}//end of first for cycle
   		}//end of second for cycle
   		
   		System.out.println("Topic "+mainTarget+" completed");
	}//end of function
}
