import static org.junit.Assert.*;

import java.util.Date;

import models.Dataset;
import models.DatasetEntry;
import models.User;

import org.junit.Before;
import org.junit.Test;


public class DatasetEntryTest {

	private static long TEST_ID = 0;
	private static String TEST_VERSION_NO = "test_versionNo";
	private static Date TEST_REGISTER_TIME_STAMP;
	private static String TEST_REGISTER_NOTE = "test_registerNote";
	private static int TEST_COUNT = 1;
	private static Date TEST_LATEST_ACCESS_TIME_STAMP;
	private static Dataset TEST_DATASET;
	private static User TEST_USER;
	
	private static DatasetEntry datasetEntry;
	private static DatasetEntry datasetEntry1;
	
	@Before
	public void setUp() throws Exception{
		datasetEntry = new DatasetEntry();
		TEST_REGISTER_TIME_STAMP = new Date();
		TEST_LATEST_ACCESS_TIME_STAMP = new Date();
		TEST_DATASET = new Dataset();
		TEST_USER = new User();
		datasetEntry1 = new DatasetEntry(TEST_VERSION_NO, TEST_REGISTER_TIME_STAMP, TEST_REGISTER_NOTE, TEST_COUNT,
				TEST_LATEST_ACCESS_TIME_STAMP, TEST_DATASET, TEST_USER);
	}
	
	@Test
	public void testVersionNo() {
		datasetEntry.setVersionNo(TEST_VERSION_NO);
		assertEquals(TEST_VERSION_NO, datasetEntry.getVersionNo());
	}

	@Test
	public void testRegisterTimeStamp() {
		datasetEntry.setRegisterTimeStamp(TEST_REGISTER_TIME_STAMP);
		assertEquals(TEST_REGISTER_TIME_STAMP, datasetEntry.getRegisterTimeStamp());
	}
	
	@Test
	public void testRegisterNote() {
		datasetEntry.setRegisterNote(TEST_REGISTER_NOTE);
		assertEquals(TEST_REGISTER_NOTE, datasetEntry.getRegisterNote());
	}
	
	@Test
	public void testCount() {
		datasetEntry.setCount(TEST_COUNT);
		assertEquals(TEST_COUNT, datasetEntry.getCount());
	}
	
	@Test
	public void testLatestAccessTimeStamp() {
		datasetEntry.setLatestAccessTimeStamp(TEST_LATEST_ACCESS_TIME_STAMP);
		assertEquals(TEST_LATEST_ACCESS_TIME_STAMP, datasetEntry.getLatestAccessTimeStamp());
	}
	
	@Test
	public void testDataset() {
		datasetEntry.setDataset(TEST_DATASET);
		assertEquals(TEST_DATASET, datasetEntry.getDataset());
	}
	
	@Test
	public void testUser() {
		datasetEntry.setUser(TEST_USER);
		assertEquals(TEST_USER, datasetEntry.getUser());
	}
}
