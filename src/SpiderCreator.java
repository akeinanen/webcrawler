public class SpiderCreator {
	
	public static void main(String[] args) {
		// Instantiate a Spider object
		Spider spider = new Spider();
		// Determine search terms
		spider.search("http://wikipedia.org/", "epic");
	}
}