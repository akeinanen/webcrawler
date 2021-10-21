import java.util.*;

public class Spider {

	// Determine the maximum amount of pages to search
	private static final int MAX_PAGES_TO_SEARCH = 10;
	
	// Store URL's to these fields
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	
	// Prevent from having already visited pages in pagesToVisit field
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while(this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
	
	// Search for the word from the url, both given as a parameters
	public void search(String url, String searchWord) {
		
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			String currentUrl;
			
			// Instantiate a spiderLeg object
			SpiderLeg leg = new SpiderLeg();
			
			// Use url given as a parameter if iteration
			if(this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			
			// Call the crawl() method from spiderLeg class
			leg.crawl(currentUrl);
			
			// Check if the parameter searchWord was found from the url's
			boolean success = leg.searchForWord(searchWord);
			if(success) {
				System.out.println(String.format("**Success** Word %s found as %s", searchWord, currentUrl));
				break;
			}
			// During last iteration if success is false, notify that search word couldn't be found
			if(this.pagesVisited.size() == MAX_PAGES_TO_SEARCH) {
				System.out.println("Couldn't find a word " + searchWord);
			}
			
			// Assign the found links to a private field
			this.pagesToVisit.addAll(leg.getLinks());
		}
		// Notify when everything is done
		System.out.println(String.format("**Done** Visited %s web page(s).", this.pagesVisited.size()));
	}
}