package dateCalculator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

public class AniversaryDayTests {

	static AniversaryDay day;
	static String icsFileContent = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		day = new AniversaryDay(new DateOfImportance("dummyName", new Date()), new Date(), "dummyReason");
	}

	@Test
	public void testAniversaryDay() {
		Date testDate = new Date();
		AniversaryDay thisDay = new AniversaryDay(new DateOfImportance("dummyName", testDate), testDate, "dummyReason");
		assertTrue(thisDay != null);
		assertTrue(thisDay.date.equals(testDate));
		assertTrue(thisDay.reason.equals("dummyReason"));
		assertTrue(thisDay.dateOfImportance.date.equals(testDate));
		assertTrue(thisDay.dateOfImportance.name.equals("dummyName"));
	}

	@Test
	public void testToString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(day.toString(), format.format(day.dateOfImportance.date) + ": " + day.reason);
	}

	@Test
	public void testIsAfter() {
		Date dateAfter = DayAdder(day.date, 10);
		AniversaryDay dayAfter = new AniversaryDay(new DateOfImportance("dummyName2", day.dateOfImportance.date), dateAfter, "dummyReason after");
		assertTrue(dayAfter.isAfter(day));
		assertFalse(day.isAfter(dayAfter));
		assertFalse(day.isAfter(day));
	}

	@Test
	public void testCompareTo() {
		Date dateAfter = DayAdder(day.date, 10);
		AniversaryDay dayAfter = new AniversaryDay(new DateOfImportance("dummyName2", day.dateOfImportance.date), dateAfter, "dummyReason after");
		assertTrue(day.compareTo(day)==0);
		assertTrue(day.compareTo(dayAfter)<0);
		assertTrue(dayAfter.compareTo(day)> 0);
	}

	@Test
	public void testToIcsFile_Create() {
		String fileFolder = System.getProperty("java.io.tmpdir").toString();
		String outFile = day.toIcsFile(fileFolder);
		File file = new File(outFile);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			fail("Could not test file since it could not be opened.");
			e.printStackTrace();
		}
		assertTrue(file.exists());
		BufferedReader br = new BufferedReader(fr);
		String line;
		try {
			line = br.readLine();
			while (line != null){
				icsFileContent += line + System.lineSeparator();
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(icsFileContent);
		file.delete();
	}
	
	@Test
	public void testToIcsFile_ContainReason(){
		if (icsFileContent == ""){ testToIcsFile_Create();}
		assertTrue(icsFileContent.contains(day.reason));
	}
	
	@Test
	public void testToIcsFile_ContainDateInRightFormat(){
		if (icsFileContent == ""){ testToIcsFile_Create();}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		assertTrue(icsFileContent.contains(format.format(day.date)));
	}
	
	private Date DayAdder(Date date, int numberOfDays){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, numberOfDays); 
		return new Date(c.getTimeInMillis());	
	}


}
