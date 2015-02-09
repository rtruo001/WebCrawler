import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerMultiThreads {
	//CONSTANTS
	private final static int THREADMAX = 8;
	
	//PRIVATE VARIABLES
	private String urlTextFile;
	private int pages;
	private int levels;
	private int threadCount;
	
	Integer count;
	HashMap <String,String> duplicateLinks;

	//PUBLIC VARIABLES
	public LinkedList<String> urls;
	
	/*	
	 * Constructors
	 */
	public CrawlerMultiThreads(String textFile) {
		urlTextFile = textFile;
		
		//Temporary values
		pages = 0;
		levels = 0;
		threadCount = 0;
		
		urls = new LinkedList<String>();
	}
	
	/*  ==========================================================================
	    Function that reads in a text file. Reads in every line of the text file
	    and then saves all of them into an ArrayList<String> urls. This array list
	    contains all urls which returns from the function.
	    
	    @param      String                  The input text file containing initial urls
	    @return     ArrayList<String>       Returns a list of all urls from the input 
	                                        text file
	    ========================================================================== */
	public void ReadFile() {
        System.out.println("Fetching textFile " + urlTextFile);
	    File file = new File(urlTextFile);
	    
	    try {        	    
	        FileInputStream fis = new FileInputStream(file);
	        InputStreamReader isr = new InputStreamReader(fis);
	        BufferedReader br = new BufferedReader(isr);
	        String currURL = "";
	       
	        while ((currURL = br.readLine()) != null) {
	            System.out.println("Reading from text file, URL: " + currURL);
	            urls.push(currURL);
	        }
	        fis.close();
	        isr.close();
	        br.close();
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void WebCrawl() {
		count = new Integer(0);
		duplicateLinks = new HashMap<String,String>();
		while (urls.size() != 0 && threadCount <= THREADMAX) {
	        (new Thread(new CrawlerThreads(urls, duplicateLinks, count))).start();
		}
    }
}
