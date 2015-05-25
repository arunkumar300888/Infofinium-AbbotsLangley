package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.PropertyRoomsDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.PropertyRooms;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.service.PropertyRoomsService;
@Service("propertyRoomsService")
public class PropertyRoomsServiceImpl implements PropertyRoomsService {
	
	@Autowired
	private PropertyRoomsDao propertyRoomsDao;
	
	@Override
	public void save(PropertyRooms propertyRoom) {
		// TODO Auto-generated method stub
		this.propertyRoomsDao.save(propertyRoom);
	}

	@Override
	public PropertyRooms findRoomById(long id) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomById(id);
	}

	@Override
	public List<PropertyRooms> findRoomByPropertyDetails(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByPropertyDetails(propertyDetailsForm);
	}

	@Override
	public List<PropertyRooms> findRoomByTenancyDetails(TenancyForm occupiedBy) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByTenancyDetails(occupiedBy);
	}

	@Override
	public List<PropertyRooms> findRoomByIsOccupied(char isOccupied) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByIsOccupied(isOccupied);
	}

	@Override
	public void delete(List<PropertyRooms> propertyRooms) {
		// TODO Auto-generated method stub
		this.propertyRoomsDao.delete(propertyRooms);
	}

	@Override
	public PropertyRooms findRoomByPropertyDetailsAndRoomName(
			PropertyDetailsForm propertyDetailsForm, String roomName) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByPropertyDetailsAndRoomName(propertyDetailsForm, roomName);
	}

	@Override
	public List<PropertyRooms> findRoomByTenancyDetailsIncludingVacantRooms(
			TenancyForm occupiedBy) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByTenancyDetailsIncludingVacantRooms(occupiedBy);
	}

	@Override
	public PropertyRooms findRoomByPropertyDetailsAndTenancy(
			PropertyDetailsForm propertyDetailsForm, TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findRoomByPropertyDetailsAndTenancy(propertyDetailsForm, tenancyForm);
	}

	@Override
	public List<PropertyRooms> findTenancyByPropertyDetails(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findTenancyByPropertyDetails(propertyDetailsForm);
	}

	@Override
	public List<PropertyRooms> findAllRoomsForPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findAllRoomsForPropertyDetailsForm(propertyDetailsForm);
	}

	@Override
	public PropertyRooms findPropertyRoomByRoomName(String roomName) {
		// TODO Auto-generated method stub
		return this.propertyRoomsDao.findPropertyRoomByRoomName(roomName);
	}

}
