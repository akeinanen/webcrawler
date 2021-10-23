import java.util.Scanner;

public class SpiderCreator {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Ask for domain and save it to a variable
		System.out.println("Enter a website to crawl e.g. 'http://wikipedia.org'");
		String website = scanner.nextLine();

		// Ask for search term and save it to a variable
		System.out.println("Enter a search term");
		String searchTerm = scanner.nextLine();

		// Instantiate a Spider object
		Spider spider = new Spider();
		// Determine search terms
		spider.search(website, searchTerm);
	}
}