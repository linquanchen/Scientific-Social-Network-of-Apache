import models.Parameter;

import org.junit.Before;
import org.junit.Test;


public class ServiceConfigurationItemTest {
	private static long TEST_ID = 0;
	private static ServiceConfiguration serviceConfiguration;
	private static Parameter parameter;
	private static String TEST_VALUE = "test_value";
	
	private static ServiceConfigurationItem serviceConfigurationItem;
	private static ServiceConfigurationItem serviceConfigurationItem1;
	
	@Before
	public void setUp() throws Exception{
		serviceConfigurationItem = new ServiceConfigurationItem();
		serviceConfiguration = new ServiceConfiguration();
		parameter = new Parameter();
		serviceConfigurationItem1 = new ServiceConfigurationItem(serviceConfiguration, parameter, TEST_VALUE);
	}
	
	@Test
	public void testServiceConfiguration() {
		serviceConfigurationItem.setServiceConfiguration(serviceConfiguration);
		assertEquals(serviceConfiguration, serviceConfigurationItem.getServiceConfiguration());
	}
	
	@Test
	public void testParameter() {
		serviceConfigurationItem.setParameter(parameter);
		assertEquals(parameter, serviceConfigurationItem.getParameter());
	}

	@Test
	public void testValue() {
		serviceConfigurationItem.setValue(TEST_VALUE);
		assertEquals(TEST_VALUE, serviceConfigurationItem.getValue());
	}
}
