package uk.co.jmr.sdp.web.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.co.jmr.sdp.domain.Holiday;
import uk.co.jmr.sdp.service.HolidayService;

import com.ardhika.wfar.WfCase;

public class RagoStatus {

	@Autowired
	private HolidayService holidayService;

	public RagoStatus(HolidayService holidayService) {

		this.holidayService = holidayService;
	}

	public char getRagoStatus(WfCase wfcase) {

		Date dateCreated = wfcase.getDateCreated();
		Date targetDate = (Date) wfcase.getAttribute("Target Date");
		if (targetDate == null) {
			return 'N';
		}
		// System.out.println("taqrDate!@!@!@!@!@! " + targetDate
		// + " Creatd date @!! " + dateCreated);
		//
		// System.out
		// .println("All Holidays-> " + holidayService.findAllHolidays());

		// get the holiday List
		List<Holiday> holidays = holidayService.findHolidaysBetween(dateCreated, targetDate);

		// invoke the function
		int totalHours = calculateHoursBetween(dateCreated, targetDate, holidays);
		if (totalHours == -1)
			return 'E';

		// that function will return the number of hours
		int remainingHours = calculateHoursBetween(new Date(), targetDate, holidays);

		if (remainingHours == -1)
			return 'O';
		float percentage = (float) (remainingHours * 100.0 / totalHours);
		int roundedPercentage = Math.round(percentage);

		// System.out.println("TOTAL HOURS= " + totalHours +
		// " REMAINING HOURS = "
		// + remainingHours + " ROUNDEDPERCENTAGE= " + roundedPercentage);

		if (roundedPercentage < 10)
			return 'R';
		if (roundedPercentage < 50)
			return 'A';
		return 'G';
	}

	private int calculateHoursBetween(Date start, Date end, List<Holiday> holidays) {

		// System.out.println("HB Start : " + start + " HB End : " + end);
		if (start.after(end))
			return -1;

		Calendar cStart = Calendar.getInstance();
		cStart.setTime(start);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(end);

		cStart.add(Calendar.MINUTE, 60 - cStart.get(Calendar.MINUTE));
		int hours = 17 - cStart.get(Calendar.HOUR_OF_DAY);
		if (hours < 0)
			hours = 0;
		setTimeToZero(cStart);
		setTimeToZero(cEnd);
		cEnd.set(Calendar.HOUR_OF_DAY, 17);
		if (isHoliday(cStart.getTime(), holidays))
			hours = 0;
		// System.out.println("On " + cStart.getTime() + " hours : " + hours);
		cStart.add(Calendar.DATE, 1);

		// System.out.println("C Start : " + cStart.getTimeInMillis()
		// + " C End : " + cEnd.getTimeInMillis());
		// System.out.println("CStart after cEnd? " + cStart.after(cEnd));
		while (!(cStart.after(cEnd))) {
			if (!(isHoliday(cStart.getTime(), holidays)) && cStart.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
				&& cStart.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
				hours += 8;
			// System.out.println("On " + cStart.getTime() + " hours : " +
			// hours);
			cStart.add(Calendar.DATE, 1);

		}
		return hours;
	}

	private static boolean isHoliday(Date date, List<Holiday> holidays) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (Holiday h : holidays) {
			Calendar calHol = Calendar.getInstance();
			calHol.setTime(h.getDate());
			if (cal.get(Calendar.DATE) == calHol.get(Calendar.DATE) && cal.get(Calendar.MONTH) == calHol.get(Calendar.MONTH)
				&& cal.get(Calendar.YEAR) == calHol.get(Calendar.YEAR)) {
				// System.out.println(" A Holiday");
				return true;
			}
		}
		// System.out.println("not a Holiday");
		return false;
	}

	private static void setTimeToZero(Calendar calender) {

		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
	}

}
