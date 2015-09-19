import models.User;

import org.junit.Before;
import org.junit.Test;


public class BookPublicationTest {

	private static String TEST_BOOK_NAME = "test_bookName";
	private static String TEST_PUBLISHER_LOCATION = "test_publisherLocation";
	private static String TEST_TIME = "test_Time";
	private static String TEST_PAGES = "test_Pages";
	
	private static String TEST_PAPER_TITLE = "test_paperTitle";
	private static User TEST_AUTHOR;
	private static String TEST_PUBLICATION_CHANNEL = "test_publicationChannel";
	private static int TEST_YEAR = 1;
	
	private static BookPublication bookPublication;
	private static BookPublication bookPublication1;
	
	@Before
	public void setUp() throws Exception{
		bookPublication = new BookPublication();
		bookPublication1 = new BookPublication(TEST_PAPER_TITLE, TEST_AUTHOR, TEST_PUBLICATION_CHANNEL, TEST_YEAR, 
				TEST_BOOK_NAME, TEST_PUBLISHER_LOCATION, TEST_TIME, TEST_PAGES);
	}
	
	@Test
	public void testBookName() {
		bookPublication.setBookName(TEST_BOOK_NAME);
		assertEquals(TEST_BOOK_NAME, bookPublication.getBookName());
	}

	@Test
	public void testPublisherLocation() {
		bookPublication.setPublisherLocation(TEST_PUBLISHER_LOCATION);
		assertEquals(TEST_PUBLISHER_LOCATION, bookPublication.getPublisherLocation());
	}
	
	@Test
	public void testTime() {
		bookPublication.setTime(TEST_TIME);
		assertEquals(TEST_TIME, bookPublication.getTime());
	}
	
	@Test
	public void testPages() {
		bookPublication.setPages(TEST_PAGES);
		assertEquals(TEST_PAGES, bookPublication.getPages());
	}
}
