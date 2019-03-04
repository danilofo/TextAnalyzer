package textAnalyzer;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;



public class UrlReader {
	
	public static InputStream openURL(String qurl) throws IOException
	{
		try {
			URL url = new URL(qurl);
			URLConnection openConnection = url.openConnection();
			InputStream input= openConnection.getInputStream();
			
			return input;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readJSON(String pUrl)
	{
	    //pUrl is the URL we created in previous step
	    try
	   {
	        URL url=new URL(pUrl);
	        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
	        BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        StringBuffer buffer=new StringBuffer();
	        while((line=br.readLine())!=null){
	            buffer.append(line);
	        }
	        return buffer.toString();
	    }catch(Exception e){
	        e.printStackTrace();
	   }
	    return null;
	}
	
	public static void UrlToFile(String source, String dest){
		
			InputStream input;
			try {
				input = openURL(source);
				File destination = new File(dest);
				FileUtility.writeToFile(input.toString(), destination.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}
	
	public static String htmlToText(InputStream html) {
	    try {
			Document document = Jsoup.parse(html, null, "");
			Element body = document.body();

			return buildStringFromNode(body).toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	private static StringBuffer buildStringFromNode(Node node) throws NullPointerException { //used in htmlToText
	    StringBuffer buffer = new StringBuffer();
	
	    if (node instanceof TextNode) {
	        TextNode textNode = (TextNode) node;
	        buffer.append(textNode.text().trim());
	    }
	
	    for (Node childNode : node.childNodes()) {
	        buffer.append(buildStringFromNode(childNode));
	    }
	
	    if (node instanceof Element) {
	        Element element = (Element) node;
	        String tagName = element.tagName();
	        if ("p".equals(tagName) || "br".equals(tagName)) {
	            buffer.append("\n");
	        }
	    }
	
	    return buffer;
	}
}