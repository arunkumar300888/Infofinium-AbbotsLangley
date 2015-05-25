package uk.co.jmr.sdp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.HolidayDao;
import uk.co.jmr.sdp.domain.Holiday;
import uk.co.jmr.sdp.service.HolidayService;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayDao holidayDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Holiday> findAllHolidays() {

		return this.holidayDao.findAllHolidays();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveHoliday(Holiday holiday) {

		this.holidayDao.saveHoliday(holiday);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Holiday> findHolidaysBetween(Date dateCreated, Date targetDate) {

		Calendar calDateCreated = Calendar.getInstance();
		calDateCreated.setTime(dateCreated);
		setTimeToZero(calDateCreated);

		Calendar calTargetDate = Calendar.getInstance();
		calTargetDate.setTime(targetDate);
		calTargetDate.add(Calendar.DATE, 1);
		setTimeToZero(calTargetDate);
		// System.out.println("@%%%% "+calDateCreated+" %%%%%@ "+
		// calTargetDate);

		List<Holiday> holidays = holidayDao.findHolidaysBetween(dateCreated, targetDate);
		return holidays;
	}

	private void setTimeToZero(Calendar calDateCreated) {

		calDateCreated.set(Calendar.HOUR_OF_DAY, 0);
		calDateCreated.set(Calendar.HOUR, 0);
		calDateCreated.set(Calendar.MINUTE, 0);
		calDateCreated.set(Calendar.SECOND, 0);
	}
}
