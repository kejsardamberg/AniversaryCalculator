package dateCalculator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AniversaryList 
{
	public DateOfImportance dateOfImportance;
	public boolean includeEventsInThePast = false;
	public boolean everyThousandDays = true;
	public boolean everyYear = true;
	public boolean specialDays = true;
	public boolean includeHalfYears = true;
	public List<AniversaryDay> aniversaries = new ArrayList<AniversaryDay>();
	
	public AniversaryList (DateOfImportance dateOfImportance)
	{
		this.dateOfImportance = dateOfImportance;
		CreateAniversaryList();
	}
	
	public AniversaryList(){
		
	}
	
	public void CreateAniversaryList()
	{
//		PopulateListByDayDifference(100, 2);
		if (everyThousandDays) { PopulateListByDayDifference(1000, 20); }
		if (specialDays) { 
			PopulateListBySpecialDays(); 
			PopulateListByMonthDifference(100, 20);
			PopulateListByMonthDifference(42, 4);
			}
		if (everyYear) { PopulateListByYearDifference(1, 20); }
		if (includeHalfYears) {PopulateListByHalfYearDifference(20);}
		if (!includeEventsInThePast) { this.RemoveDatesInThePast(); }
		this.SortListByDate();
//		PopulateListByYearDifference(10, 50);
	}
	
	private void PopulateListByHalfYearDifference(int numberOfYearsForward){
		Date today = new Date();
		Date aniversaryDate = MonthAdder(this.dateOfImportance.date, 6);
		this.aniversaries.add(new AniversaryDay(this.dateOfImportance, aniversaryDate, "Half a year since occasion of " + this.dateOfImportance.name + "."));

		int numberOfYears = 1;
		while (aniversaryDate.before(YearAdder(today, numberOfYearsForward))) 
		{
			aniversaryDate = YearAdder(aniversaryDate, 1);
			this.aniversaries.add(new AniversaryDay(this.dateOfImportance, aniversaryDate, numberOfYears + " and a half years since occasion of " + this.dateOfImportance.name + "."));
			numberOfYears++;
		}
	}
	
	private void PopulateListBySpecialDays(){
		this.aniversaries.add(new AniversaryDay(this.dateOfImportance, DayAdder(this.dateOfImportance.date, 42), "42 days since occasion " + this.dateOfImportance.name + "."));
		this.aniversaries.add(new AniversaryDay(this.dateOfImportance, DayAdder(this.dateOfImportance.date, 666), "666 days since occasion " + this.dateOfImportance.name + "."));
		this.aniversaries.add(new AniversaryDay(this.dateOfImportance, DayAdder(this.dateOfImportance.date, 6666), "6666 days since occasion of " + this.dateOfImportance.name + "."));
	}
	
	private void PopulateListByDayDifference(int dayIntervals, int numberOfYearsForward){
		Date today = new Date();
		int days = dayIntervals;
		Date aniversaryDate = this.dateOfImportance.date;
		while (aniversaryDate.before(YearAdder(today, numberOfYearsForward))) 
		{
			aniversaryDate = DayAdder(this.dateOfImportance.date, days);
			this.aniversaries.add(new AniversaryDay(this.dateOfImportance, aniversaryDate, days + " days since occasion of " + this.dateOfImportance.name + "."));
			days = days + dayIntervals;
		}
	}
	
	private void PopulateListByMonthDifference(int monthIntervals, int numberOfYearsForward){
		Date today = new Date();
		int months = monthIntervals;
		Date aniversaryDate = this.dateOfImportance.date;
		while (aniversaryDate.before(YearAdder(today, numberOfYearsForward))) 
		{
			aniversaryDate = MonthAdder(this.dateOfImportance.date, months);
			this.aniversaries.add(new AniversaryDay(this.dateOfImportance, aniversaryDate, months + " months since occasion of " + this.dateOfImportance.name + "."));
			months += monthIntervals;
		}
	}

	
	private void PopulateListByYearDifference(int yearIntervals, int numberOfYearsForward){
		Date today = new Date();
		int years = yearIntervals;
		Date aniversaryDate = this.dateOfImportance.date;
		while (aniversaryDate.before(YearAdder(today, numberOfYearsForward))) 
		{
			aniversaryDate = YearAdder(this.dateOfImportance.date, years);
			this.aniversaries.add(new AniversaryDay(this.dateOfImportance, aniversaryDate, years + " years since occasion of " + this.dateOfImportance.name + "."));
			years += yearIntervals;
		}
	}

	
	private Date YearAdder(Date date, int numberOfYears){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.YEAR, numberOfYears); 
		return new Date(c.getTimeInMillis());
	}
	
	private Date MonthAdder(Date date, int numberOfMonths){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.MONTH, numberOfMonths); 
		return new Date(c.getTimeInMillis());
	}

	private Date DayAdder(Date date, int numberOfDays){
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, numberOfDays); 
		return new Date(c.getTimeInMillis());	
	}
	
	private void SortListByDate(){
		Collections.sort(this.aniversaries);
	}
	
	private void RemoveDatesInThePast(){
		//AniversaryList cleanedList = new AniversaryList(this.dateOfImportance);
		Date today = new Date();
		for(int i = 0; i < this.aniversaries.size(); i++){
			if (year(this.aniversaries.get(i).date) < year(today)){
				this.aniversaries.remove(i);
				i = i - 1;
			}
		}
	}
	
	private static int year(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public void toIcsFiles(String folderForFiles){
		for(AniversaryDay day : this.aniversaries){
			day.toIcsFile(folderForFiles);
		}
	}
	
	public void PrintList(){
		RemoveDatesInThePast();
		SortListByDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for(AniversaryDay day : this.aniversaries){
			System.out.println("The date " + dateFormat.format(day.date) + " should be honored because it's " + day.reason + " (" + dateFormat.format(this.dateOfImportance.date) + ")");
		}
	}
}
