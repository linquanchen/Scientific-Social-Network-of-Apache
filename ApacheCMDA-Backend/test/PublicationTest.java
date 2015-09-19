import models.User;

import org.junit.Before;
import org.junit.Test;


public class PublicationTest {

	private static String TEST_PAPER_TITLE = "test_paperTitle";
	private static User TEST_AUTHOR;
	private static String TEST_PUBLICATION_CHANNEL = "test_publicationChannel";
	private static int TEST_YEAR = 1;
	
	private static Publication publication;
	private static Publication publication1;
	
	@Before
	public void setUp() throws Exception{
		publication = new Publication();
		TEST_AUTHOR = new User();
		publication1 = new Publication(TEST_PAPER_TITLE, TEST_AUTHOR, TEST_PUBLICATION_CHANNEL, TEST_YEAR);
	}
	
	@Test
	public void testPaperTitle() {
		publication.setPaperTitle(TEST_PAPER_TITLE);
		assertEquals(TEST_PAPER_TITLE, publication.getPaperTitle());
	}

	@Test
	public void testAuthor() {
		publication.setAuthor(TEST_AUTHOR);
		assertEquals(TEST_AUTHOR, publication.getAuthor());
	}
	
	@Test
	public void testPublicationChannel() {
		publication.setPublicationChannel(TEST_PUBLICATION_CHANNEL);
		assertEquals(TEST_PUBLICATION_CHANNEL, publication.getPublicationChannel());
	}
	
	@Test
	public void testYear() {
		publication.setYear(TEST_YEAR);
		assertEquals(TEST_YEAR, publication.getYear());
	}
	
}
