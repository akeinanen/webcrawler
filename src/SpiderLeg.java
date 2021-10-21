import java.io.IOException;
import java.util.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SpiderLeg {
	// fake USER_AGENT so the web server thinks the robot is a normal web browser
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	// Links found with crawl method are stored to this variable
	private List<String> links = new LinkedList<String>();
	// Document fetched in crawl method with Jsoup is stored to this variable
	private Document htmlDocument;
	
	// search() method in Spider class calls this method.
	public boolean crawl(String url) {
		try {
			// Connect to the url given as a parameter
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			// Fetch the html from the website
			Document htmlDocument = connection.get();
			// Assign the results to a private field
			this.htmlDocument = htmlDocument;
			
			// Notify everything worked if HTTP code is 200
			if(connection.response().statusCode() == 200) {
				System.out.println("Received web page at " + url);
			} 
			// Notify and stop if retrieved content type is not HTML
			if(!connection.response().contentType().contains("text/html")) {
				System.out.println("Retrieved something other than HTML");
				return false;
			}
			
			// Find all links on the page and show the count of them
			Elements linksOnPage = htmlDocument.select("a[href]");
			System.out.println("Found (" + linksOnPage.size() + ") links");
			// Add links to a private field
			for(Element link : linksOnPage) {
				this.links.add(link.absUrl("href"));
			}
			return true;
		}
		// In case something goes wrong
		catch(IOException ioe) {
			System.out.println("Error in out HTTP request" + ioe);
			return false;
		}
	}
	
	// This methods checks wheter the word was found or not
	public boolean searchForWord(String searchWord) {
		// This method should be called only after a succesfull crawl
		if(this.htmlDocument == null) {
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
			return false;
		}
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
	}
	
	// Getter to return all the links on the page
	public List<String> getLinks() {
		return this.links;
	}
}