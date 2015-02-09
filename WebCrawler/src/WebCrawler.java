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

    public static void main(String[] args) throws IOException {    	
    	CrawlerMultiThreads crawler;
    	
    	//Creates a new directory called WebCrawlerContents
    	File dir = new File("WebCrawlerContents");
        dir.mkdir();
    	
    	//TEMPORARY TODO
    	if (args.length == 1) {
    		crawler = new CrawlerMultiThreads(args[0]);
    		crawler.ReadFile();
    		crawler.WebCrawl();
    	}
    	else {
    		System.out.println("Add parameter: Text file including the URLs");
    	}
       
        System.out.println("\nFINISHED");
    }
}