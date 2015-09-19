import static org.junit.Assert.*;
import models.ClimateService;
import models.Parameter;

import org.junit.Before;
import org.junit.Test;



public class ParameterTest {
	private static long TEST_ID = 0;
	private static ClimateService climateService;
	private static long TEST_INDEX_IN_SERVICE = 1;
	private static String TEST_NAME = "name";
	private static String TEST_DATA_RANGE = "dataRange";
	private static String TEST_RULE = "rule";
	private static String TEST_PURPOSE = "purpose";
	
	private static Parameter parameter;
	private static Parameter parameter1;
	
	@Before
	public void setUp() throws Exception{
		parameter = new Parameter();
		climateService = new ClimateService();
		parameter1 = new Parameter(climateService, TEST_INDEX_IN_SERVICE, TEST_NAME, TEST_DATA_RANGE,
				TEST_RULE, TEST_PURPOSE);
	}
	
	@Test
	public void testClimateService() {
		parameter.setClimateService(climateService);
		assertEquals(climateService, parameter.getClimateService());
	}
	
	@Test
	public void testIndexInService() {
		parameter.setIndexInService(TEST_INDEX_IN_SERVICE);
		assertEquals(TEST_INDEX_IN_SERVICE, parameter.getIndexInService());
	}
	
	@Test
	public void testName() {
		parameter.setName(TEST_NAME);
		assertEquals(TEST_NAME, parameter.getName());
	}
	
	@Test
	public void testDataRange() {
		parameter.setDataRange(TEST_DATA_RANGE);
		assertEquals(TEST_DATA_RANGE, parameter.getDataRange());
	}
	
	@Test
	public void testRule() {
		parameter.setRule(TEST_RULE);
		assertEquals(TEST_RULE, parameter.getRule());
	}
	
	@Test
	public void testPurpose() {
		parameter.setPurpose(TEST_PURPOSE);
		assertEquals(TEST_PURPOSE, parameter.getPurpose());
	}
}
