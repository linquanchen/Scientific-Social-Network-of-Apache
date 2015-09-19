import models.User;

import org.junit.Before;
import org.junit.Test;


public class ConferencePublicationTest {
	private static String TEST_NAME = "test_name";
	private static String TEST_LOCATION = "test_location";
	private static String TEST_TIME = "test_time";
	private static String TEST_PAGE = "test_page";
	
	private static String TEST_PAPER_TITLE = "test_paperTitle";
	private static User TEST_AUTHOR;
	private static String TEST_PUBLICATION_CHANNEL = "test_publicationChannel";
	private static int TEST_YEAR = 1;
	
	private static ConferencePublication conferencePublication;
	private static ConferencePublication conferencePublication1;
	
	@Before
	public void setUp() throws Exception{
		conferencePublication = new ConferencePublication();
		TEST_AUTHOR = new User();
		conferencePublication1 = new ConferencePublication(TEST_PAPER_TITLE, TEST_AUTHOR, TEST_PUBLICATION_CHANNEL, TEST_YEAR,
				TEST_NAME, TEST_LOCATION, TEST_TIME, TEST_PAGE);
	}
	
	@Test
	public void testName() {
		conferencePublication.setName(TEST_NAME);
		assertEquals(TEST_NAME, conferencePublication.getName());
	}
	
	@Test
	public void testLocation() {
		conferencePublication.setLocation(TEST_LOCATION);
		assertEquals(TEST_LOCATION, conferencePublication.getLocation());
	}
	
	@Test
	public void testTime() {
		conferencePublication.setTime(TEST_TIME);
		assertEquals(TEST_TIME, conferencePublication.getTime());
	}
	
	@Test
	public void testPage() {
		conferencePublication.setPage(TEST_PAGE);
		assertEquals(TEST_PAGE, conferencePublication.getPage());
	}
}
