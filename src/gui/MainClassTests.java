package gui;

import static org.junit.Assert.*;
import java.text.ParseException;
import org.junit.Ignore;
import org.junit.Test;

public class MainClassTests {

	@Test
	@Ignore("No method to close window after test implemented yet")
	public void testStartNoArgs() {
		String[] args = {};
		try {
			Main.main(args);
		} catch (ParseException e) {
			fail(e.toString());
		}		
	}

	@Test
	@Ignore("Not capturing response, hence so stupid test it is ignored.")
	public void testStartOneArg(){
		String[] args = {"/help"};
		try {
			Main.main(args);
		} catch (ParseException e) {
			fail(e.toString());
		}		
	}

	@Test
	@Ignore("Not capturing response, hence so stupid test it is ignored.")
	public void testStartManyArgPositiveTest(){
		String[] args = {"/help", "/date=2014-10-11", "/name:Test name"};
		try {
			Main.main(args);
		} catch (ParseException e) {
			fail(e.toString());
		}		
	}
}
