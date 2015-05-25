package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.CheckingOutRoomsDao;
import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.CheckingOutRooms;
import uk.co.jmr.sdp.service.CheckingOutFormService;
import uk.co.jmr.sdp.service.CheckingOutRoomsService;

@Service("checkingOutRoomsService")
public class CheckingOutRoomsServiceImpl implements CheckingOutRoomsService{

	@Autowired
	private CheckingOutRoomsDao checkingOutRoomsDao;

	@Override
	public void save(CheckingOutRooms checkingOutRooms) {
		// TODO Auto-generated method stub
		this.checkingOutRoomsDao.save(checkingOutRooms);
	}

	@Override
	public void delete(List<CheckingOutRooms> checkingOutRooms) {
		// TODO Auto-generated method stub
		this.checkingOutRoomsDao.delete(checkingOutRooms);
	}

	@Override
	public List<CheckingOutRooms> findCheckingOutRoomsByCheckingOutFormId(
			CheckingOutForm checkingOutForm) {
		// TODO Auto-generated method stub
		return this.checkingOutRoomsDao.findCheckingOutRoomsByCheckingOutFormId(checkingOutForm);
	}
	
	
	
}

