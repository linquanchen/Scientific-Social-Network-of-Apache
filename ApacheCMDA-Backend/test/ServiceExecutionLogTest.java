import java.util.Date;

import models.ClimateService;
import models.User;

import org.junit.Before;
import org.junit.Test;


public class ServiceExecutionLogTest {
	private static long TEST_ID = 0;
	private static ClimateService climateService;
	private static User user;
	private static ServiceConfiguration serviceConfiguration;
	private static String TEST_PURPOSE = "purpose";
	private static Date TEST_EXECUTION_START_TIME;
	private static Date TEST_EXECUTION_END_TIME;
	private static String TEST_PLOT_URL = "test_plotUrl";
	private static String TEST_DATA_URL = "test_dataUrl";
	private static Date TEST_DATASET_STUDY_START_TIME;
	private static Date TEST_DATASET_STUDY_END_TIME;
	
	private static ServiceExecutionLog serviceExecutionLog;
	private static ServiceExecutionLog serviceExecutionLog1;
	
	@Before
	public void setUp() throws Exception{
		serviceExecutionLog = new ServiceExecutionLog();
		climateService = new ClimateService();
		serviceConfiguration = new ServiceConfiguration();
		user = new User();
		TEST_EXECUTION_START_TIME = new Date();
		TEST_EXECUTION_END_TIME = new Date();
		TEST_DATASET_STUDY_START_TIME = new Date();
		TEST_DATASET_STUDY_END_TIME = new Date();
		serviceExecutionLog1 = new ServiceExecutionLog(climateService, user, serviceConfiguration, TEST_PURPOSE,
				TEST_EXECUTION_START_TIME, TEST_EXECUTION_END_TIME, TEST_DATA_URL, TEST_PLOT_URL,
				TEST_DATASET_STUDY_START_TIME, TEST_DATASET_STUDY_END_TIME);
	}
	
	@Test
	public void testId() {
		serviceExecutionLog.setId(TEST_ID);
		assertEquals(TEST_ID, serviceExecutionLog.getId());
	}

	@Test
	public void testClimateService() {
		serviceExecutionLog.setClimateService(climateService);
		assertEquals(climateService, serviceExecutionLog.getClimateService());
	}
	
	@Test
	public void testUser() {
		serviceExecutionLog.setUser(user);
		assertEquals(user, serviceExecutionLog.getUser());
	}
	
	@Test
	public void testServiceConfiguration() {
		serviceExecutionLog.setServiceConfiguration(serviceConfiguration);
		assertEquals(serviceConfiguration, serviceExecutionLog.getServiceConfiguration());
	}
	
	@Test
	public void testPurpose() {
		serviceExecutionLog.setPurpose(TEST_PURPOSE);
		assertEquals(TEST_PURPOSE, serviceExecutionLog.getPurpose());
	}
	
	@Test
	public void testExecutionStartTime() {
		serviceExecutionLog.setExecutionStartTime(TEST_EXECUTION_START_TIME);
		assertEquals(TEST_EXECUTION_START_TIME, serviceExecutionLog.getExecutionStartTime());
	}
	
	@Test
	public void testExecutionEndTime() {
		serviceExecutionLog.setExecutionEndTime(TEST_EXECUTION_END_TIME);
		assertEquals(TEST_EXECUTION_END_TIME, serviceExecutionLog.getExecutionEndTime());
	}
	
	@Test
	public void testPlotUrl(){
		serviceExecutionLog.setPlotUrl(TEST_PLOT_URL);
		assertEquals(TEST_PLOT_URL, serviceExecutionLog.getPlotUrl());
	}
	
	@Test
	public void testDataUrl(){
		serviceExecutionLog.setDataUrl(TEST_DATA_URL);
		assertEquals(TEST_DATA_URL, serviceExecutionLog.getDataUrl());
	}
	
	@Test
	public void testDatasetStudyStartTime() {
		serviceExecutionLog.setDatasetStudyStartTime(TEST_DATASET_STUDY_START_TIME);
		assertEquals(TEST_DATASET_STUDY_START_TIME, serviceExecutionLog.getDatasetStudyStartTime());
	}
	
	@Test
	public void testDatasetStudyEndTime() {
		serviceExecutionLog.setDatasetStudyEndTime(TEST_DATASET_STUDY_END_TIME);
		assertEquals(TEST_DATASET_STUDY_END_TIME, serviceExecutionLog.getDatasetStudyEndTime());
	}
}
