import models.User;

import org.junit.Before;
import org.junit.Test;


public class JournalPublicationTest {
	
	private static String TEST_PAPER_TITLE = "test_paperTitle";
	private static User TEST_AUTHOR;
	private static String TEST_PUBLICATION_CHANNEL = "test_publicationChannel";
	private static int TEST_YEAR = 1;
	
	private static String TEST_JOURNAL_NAME = "journalName";
	private static int TEST_VOLUME = 0;
	private static int TEST_Q_COLUMN = 1;
	private static String TEST_PAGE = "page";
	
	private static JournalPublication journalPublication;
	private static JournalPublication journalPublication1;
	
	@Before
	public void setUp() throws Exception{
		journalPublication = new JournalPublication();
		journalPublication1 = new JournalPublication(TEST_PAPER_TITLE, TEST_AUTHOR, TEST_PUBLICATION_CHANNEL, TEST_YEAR,
				TEST_JOURNAL_NAME, TEST_VOLUME, TEST_Q_COLUMN, TEST_PAGE);
	}
	
	@Test
	public void testJournalName() {
		journalPublication.setJournalName(TEST_JOURNAL_NAME);
		assertEquals(TEST_JOURNAL_NAME, journalPublication.getJournalName());
	}

	@Test
	public void testVolume() {
		journalPublication.setVolume(TEST_VOLUME);
		assertEquals(TEST_VOLUME, journalPublication.getVolume());
	}
	
	@Test
	public void testColumn() {
		journalPublication.setColumn(TEST_Q_COLUMN);
		assertEquals(TEST_Q_COLUMN, journalPublication.getColumn());
	}
	
	@Test
	public void testPage() {
		journalPublication.setPage(TEST_PAGE);
		assertEquals(TEST_PAGE, journalPublication.getPage());
	}
}
