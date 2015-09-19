import static org.junit.Assert.*;

import java.util.Date;

import models.ClimateService;
import models.ServiceEntry;
import models.User;

import org.junit.Before;
import org.junit.Test;


public class ServiceEntryTest {
	private static long TEST_ID = 0;
	private static ClimateService climateService;
	private static String TEST_VERSION_NO = "versionNo";
	private static User user;
	private static Date TEST_REGISTER_TIME_STAMP;
	private static String TEST_REGISTER_NOTE = "registerNote";
	private static int TEST_COUNT = 1;
	private static Date TEST_LATEST_ACCESS_TIME_STAMP;
	
	private static Date TEST_REGISTER_TIME_STAMP1;
	private static Date TEST_LATEST_ACCESS_TIME_STAMP1;
	
	private static ServiceEntry serviceEntry;
	private static ServiceEntry serviceEntry1;
	
	@Before
	public void setUp() throws Exception{
		serviceEntry = new ServiceEntry();
		climateService = new ClimateService();
		user = new User();
		TEST_REGISTER_TIME_STAMP = new Date();
		TEST_LATEST_ACCESS_TIME_STAMP = new Date();
		
		TEST_REGISTER_TIME_STAMP1 = null;
		TEST_LATEST_ACCESS_TIME_STAMP1 = null;
		
		serviceEntry1 = new ServiceEntry(TEST_LATEST_ACCESS_TIME_STAMP, TEST_VERSION_NO, user, TEST_REGISTER_TIME_STAMP,
				TEST_REGISTER_NOTE, TEST_COUNT, climateService);
		serviceEntry1 = new ServiceEntry(TEST_LATEST_ACCESS_TIME_STAMP1, TEST_VERSION_NO, user, TEST_REGISTER_TIME_STAMP1,
				TEST_REGISTER_NOTE, TEST_COUNT, climateService);
	}
	
	@Test
	public void testId() {
		serviceEntry.setId(TEST_ID);
		assertEquals(TEST_ID, serviceEntry.getId());
	}

	@Test
	public void testClimateService() {
		serviceEntry.setClimateService(climateService);
		assertEquals(climateService, serviceEntry.getClimateService());
	}
	
	@Test
	public void testVersionNo() {
		serviceEntry.setVersionNo(TEST_VERSION_NO);
		assertEquals(TEST_VERSION_NO, serviceEntry.getVersionNo());
	}
	
	@Test
	public void testUser() {
		serviceEntry.setUser(user);
		assertEquals(user, serviceEntry.getUser());
	}
	
	@Test
	public void testRegisterTimeStamp() {
		serviceEntry.setRegisterTimeStamp(TEST_LATEST_ACCESS_TIME_STAMP);
		assertEquals(TEST_LATEST_ACCESS_TIME_STAMP, serviceEntry.getRegisterTimeStamp());
	}
	
	@Test
	public void testRegisterNote() {
		serviceEntry.setRegisterNote(TEST_REGISTER_NOTE);
		assertEquals(TEST_REGISTER_NOTE, serviceEntry.getRegisterNote());
	}
	
	@Test
	public void testCount() {
		serviceEntry.setCount(TEST_COUNT);
		assertEquals(TEST_COUNT, serviceEntry.getCount());
	}
	
	@Test
	public void testLatestAccessTimestamp() {
		serviceEntry.setLatestAccessTimestamp(TEST_LATEST_ACCESS_TIME_STAMP);
		assertEquals(TEST_LATEST_ACCESS_TIME_STAMP, serviceEntry.getLatestAccessTimestamp());
	}
}
