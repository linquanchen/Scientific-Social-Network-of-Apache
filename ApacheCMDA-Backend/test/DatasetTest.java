import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ClimateService;
import models.Dataset;
import models.Instrument;

import org.junit.Before;
import org.junit.Test;


public class DatasetTest {
	private static long TEST_ID = 0;
	private static String TEST_NAME = "test_name";
	private static String TEST_DATA_SOURCE_NAME_IN_WEB_INTERFACE = "test_dataSourceNameinWebInterface";
	private static String TEST_AGENCY_ID = "test_agencyId";
	private static Instrument TEST_INSTRUMENT;
	private List<ClimateService> TEST_CLIMATE_SERVICE_SET;
	private static Date TEST_PUBLISH_TIME_STAMP;
	private static String TEST_URL = "test_url";
	private static String TEST_PHYSICAL_VARIABLE = "test_physicalVariable";
	private static String TEST_CMIP5VAR_NAME = "test_CMIP5VarName";
	private static String TEST_UNITS = "test_units";
	private static String TEST_GRID_DIMENSION = "test_gridDimension";
	private static String TEST_SOURCE = "test_source";
	private static String TEST_STATUS = "test_status";
	private static String TEST_RESPONSIBLE_PERSION = "test_responsiblePerson";
	private static String TEST_VARIABLE_NAME_IN_WEB_INTERFACE = "test_variableNameInWebInterface";
	private static String TEST_DATA_SOURCE_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE = "test_dataSourceInputParameterToCallScienceApplicationCode";
	private static String TEST_VARIABLE_NAME_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE = "variableNameInputParameterToCallScienceApplicationCode";
	private static String TEST_COMMENT = "test_comment";
	private static Date TEST_START_TIME;
	private static Date TEST_END_TIME;
	
	private static Dataset dataSet;
	private static Dataset dataSet1;
	
	public void initAddClimateService() {
		ClimateService climateService1 = new ClimateService();
		ClimateService climateService2 = new ClimateService();
		TEST_CLIMATE_SERVICE_SET.add(climateService1);
		TEST_CLIMATE_SERVICE_SET.add(climateService2);
	}
	
	@Before
	public void setUp() throws Exception{
		dataSet = new Dataset();
		TEST_PUBLISH_TIME_STAMP = new Date();
		TEST_START_TIME = new Date();
		TEST_END_TIME = new Date();
		TEST_INSTRUMENT = new Instrument();
		TEST_CLIMATE_SERVICE_SET = new ArrayList<ClimateService>();
		
		initAddClimateService();
		dataSet1 = new Dataset(TEST_NAME, TEST_DATA_SOURCE_NAME_IN_WEB_INTERFACE, TEST_AGENCY_ID, TEST_INSTRUMENT,
				TEST_CLIMATE_SERVICE_SET, TEST_PUBLISH_TIME_STAMP, TEST_URL, TEST_PHYSICAL_VARIABLE, TEST_CMIP5VAR_NAME,
				TEST_UNITS, TEST_GRID_DIMENSION, TEST_SOURCE, TEST_STATUS, TEST_RESPONSIBLE_PERSION, TEST_VARIABLE_NAME_IN_WEB_INTERFACE,
				TEST_DATA_SOURCE_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE, TEST_VARIABLE_NAME_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE,
				TEST_COMMENT, TEST_START_TIME, TEST_END_TIME);
	}
	
	@Test
	public void testName(){
		dataSet.setName(TEST_NAME);
		assertEquals(TEST_NAME, dataSet.getName());
	}

	@Test
	public void testDataSourceNameinWebInterface(){
		dataSet.setDataSourceNameinWebInterface(TEST_DATA_SOURCE_NAME_IN_WEB_INTERFACE);
		assertEquals(TEST_DATA_SOURCE_NAME_IN_WEB_INTERFACE, dataSet.getDataSourceNameinWebInterface());
	}
	
	@Test
	public void testAgencyId(){
		dataSet.setAgencyId(TEST_AGENCY_ID);
		assertEquals(TEST_AGENCY_ID, dataSet.getAgencyId());
	}
	
	@Test
	public void testInstrument(){
		dataSet.setInstrument(TEST_INSTRUMENT);
		assertEquals(TEST_INSTRUMENT, dataSet.getInstrument());
	}
	
	@Test
	public void testClimateServiceSet(){
		dataSet.setClimateServiceSet(TEST_CLIMATE_SERVICE_SET);
		assertEquals(TEST_CLIMATE_SERVICE_SET, dataSet.getClimateServiceSet());
	}
	
	@Test
	public void testPublishTimeStamp(){
		dataSet.setPublishTimeStamp(TEST_PUBLISH_TIME_STAMP);
		assertEquals(TEST_PUBLISH_TIME_STAMP, dataSet.getPublishTimeStamp());
	}
	
	@Test
	public void testUrl(){
		dataSet.setUrl(TEST_URL);
		assertEquals(TEST_URL, dataSet.getUrl());
	}
	
	@Test
	public void testPhysicalVariable(){
		dataSet.setPhysicalVariable(TEST_PHYSICAL_VARIABLE);
		assertEquals(TEST_PHYSICAL_VARIABLE, dataSet.getPhysicalVariable());
	}
	
	@Test
	public void testCMIP5VarName(){
		dataSet.setCMIP5VarName(TEST_CMIP5VAR_NAME);
		assertEquals(TEST_CMIP5VAR_NAME, dataSet.getCMIP5VarName());
	}
	
	@Test
	public void testUnits(){
		dataSet.setUnits(TEST_UNITS);
		assertEquals(TEST_UNITS, dataSet.getUnits());
	}
	
	@Test
	public void testGridDimension(){
		dataSet.setGridDimension(TEST_GRID_DIMENSION);
		assertEquals(TEST_GRID_DIMENSION, dataSet.getGridDimension());
	}
	
	@Test
	public void testSource(){
		dataSet.setSource(TEST_SOURCE);
		assertEquals(TEST_SOURCE, dataSet.getSource());
	}
	
	@Test
	public void testStatus(){
		dataSet.setStatus(TEST_STATUS);
		assertEquals(TEST_STATUS, dataSet.getStatus());
	}
	
	@Test
	public void testResponsiblePerson(){
		dataSet.setResponsiblePerson(TEST_RESPONSIBLE_PERSION);
		assertEquals(TEST_RESPONSIBLE_PERSION, dataSet.getResponsiblePerson());
	}
	
	@Test
	public void testVariableNameInWebInterface(){
		dataSet.setVariableNameInWebInterface(TEST_VARIABLE_NAME_IN_WEB_INTERFACE);
		assertEquals(TEST_VARIABLE_NAME_IN_WEB_INTERFACE, dataSet.getVariableNameInWebInterface());
	}
	
	@Test
	public void testDataSourceInputParameterToCallScienceApplicationCode(){
		dataSet.setDataSourceInputParameterToCallScienceApplicationCode(TEST_DATA_SOURCE_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE);
		assertEquals(TEST_DATA_SOURCE_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE, dataSet.getDataSourceInputParameterToCallScienceApplicationCode());
	}
	
	@Test
	public void testVariableNameInputParameterToCallScienceApplicationCode(){
		dataSet.setVariableNameInputParameterToCallScienceApplicationCode(TEST_VARIABLE_NAME_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE);
		assertEquals(TEST_VARIABLE_NAME_INPUT_PARAMETER_TO_CALL_SCIENCE_APPLICATION_CODE, dataSet.getVariableNameInputParameterToCallScienceApplicationCode());
	}
	
	@Test
	public void testComment(){
		dataSet.setComment(TEST_COMMENT);
		assertEquals(TEST_COMMENT, dataSet.getComment());
	}
	
	@Test
	public void testStartTime(){
		dataSet.setStartTime(TEST_START_TIME);
		assertEquals(TEST_START_TIME, dataSet.getStartTime());
	}
	
	@Test
	public void testEndTime(){
		dataSet.setEndTime(TEST_END_TIME);
		assertEquals(TEST_END_TIME, dataSet.getEndTime());
	}
}
