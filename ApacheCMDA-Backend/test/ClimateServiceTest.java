

import static org.junit.Assert.*;
import models.ClimateService;
import models.User;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class ClimateServiceTest {

	private static long TEST_ROOT_SERVICE_ID = 1;
	private static User TEST_USER;
	private static String TEST_NAME = "test_name";
	private static String TEST_PURPOSE = "test_purpose";
	private static String TEST_URL = "test_url";
	private static String TEST_SCENARIO = "test_scenario";
	private static Date TEST_CREATE_TIME;
	private static Date TEST_CREATE_TIME1;
	private static String TEST_VERSIONNO = "test_versionNo";
	
	private static ClimateService climateService;
	private static ClimateService climateService1;
	
	@Before
	public void setUp() throws Exception{
		climateService = new ClimateService();
		TEST_USER = new User();
		TEST_CREATE_TIME = new Date(0);
		TEST_CREATE_TIME1 = null; 
		climateService1 = new ClimateService(TEST_ROOT_SERVICE_ID, TEST_USER, TEST_NAME, TEST_PURPOSE, TEST_URL, TEST_SCENARIO, TEST_CREATE_TIME, TEST_VERSIONNO);
		climateService1 = new ClimateService(TEST_ROOT_SERVICE_ID, TEST_USER, TEST_NAME, TEST_PURPOSE, TEST_URL, TEST_SCENARIO, TEST_CREATE_TIME1, TEST_VERSIONNO);
	}
	
	@Test
	public void testRootServiceId(){
		climateService.setRootServiceId(TEST_ROOT_SERVICE_ID);
		assertEquals(climateService.getRootServiceId(), TEST_ROOT_SERVICE_ID, 0.001);

	}
	
	@Test
	public void testUser(){
		climateService.setUser(TEST_USER);
		assertEquals(TEST_USER, climateService.getUser());

	}
	
	@Test
	public void testName(){
		climateService.setName(TEST_NAME);
		assertEquals(TEST_NAME, climateService.getName());

	}

	@Test
	public void testPurpose(){
		climateService.setPurpose(TEST_PURPOSE);
		assertEquals(TEST_PURPOSE, climateService.getPurpose());
	}
	
	@Test
	public void testUrl(){
		climateService.setUrl(TEST_URL);
		assertEquals(TEST_URL, climateService.getUrl());
	}
	
	@Test
	public void testScenario(){
		climateService.setScenario(TEST_SCENARIO);
		assertEquals(TEST_SCENARIO, climateService.getScenario());
	}
	
	@Test
	public void testCreateTime(){
		climateService.setCreateTime(TEST_CREATE_TIME);
		assertEquals(TEST_CREATE_TIME, climateService.getCreateTime());
	}
	
	@Test
	public void testVersionNo(){
		climateService.setVersionNo(TEST_VERSIONNO);
		assertEquals(TEST_VERSIONNO, climateService.getVersionNo());
	}
}
