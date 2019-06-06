package dateCalculator;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class DateOfImportanceTests {

	@Test
	public void testDateOfImportance() {
		Date testDate = new Date();
		DateOfImportance doi = new DateOfImportance();
		doi.name = "dummyName";
		doi.date = testDate;
		assertTrue(doi.date.equals(testDate));
		assertTrue(doi.name == "dummyName");
	}

	@Test
	public void testDateOfImportanceConstr() {
		Date testDate = new Date();
		DateOfImportance doi = new DateOfImportance("dummyName", testDate);
		assertTrue(doi.name == "dummyName");
		assertTrue(doi.date == testDate);
	}
}
