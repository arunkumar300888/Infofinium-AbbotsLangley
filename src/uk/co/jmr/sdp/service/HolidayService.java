package uk.co.jmr.sdp.service;

import java.util.Date;
import java.util.List;

import uk.co.jmr.sdp.domain.Holiday;

public interface HolidayService {

	List<Holiday> findAllHolidays();

	void saveHoliday(Holiday holiday);

	List<Holiday> findHolidaysBetween(Date dateCreated, Date targetDate);
}
