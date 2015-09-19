import static org.junit.Assert.*;

import models.User;

import org.junit.Before;
import org.junit.Test;


public class UserTest {

	private static long TEST_ID = 0;
	private static String USER_NAME = "userName";
	private static String PASSWORD = "password";
	private static String FIRST_NAME = "firstName";
	private static String LAST_NAME = "lastName";
	private static String MIDDLE_INITIAL = "middleInitial";
	private static String AFFILIATION = "affiliation";
	private static String TITLE = "title";
	private static String EMAIL = "email";
	private static String MAILING_ADDRESS = "mailingAddress";
	private static String PHONE_NUMBER = "phoneNumber";
	private static String FAX_NUMBER = "faxNumber";
	private static String RESEARCH_FIELDS = "researchFields";
	private static String HIGHTEST_DEGRESS = "highestDegree";
	
	private static User user;
	private static User user1;
	
	@Before
	public void setUp() throws Exception{
		user = new User();
		user1 = new User(USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, MIDDLE_INITIAL, AFFILIATION, TITLE,
				EMAIL, MAILING_ADDRESS, PHONE_NUMBER, FAX_NUMBER, RESEARCH_FIELDS, HIGHTEST_DEGRESS);
	}

	@Test
	public void testUserName() {
		user.setUserName(USER_NAME);
		assertEquals(USER_NAME, user.getUserName());
	}
	
	@Test
	public void testPassword() {
		user.setPassword(PASSWORD);
		assertEquals(PASSWORD, user.getPassword());
	}
	
	@Test
	public void testFirstName() {
		user.setFirstName(FIRST_NAME);
		assertEquals(FIRST_NAME, user.getFirstName());
	}
	
	@Test
	public void testLastName() {
		user.setLastName(LAST_NAME);
		assertEquals(LAST_NAME, user.getLastName());
	}
	
	@Test
	public void testMiddleInitial() {
		user.setMiddleInitial(MIDDLE_INITIAL);
		assertEquals(MIDDLE_INITIAL, user.getMiddleInitial());
	}
	
	@Test
	public void testAffiliation() {
		user.setAffiliation(AFFILIATION);
		assertEquals(AFFILIATION, user.getAffiliation());
	}
	
	@Test
	public void testTitle() {
		user.setTitle(TITLE);
		assertEquals(TITLE, user.getTitle());
	}
	
	@Test
	public void testEmail() {
		user.setEmail(EMAIL);
		assertEquals(EMAIL, user.getEmail());
	}
	
	@Test
	public void testMailingAddress() {
		user.setMailingAddress(MAILING_ADDRESS);
		assertEquals(MAILING_ADDRESS, user.getMailingAddress());
	}
	
	@Test
	public void testPhoneNumber() {
		user.setPhoneNumber(PHONE_NUMBER);
		assertEquals(PHONE_NUMBER, user.getPhoneNumber());
	}
	
	@Test
	public void testFaxNumber() {
		user.setFaxNumber(FAX_NUMBER);
		assertEquals(FAX_NUMBER, user.getFaxNumber());
	}
	
	@Test
	public void testResearchFields() {
		user.setResearchFields(RESEARCH_FIELDS);
		assertEquals(RESEARCH_FIELDS, user.getResearchFields());
	}
	
	@Test
	public void testHighestDegree() {
		user.setHighestDegree(HIGHTEST_DEGRESS);
		assertEquals(HIGHTEST_DEGRESS, user.getHighestDegree());
	}
}
