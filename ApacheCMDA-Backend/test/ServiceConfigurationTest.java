import models.ClimateService;
import models.User;

import org.junit.Before;
import org.junit.Test;


public class ServiceConfigurationTest {

	private static long TEST_ID = 0;
	private static String TEST_RUN_TIME = "runTime";
	private static ClimateService climateService;
	private static User user;
	
	private static ServiceConfiguration serviceConfiguration;
	private static ServiceConfiguration serviceConfiguration1;
	
	@Before
	public void setUp() throws Exception{
		user = new User();
		climateService = new ClimateService();
		serviceConfiguration = new ServiceConfiguration();
		serviceConfiguration1 = new ServiceConfiguration(climateService, user, TEST_RUN_TIME);
	}
	
	@Test
	public void testClimateService() {
		serviceConfiguration.setClimateservice(climateService);
		assertEquals(climateService, serviceConfiguration.getClimateservice());
	}
	
	@Test
	public void testId() {
		serviceConfiguration.setId(TEST_ID);
		assertEquals(TEST_ID, serviceConfiguration.getId());
	}
	
	@Test
	public void testRunTime() {
		serviceConfiguration.setRunTime(TEST_RUN_TIME);
		assertEquals(TEST_RUN_TIME, serviceConfiguration.getRunTime());
	}
	
	@Test
	public void testUser() {
		serviceConfiguration.setUser(user);
		assertEquals(user, serviceConfiguration.getUser());
	}

	@Test
	public void testEquals() {
		ServiceConfiguration serviceConfiguration1 = new ServiceConfiguration();
		assertEquals(true, serviceConfiguration.equals(serviceConfiguration1));
		assertEquals(false, serviceConfiguration.equals(" "));
		serviceConfiguration1.setId(100);
		assertEquals(false, serviceConfiguration.equals(serviceConfiguration1));
	}
}
