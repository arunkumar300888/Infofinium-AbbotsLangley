package uk.co.jmr.sdp.dao;

import java.util.Date;
import java.util.List;

import uk.co.jmr.sdp.domain.Holiday;

public interface HolidayDao {

	List<Holiday> findAllHolidays();

	void saveHoliday(Holiday holiday);

	List<Holiday> findHolidaysBetween(Date dateCreated, Date targetDate);
}
