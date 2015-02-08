import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlerThreads implements Runnable {
	
	//PUBLIC VARIABLES
	public LinkedList<String> thread_url;
	public static HashMap<String, String> duplicateLinks;
	public static int count;

	/*
	 * Constructors
	 */
	public CrawlerThreads(LinkedList<String> text_urls) {
		thread_url = text_urls;
		count = 0;
		if (count == 0)
			duplicateLinks = new HashMap<String, String>();
	}
	
	public void run() {
		preDownload();
	}
	
	public synchronized void preDownload() {
		while (thread_url.size() != 0) {
			DownloadingLinks(thread_url.pop(), count);
			++count;
		}
	}
	
	 /*  ==========================================================================
	    Downloads the url's HTML using Jsoup. Adjusts and sends the html string
	    accordingly to save the contents into the files.
	    
	    @param      String		The url to download the html
	    			int			To append to the file name to determine num of files
	    @return     NONE        
	    ========================================================================== */
	private synchronized void DownloadingLinks(String url, int count) {
	    try {
	        Document doc = Jsoup.connect(url).get();
	        Elements links = doc.select("a[href]");
	
	        //Does the storing the html contents into the files
	        String htmlContent = doc.html();
	        String fileToStore = "fileToStore" + Integer.toString(count);
	        SaveContentIntoFile(htmlContent, fileToStore);
	
	        //Saves the links in the html files and sends it to end of the thread_url
	        StoringLinksIntoList(links);
	        
	        System.out.println("\nLinks: " + links.size());
	        PrintLinks(links);
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/*  ==========================================================================
	    Checks if links begin with http:// or https://. Links must start with
	    these correct protocols.
	    
	    @param      String    	The links that are getting crawled.
	    @return     boolean		If the protocols are supported or not.        
	    ========================================================================== */
	private boolean CheckProtocols(String link) {
		//Not a protocol
		if (link.length() < 7) {
			return false;
		}
		if (link.substring(0, 7).equals("http://") || link.substring(0, 8).equals("https://")) {
			return true;
		}
		return false;
	}
	
	/*  ==========================================================================
	    Stores the links into into a hashmap to ensure duplicates would not happen
	    
	    @param      Elements    	The links read from the HTML.
	    @return    	None	       
	    ========================================================================== */
	private void StoringLinksIntoList(Elements links) {
		for (Element link : links) {
			boolean ifSupportedProtocols = CheckProtocols(link.attr("abs:href"));
			if (ifSupportedProtocols) {
				if (!duplicateLinks.containsKey(link.attr("abs:href"))) {
					duplicateLinks.put(link.attr("abs:href"),link.attr("abs:href"));
					thread_url.push(link.attr("abs:href"));
				}
				else { /*Move onto next link*./ 
			else { /*Move onto next link*/ }
			}
		}
	}
	
	/*  ==========================================================================
	    Prints out all the contents the links have crawled.
	    
	    @param      Elements    The contents from the links crawled.
	    @return     NONE        
	    ========================================================================== */
	private static void PrintLinks(Elements content) {
	    for (Element link: content) {
	        System.out.println("Links within base link: " + link.attr("abs:href"));
	    }
	}
	
	/*  ==========================================================================
	    Stores the html contents into the files in directory WebCrawlerContents
	    
	    @param      String		The content of the html
	    			String		The file name to store contents in
	    @return     NONE        
	    ========================================================================== */
	private static void SaveContentIntoFile(String htmlContent, String fileToStore) {
	    System.out.println("Storing into file...\n");
	    System.out.println(htmlContent);
	    try {
	        PrintWriter writer = new PrintWriter("WebCrawlerContents/" + fileToStore, "UTF-8");
	        writer.println(htmlContent);
	        writer.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
}
