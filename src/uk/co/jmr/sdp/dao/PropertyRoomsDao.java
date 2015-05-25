package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.PropertyRooms;
import uk.co.jmr.sdp.domain.TenancyForm;

public interface PropertyRoomsDao {

	public void save(PropertyRooms propertyRoom);
	
	public void delete(List<PropertyRooms> propertyRooms);
	
	public PropertyRooms findRoomById(long id);
	
	public List<PropertyRooms> findAllRoomsForPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm);
	
	public List<PropertyRooms> findRoomByPropertyDetails(PropertyDetailsForm propertyDetailsForm);
	
	public List<PropertyRooms> findRoomByTenancyDetails(TenancyForm occupiedBy);
	
	public List<PropertyRooms> findRoomByTenancyDetailsIncludingVacantRooms(TenancyForm occupiedBy);
	
	public List<PropertyRooms> findRoomByIsOccupied(char isOccupied);
	
	public PropertyRooms findRoomByPropertyDetailsAndRoomName(PropertyDetailsForm propertyDetailsForm,String roomName);
	
	public PropertyRooms findRoomByPropertyDetailsAndTenancy(PropertyDetailsForm propertyDetailsForm,TenancyForm tenancyForm);

	public List<PropertyRooms> findTenancyByPropertyDetails(
			PropertyDetailsForm propertyDetailsForm);
	
	PropertyRooms findPropertyRoomByRoomName(String roomName);
	
	
}
