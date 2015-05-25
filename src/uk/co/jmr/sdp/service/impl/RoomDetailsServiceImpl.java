package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.RoomDetailsDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RoomDetails;
import uk.co.jmr.sdp.service.RoomDetailsService;

@Service("roomDetailsService")
public class RoomDetailsServiceImpl implements RoomDetailsService{

	@Autowired
	private RoomDetailsDao roomDetailsDao;

	@Override
	public void save(RoomDetails roomDetails) {
		// TODO Auto-generated method stub
		this.roomDetailsDao.save(roomDetails);
	}

	@Override
	public void delete(List<RoomDetails> roomDetails) {
		// TODO Auto-generated method stub
		this.roomDetailsDao.delete(roomDetails);
	}

	@Override
	public List<RoomDetails> findRoomDetailsByPropertyDetailsFormId(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.roomDetailsDao.findRoomDetailsByPropertyDetailsFormId(propertyDetailsForm);
	}
	
	@Override
	public RoomDetails findRoomDetailsByPropertyDetailsFormIdAndRoomNo(
			PropertyDetailsForm propertyDetailsForm,String roomNo) {
		// TODO Auto-generated method stub
		return this.roomDetailsDao.findRoomDetailsByPropertyDetailsFormIdAndRoomNo(propertyDetailsForm,roomNo);
	}

	@Override
	public RoomDetails findRoomDetailsById(long id) {
		// TODO Auto-generated method stub
		return this.findRoomDetailsById(id);
	}
}
