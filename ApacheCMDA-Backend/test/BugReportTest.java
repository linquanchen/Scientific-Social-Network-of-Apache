import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class BugReportTest {

	private static long TEST_ID = 0;
	private static String TEST_TITLE = "test_title";
	private static String TEST_EMAIL = "test_email";
	private static String TEST_NAME = "test_name";
	private static String TEST_ORGANIZATION = "test_organization";
	private static String TEST_DESCRIPTION = "test_description";
	private static int TEST_SOLVED = 0;
	private static Date TEST_CREATION_DATE;
	private static Date TEST_UPDATE_DATE;
	
	private static BugReport bugReport;
	private static BugReport bugReport1;
	
	@Before
	public void setUp() throws Exception{
		bugReport = new BugReport();
		TEST_CREATION_DATE = new Date(0);
		TEST_UPDATE_DATE = new Date(1);
		bugReport1 = new BugReport(TEST_TITLE, TEST_EMAIL, TEST_NAME, TEST_ORGANIZATION, TEST_DESCRIPTION, TEST_SOLVED, TEST_CREATION_DATE, TEST_UPDATE_DATE);
	}
	
	@Test
	public void testId(){
		bugReport.setId(TEST_ID);
		assertEquals(bugReport.getId(), TEST_ID, 0.001);
	}
	
	@Test
	public void testToString(){
		assertEquals("BugReport #" + TEST_ID, bugReport.toString());
	}
	
	@Test
	public void testTitle(){
		bugReport.setTitle(TEST_TITLE);
		assertEquals(TEST_TITLE, bugReport.getTitle());
	}
	
	@Test
	public void testDescription(){
		bugReport.setDescription(TEST_DESCRIPTION);
		assertEquals(TEST_DESCRIPTION, bugReport.getDescription());
	}
	
	@Test
	public void testEmail(){
		bugReport.setEmail(TEST_EMAIL);
		assertEquals(TEST_EMAIL, bugReport.getEmail());
	}
	
	@Test
	public void testName(){
		bugReport.setName(TEST_NAME);
		assertEquals(TEST_NAME, bugReport.getName());
	}
	
	@Test
	public void testOrganization(){
		bugReport.setOrganization(TEST_ORGANIZATION);
		assertEquals(TEST_ORGANIZATION, bugReport.getOrganization());
	}
	
	@Test
	public void testSolved(){
		bugReport.setSolved(TEST_SOLVED);
		assertEquals(TEST_SOLVED, bugReport.getSolved());
	}

	@Test
	public void testCreationDate(){
		bugReport.setCreationDate(TEST_CREATION_DATE);
		assertEquals(TEST_CREATION_DATE, bugReport.getCreationDate());
	}
	
	@Test
	public void testUpdateDate(){
		bugReport.setUpdateDate(TEST_UPDATE_DATE);
		assertEquals(TEST_UPDATE_DATE, bugReport.getUpdateDate());
	}
}
