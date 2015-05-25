package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CheckingOutRoomsDao;
import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.CheckingOutRooms;

@Repository("checkingOutRoomsDao")
public class CheckingOutRoomsDaoImpl implements CheckingOutRoomsDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(CheckingOutRooms checkingOutRooms) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(checkingOutRooms);
	}

	@Override
	public void delete(List<CheckingOutRooms> checkingOutRooms) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(checkingOutRooms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CheckingOutRooms> findCheckingOutRoomsByCheckingOutFormId(
			CheckingOutForm checkingOutForm) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from CheckingOutRooms where checkingOutForm=?",checkingOutForm);
	}
	
	
	
	
}
