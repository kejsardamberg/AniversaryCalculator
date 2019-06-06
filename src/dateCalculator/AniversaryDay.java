package dateCalculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AniversaryDay implements Comparable<AniversaryDay>{
	DateOfImportance dateOfImportance;
	Date date;
	String reason;
	
	public AniversaryDay(DateOfImportance dateOfImportance, Date date, String reason){
		this.date = date;
		this.dateOfImportance = dateOfImportance;
		this.reason = reason;
	}
	
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(this.date) + ": " + this.reason;  
	}
	
	public boolean isAfter (AniversaryDay aniveraryDay) {
		return this.date.compareTo(aniveraryDay.date) > 0;
	}
	
	@Override
	public int compareTo(AniversaryDay day) {
		return this.date.compareTo(day.date);
	}
	
	public String toIcsFile (String fileFolder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String email = "no.no@no.no";
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		//String eventDescription = "The date " + dateFormat.format(this.date) + " should be honored because it's " + this.reason + " (" + dateFormat.format(this.dateOfImportance.date) + ")";
		String fileName = dateFormat.format(this.date) + "_" + this.reason.replace(" ", "_").replace(".",  "") + ".ics";
		StringBuilder content = new StringBuilder();
		content.append(
			"BEGIN:VCALENDAR" + System.lineSeparator() +
			"VERSION:2.0" + System.lineSeparator() +
			//"PRODID:-//hacksw/handcal//NONSGML v1.0//EN" + System.lineSeparator() +
			"BEGIN:VEVENT" + System.lineSeparator() +
			"UID:" + email + System.lineSeparator() +
//"PRODID:-//Microsoft Corporation//Outlook 15.0 MIMEDIR//EN" + System.lineSeparator() +
//"VERSION:2.0" + System.lineSeparator() +
"METHOD:PUBLISH" + System.lineSeparator() +
"X-MS-OLK-FORCEINSPECTOROPEN:TRUE" + System.lineSeparator() +
//"BEGIN:VTIMEZONE" + System.lineSeparator() +
//"TZID:W. Europe Standard Time" + System.lineSeparator() +
//"BEGIN:STANDARD" + System.lineSeparator() +
//"DTSTART:16011028T030000" + System.lineSeparator() +
//"RRULE:FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10" + System.lineSeparator() +
//"TZOFFSETFROM:+0200" + System.lineSeparator() +
//"TZOFFSETTO:+0100" + System.lineSeparator() +
//"END:STANDARD" + System.lineSeparator() +
//"BEGIN:DAYLIGHT" + System.lineSeparator() +
//"DTSTART:16010325T020000" + System.lineSeparator() +
//"RRULE:FREQ=YEARLY;BYDAY=-1SU;BYMONTH=3" + System.lineSeparator() +
//"TZOFFSETFROM:+0100" + System.lineSeparator() +
//"TZOFFSETTO:+0200" + System.lineSeparator() +
//"END:DAYLIGHT" + System.lineSeparator() +
//"END:VTIMEZONE" + System.lineSeparator() +
//"BEGIN:VEVENT" + System.lineSeparator() +
"CLASS:PRIVATE" + System.lineSeparator() +
"CREATED:20150408T052327Z" + System.lineSeparator() +
"DESCRIPTION:\n" + System.lineSeparator() +
//"DTSTAMP:20150408T052327Z" + System.lineSeparator() +
//"DTSTART;VALUE=DATE:20150408" + System.lineSeparator() +
"LAST-MODIFIED:20150408T052327Z" + System.lineSeparator() +
"PRIORITY:5" + System.lineSeparator() +
//"RECURRENCE-ID;TZID=\"W. Europe Standard Time\":20150408T000000" + System.lineSeparator() +
"SEQUENCE:0" + System.lineSeparator() +
			"DTSTAMP:" + dateFormat.format(this.date)  + "T000000" + System.lineSeparator() +
//			"ORGANIZER;CN=John Doe:MAILTO:" + email + System.lineSeparator() +
			"DTSTART:" + dateFormat.format(this.date) + "T000000" + System.lineSeparator() +
			//	"DTEND:" + dateFormat.format(this.date)  + "T000000Z" + System.lineSeparator() +
"DTEND;VALUE=DATE:" + dateFormat.format(DayAdder(this.date, 1)) + System.lineSeparator() +
			"TRANSP:TRANSPARENT" + System.lineSeparator() +
			//"UID:j5CIEaxCxEGIal5ClSP6aA==" + System.lineSeparator() +
			"X-ALT-DESC;FMTTYPE=text/html:<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n<HTML>\n<HEAD>\n<META NAME=\"Generator\" CONTENT=\"MS Exchange Server version rmj.rmm.rup.rpr\">\n<TITLE></TITLE>\n</HEAD>\n<BODY>\n<!-- Converted from text/rtf format -->\n<BR>\n\n</BODY>\n</HTML>\"" + System.lineSeparator() +
			"X-MICROSOFT-CDO-BUSYSTATUS:FREE" + System.lineSeparator() +
			"X-MICROSOFT-CDO-IMPORTANCE:1" + System.lineSeparator() +
			"X-MICROSOFT-DISALLOW-COUNTER:FALSE" + System.lineSeparator() +
			"X-MS-OLK-AUTOFILLLOCATION:FALSE" + System.lineSeparator() +
			"X-MS-OLK-AUTOSTARTCHECK:FALSE" + System.lineSeparator() +
			"X-MS-OLK-CONFTYPE:0" + System.lineSeparator() +
			"SUMMARY:" + this.reason + System.lineSeparator() +
			"END:VEVENT" + System.lineSeparator() +
			"END:VCALENDAR" + System.lineSeparator()
			);
		writeToFile(content.toString(), fileFolder + "\\" + fileName);
		return fileFolder + "\\" + fileName;
	}
	
	private Date DayAdder(Date date, int numberOfDays){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, numberOfDays); 
		return new Date(c.getTimeInMillis());	
	}
	
	private void writeToFile(String content, String filePath){
		//System.out.println(content);
		try {
			 
			File file = new File(filePath);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
