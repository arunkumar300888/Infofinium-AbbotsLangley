package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.RoomDetailsDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RoomDetails;

@Repository("roomDetailsDao")
public class RoomDetailsDaoImpl implements RoomDetailsDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(RoomDetails roomDetails) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(roomDetails);
	}

	@Override
	public void delete(List<RoomDetails> roomDetails) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(roomDetails);
	}

	@Override
	public List<RoomDetails> findRoomDetailsByPropertyDetailsFormId(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		List<RoomDetails> roomDetails=hibernateTemplate.find("from RoomDetails where propertyDetailsForm=? order by roomNo",propertyDetailsForm);
		return roomDetails;
	}

	@Override
	public RoomDetails findRoomDetailsById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(RoomDetails.class, id);
	}

	@Override
	public RoomDetails findRoomDetailsByPropertyDetailsFormIdAndRoomNo(
			PropertyDetailsForm propertyDetailsForm, String roomNo) {
		// TODO Auto-generated method stub
		List<RoomDetails> roomDetails=hibernateTemplate.find("from RoomDetails where propertyDetailsForm=? and roomNo=?",propertyDetailsForm,roomNo);
		if(roomDetails.size()>=1)
			return roomDetails.get(0);
		return null;
	}
	
	

}
