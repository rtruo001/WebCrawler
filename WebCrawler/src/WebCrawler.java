/*
 * Name: Randy Truong
 * TA: Mohiuddin Qader
 * Partner: 
 */

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class WebCrawler {

    /*  ==========================================================================
        Function that reads in a text file. Reads in every line of the text file
        and then saves all of them into an ArrayList<String> urls. This array list
        contains all urls which returns from the function.
        
        @param      String                  The input text file containing initial urls
        @return     ArrayList<String>       Returns a list of all urls from the input 
                                            text file
        ========================================================================== */
    private static ArrayList<String> ReadFile(String textFile) {
        File file = new File(textFile);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        ArrayList<String> urls = new ArrayList<String>();

        try {        	    
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String currURL = "";
           
            while ((currURL = br.readLine()) != null) {
                System.out.println("Reading from text file, URL: " + currURL);
                urls.add(currURL);
            }
            fis.close();
            isr.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
    
    /*  ==========================================================================
	    Trims out the output that passes through the size.
	    
	    @param      String		The message to print.
	    			Object		The arguments placed into the print.
	    @return     NONE        
	    ========================================================================== */
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    /*  ==========================================================================
	    Trims out the output that passes through the size.
	    
	    @param      String		The given string
	    			int			The width that the string characters should not pass.
	    @return     NONE        
	    ========================================================================== */
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
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
	    Stores the html contents into the file in directory WebCrawlerContents
	    
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

    /*  ==========================================================================
	    Downloads the url's HTML using Jsoup. Adjusts and sends the html string
	    accordingly to save the contents into the files.
	    
	    @param      String		The url to download the html
	    			int			To append to the file name to determine num of files
	    @return     NONE        
	    ========================================================================== */
    private static void DownloadingLinks(String url, int count) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            String htmlContent = doc.html();
            String fileToStore = "fileToStore" + Integer.toString(count);
            SaveContentIntoFile(htmlContent, fileToStore);

            System.out.println("\nLinks: " + links.size());
            PrintLinks(links);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Webcrawl(ArrayList<String> urls) {
        for (int i = 0; i < urls.size(); ++i) {
            DownloadingLinks(urls.get(i), i);
        }
    }

    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "Add parameter: Text file including URLs");
        String textFile = args[0];
        System.out.println("Fetching textFile " + textFile);
        ArrayList<String> urls = ReadFile(textFile);

        for (int i = 0; i < urls.size(); ++i) {
            System.out.println(urls.get(i));
        }

        File dir = new File("WebCrawlerContents");
        dir.mkdir();
        Webcrawl(urls);
        System.out.println("\nFINISHED");
    }
}