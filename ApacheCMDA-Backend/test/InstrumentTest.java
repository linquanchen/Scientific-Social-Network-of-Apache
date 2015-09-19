import static org.junit.Assert.*;

import java.util.Date;

import models.Instrument;

import org.junit.Before;
import org.junit.Test;


public class InstrumentTest {
	private static long TEST_ID = 0;
	private static String TEST_NAME = "test_name";
	private static String TEST_DESCRIPTION = "test_description";
	private static Date TEST_LAUNCH_DATE;
	
	private static Instrument instrument;
	private static Instrument instrument1;
	
	@Before
	public void setUp() throws Exception{
		instrument = new Instrument();
		TEST_LAUNCH_DATE = new Date();
		instrument1 = new Instrument(TEST_NAME, TEST_DESCRIPTION, TEST_LAUNCH_DATE);
	}
	
	@Test
	public void testName() {
		instrument.setName(TEST_NAME);
		assertEquals(TEST_NAME, instrument.getName());
	}

	@Test
	public void testId() {
		instrument.setId(TEST_ID);
		assertEquals(TEST_ID, instrument.getId());
	}
	
	@Test
	public void testDescription() {
		instrument.setDescription(TEST_DESCRIPTION);
		assertEquals(TEST_DESCRIPTION, instrument.getDescription());
	}
	
	@Test
	public void testLaunchDate() {
		instrument.setLaunchDate(TEST_LAUNCH_DATE);
		assertEquals(TEST_LAUNCH_DATE, instrument.getLaunchDate());
	}
}
