package uk.co.jmr.sdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.PropertyRoomsDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.PropertyRooms;
import uk.co.jmr.sdp.domain.TenancyForm;


@Repository("propertyRoomsDao")
public class PropertyRoomsDaoImpl implements PropertyRoomsDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(PropertyRooms propertyRoom) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(propertyRoom);
	}

	@Override
	public PropertyRooms findRoomById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(PropertyRooms.class, id);
	}
	
	

	@Override
	public List<PropertyRooms> findRoomByPropertyDetails(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where propertyDetailsForm=? and isOccupied=?",propertyDetailsForm,'N');
		return rooms;
	}

	@Override
	public List<PropertyRooms> findRoomByTenancyDetails(TenancyForm occupiedBy) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where occupiedBy=?",occupiedBy);
		return rooms;
	}

	@Override
	public List<PropertyRooms> findRoomByIsOccupied(char isOccupied) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where isOccupied=?",isOccupied);
		return rooms;
	}

	@Override
	public void delete(List<PropertyRooms> propertyRooms) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(propertyRooms);
	}

	@Override
	public PropertyRooms findRoomByPropertyDetailsAndRoomName(
			PropertyDetailsForm propertyDetailsForm, String roomName) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where propertyDetailsForm=? and roomName=?",propertyDetailsForm,roomName);
		if(rooms.size()>=1)
			return rooms.get(0);
		return null;
	}

	@Override
	public List<PropertyRooms> findRoomByTenancyDetailsIncludingVacantRooms(
			TenancyForm occupiedBy) {
		// TODO Auto-generated method stub
		//List<PropertyRooms> propertyRooms=new ArrayList<PropertyRooms>();
		List<PropertyRooms> rooms1=hibernateTemplate.find("from PropertyRooms where occupiedBy=? ",occupiedBy);
		List<PropertyRooms> rooms2=hibernateTemplate.find("from PropertyRooms where isOccupied=?",'N');
		
		rooms1.addAll(rooms2);
		
		return rooms1;
	}

	@Override
	public PropertyRooms findRoomByPropertyDetailsAndTenancy(
			PropertyDetailsForm propertyDetailsForm, TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where propertyDetailsForm=? and occupiedBy=?",propertyDetailsForm,tenancyForm);
		if(rooms.size()>=1)
			return rooms.get(0);
		return null;
	}

	@Override
	public List<PropertyRooms> findTenancyByPropertyDetails(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from PropertyRooms where propertyDetailsForm=? and isOccupied=?",propertyDetailsForm,'Y');
	}

	@Override
	public List<PropertyRooms> findAllRoomsForPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from PropertyRooms where propertyDetailsForm=?",propertyDetailsForm);
	}

	@Override
	public PropertyRooms findPropertyRoomByRoomName(String roomName) {
		// TODO Auto-generated method stub
		List<PropertyRooms> rooms=hibernateTemplate.find("from PropertyRooms where roomName=?",roomName);
		if(rooms.size()>=1)
			return rooms.get(0);
		return null;
	}

	
}
