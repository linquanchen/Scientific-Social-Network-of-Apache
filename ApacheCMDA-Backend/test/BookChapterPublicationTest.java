import models.User;

import org.junit.Before;
import org.junit.Test;


public class BookChapterPublicationTest {

	private static String TEST_BOOK_NAME = "test_bookName";
	private static String TEST_EDITOR_NAME = "test_editorName";
	private static String TEST_TIME = "test_Time";
	
	private static String TEST_PAPER_TITLE = "test_paperTitle";
	private static User TEST_AUTHOR;
	private static String TEST_PUBLICATION_CHANNEL = "test_publicationChannel";
	private static int TEST_YEAR = 1;
	
	private BookChapterPublication bookChapterPublication;
	private BookChapterPublication bookChapterPublication1;
	
	@Before
	public void setUp() throws Exception{
		bookChapterPublication = new BookChapterPublication();
		TEST_AUTHOR = new User();
		bookChapterPublication1 = new BookChapterPublication(TEST_PAPER_TITLE, TEST_AUTHOR, TEST_PUBLICATION_CHANNEL, TEST_YEAR,
				TEST_BOOK_NAME, TEST_EDITOR_NAME, TEST_TIME);
	}
	
	@Test
	public void testBookName() {
		bookChapterPublication.setBookName(TEST_BOOK_NAME);
		assertEquals(TEST_BOOK_NAME, bookChapterPublication.getBookName());
	}

	@Test
	public void testEditorName() {
		bookChapterPublication.setEditorName(TEST_EDITOR_NAME);
		assertEquals(TEST_EDITOR_NAME, bookChapterPublication.getEditorName());
	}
	
	@Test
	public void testTime() {
		bookChapterPublication.setTime(TEST_TIME);
		assertEquals(TEST_TIME, bookChapterPublication.getTime());
	}
}
