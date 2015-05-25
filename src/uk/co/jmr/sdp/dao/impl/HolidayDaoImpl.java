package uk.co.jmr.sdp.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.HolidayDao;
import uk.co.jmr.sdp.domain.Holiday;

@Repository("holidayDao")
public class HolidayDaoImpl implements HolidayDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Holiday> findAllHolidays() {

		return hibernateTemplate.find("from Holiday");
	}

	@Override
	public void saveHoliday(Holiday holiday) {

		hibernateTemplate.saveOrUpdate(holiday);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Holiday> findHolidaysBetween(Date dateCreated, Date targetDate) {

		return hibernateTemplate.find("from Holiday h where h.date >=? and h.date<=?", dateCreated, targetDate);
	}

}
