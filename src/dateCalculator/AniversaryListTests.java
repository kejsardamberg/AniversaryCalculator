package dateCalculator;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class AniversaryListTests {
	
	@Test
	public void testAniversaryList_Defaults() {
		AniversaryList list = new AniversaryList();
		assertTrue("Default boolean value for everyYear", list.everyYear == true);
		assertTrue("Default boolean value for everyThousandDays", list.everyThousandDays == true);
		assertTrue("Default boolean value for events in the past", list.includeEventsInThePast == false);
		assertTrue("Default boolean value for special days inclusion", list.specialDays == true);
		assertTrue("Default boolean value for half years", list.includeHalfYears == true);
		assertTrue("Check that no date of importance is created with empty constructor", list.dateOfImportance == null);
		assertTrue("Check that no aniversaries are created with limited construct", list.aniversaries.size() < 1);
	}

	@Test
	public void testAniversaryList_Construct() {
		Date testDate = new Date();
		AniversaryList list = new AniversaryList(new DateOfImportance("dummyReason", testDate));
		assertTrue("Default boolean value for everyYear", list.everyYear == true);
		assertTrue("Default boolean value for everyThousandDays", list.everyThousandDays == true);
		assertTrue("Default boolean value for events in the past", list.includeEventsInThePast == false);
		assertTrue("Default boolean value for special days inclusion", list.specialDays == true);
		assertTrue("Default boolean value for half years", list.includeHalfYears == true);
		assertTrue("Check that date of importance get the right date", list.dateOfImportance.date.equals(testDate));
		assertTrue("Check that date of importance get the right name", list.dateOfImportance.name.equals("dummyReason"));
		assertTrue("Check that aniversaries list get populated", list.aniversaries.size() > 0);
	}

	@Test
	public void testCreateAniversaryList() {
		Date testDate = DayAdder(new Date(), -Random(0,10000));
		AniversaryList list = new AniversaryList();
		list.dateOfImportance = new DateOfImportance("dummyReason", testDate);
		list.CreateAniversaryList();
		assertTrue("Check that aniversary list creation works with default values", list.aniversaries.size() > 0);
		
		list.includeEventsInThePast = false;
		list.everyYear = false;
		list.includeHalfYears = false;
		list.specialDays = false;
		list.everyThousandDays = false;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		assertTrue("Check that nothing is added if no relevant option is set", list.aniversaries.size() == 0);
		
		list.everyYear = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		int count = list.aniversaries.size();
		assertTrue("Check that yearly anniversaries are in fact added", count > 0);
		
		list.everyThousandDays = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		assertTrue("Check that aniversaries for even 1000 days are added upon request", list.aniversaries.size() > count);
		count = list.aniversaries.size();
		
		list.specialDays = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		assertTrue("Check that special days are added upon request", list.aniversaries.size() > count);
		count = list.aniversaries.size();
		

		list.includeHalfYears = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		assertTrue("Check that half year anniversaries are added upon request", list.aniversaries.size() > count);
		count = list.aniversaries.size();
		
		list.includeEventsInThePast = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		assertTrue("Chack that events in the passed adds to list", list.aniversaries.size() > count);
	}

	@Test
	@Ignore("Not implemented yet due to no sufficient cleanup method implemented yet.")
	public void testToIcsFiles() {
		Date testDate = DayAdder(new Date(), -Random(1, 10000));
		String fileFolder = System.getProperty("java.io.tmpdir").toString();
		AniversaryList list = new AniversaryList(new DateOfImportance("dummyReason", testDate));
		list.toIcsFiles(fileFolder);
	}
	
	@Test
	public void testIs1000daysBetween1000daysAniversaries(){
		
		Date testDate = DayAdder(new Date(), -Random(1, 10000));
		AniversaryList list = new AniversaryList();
		list.dateOfImportance = new DateOfImportance("dummyReason", testDate);
		list.includeEventsInThePast = true;
		list.everyYear = false;
		list.includeHalfYears = false;
		list.specialDays = false;
		list.everyThousandDays = true;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		Date checkDate = DayAdder(testDate, 1000);
		for (AniversaryDay day: list.aniversaries){
			assertTrue("Date check for " + day.toString(), day.date.equals(checkDate));
			assertTrue("Each aniversary reason contains '000' check for " + day.toString(), day.reason.toLowerCase().contains("000"));
			checkDate = DayAdder(checkDate, 1000);
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testHalfYearAniversaries(){
		
		Date testDate = DayAdder(new Date(), -Random(1, 10000));
		AniversaryList list = new AniversaryList();
		list.dateOfImportance = new DateOfImportance("dummyReason", testDate);
		list.includeEventsInThePast = true;
		list.everyYear = false;
		list.includeHalfYears = true;
		list.specialDays = false;
		list.everyThousandDays = false;
		list.aniversaries.clear();
		list.CreateAniversaryList();
		//Date checkDate = DayAdder(testDate, 1000);
		for (AniversaryDay day: list.aniversaries){
			assertTrue("Reason contains the word half for half year events", day.reason.toLowerCase().contains("half"));
			assertTrue("Correct day of month for half year events", day.date.getDate() == testDate.getDate());
			assertTrue("Correct month difference for half year events", day.date.getMonth() - testDate.getMonth() == 6 || testDate.getMonth() - day.date.getMonth() == 6);
		}
	}

	private int Random(int start, int stop){
		return (int)(Math.random() * (stop-start)) + start;
	}
	
	
	private Date DayAdder(Date date, int numberOfDays){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, numberOfDays); 
		return new Date(c.getTimeInMillis());	
	}

}
